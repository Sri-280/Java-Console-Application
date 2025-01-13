package ATM;

import java.util.ArrayList;
import java.util.Scanner;
import Notes.Notes;

public class UserAction {
    public static Accounts userLogin(Scanner scanner) { // to login user
        for (int i = 0; i <= 2; i++) { //allow 3 attempt
            System.out.println("Enter User ID:");
            String userID = scanner.nextLine();
            System.out.println("Enter User Password:");
            String userPassword = scanner.nextLine();
            for (Accounts user : Atm.getAccounts()) { //iterate through accounts
                if (user instanceof User) { // check the account is user type
                    if (user.getId().equals(userID) && user.getPin().equals(userPassword)) { //checks given id and pin matches with existing one
                        System.out.println("Login Successful");
                        return user;
                    }
                }
            }
            System.out.println("Invalid...Try Again...");
        }
        System.out.println("Too many failed attempts. Access denied."); //deny access after 3 failure attempts
        return null;
    }

    public static void deposit(User user, Scanner scanner) { //method to deposit user amount
        System.out.println("Enter amount to deposit: "); //enter amount to deposit
        long amount = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 2000 notes you deposit:"); //2000 notes count
        long note2k = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 500 notes you deposit");  //500 notes count
        long note5h = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 200 notes you deposit");  //200 notes count
        long note2h = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 100 notes you deposit");  //100 notes count
        long note1h = Long.parseLong(scanner.nextLine());
        long denominationamount = note2k * 2000 + note5h * 500 + note2h * 200 + note1h * 100; //adds the denominations to check it match with og amount
        if (amount == denominationamount) {// if equals given amount match with calculated amount
            System.out.println("Denomination matched the given Amount");
            for (Notes notes : Atm.getNotes()) { //iterate through atm notes
                String paper = notes.getNotes();
                switch (paper) { //set new note count from the existing one
                    case "2000":
                        notes.setNotecount(notes.getNotecount() + note2k);
                        break;
                    case "500":
                        notes.setNotecount(notes.getNotecount() + note5h);
                        break;
                    case "200":
                        notes.setNotecount(notes.getNotecount() + note2h);
                        break;
                    case "100":
                        notes.setNotecount(notes.getNotecount() + note1h);
                        break;
                }
            }
            user.deposit(amount); //add deposit to user balance
            Atm.updateAtmBalance(amount); //method to update atm balance
            user.getTransactions().add(new Transactions(user.getId(), "Deposit", amount));// new transaction is added to the list
            System.out.println("Deposited amount successfully.");
            System.out.println("Amount deposited is Rs." + amount);
            //System.out.println("Current ATM balance: Rs." + Atm.getAtmbalance());
            return ;
        }
        System.out.println("Invalid denomination");
    }

    public static void withdraw(User user, Scanner scanner) throws CloneNotSupportedException { //method to withdraw amount
        ArrayList<String> notesTransaction=new ArrayList<>();
        ArrayList<Notes> duplicateTransaction=new ArrayList<Notes>();//created new duplicate list to store withdrawal
        System.out.println("Enter amount to withdraw: ");
        long withdrawAmount=Long.parseLong(scanner.nextLine());
        if(withdrawAmount<=user.getBalance()){ //check user balance is sufficient for withdrawal
            if(withdrawAmount<=Atm.getAtmbalance()){ //check atm balance is sufficient for withdrawal
                long storeamount=withdrawAmount;
                for (Notes atmNotes:Atm.getNotes()){ //iterate atm note and clone
                    duplicateTransaction.add(atmNotes.clone());
                }
                while (withdrawAmount!=0){
                    for (Notes duplicateNotes:duplicateTransaction){
                        String note=duplicateNotes.getNotes();
                        switch (note){
                            case "2000","500","200","100":
                                withdrawAmount= UserAction.performWithdraw(withdrawAmount,duplicateNotes,notesTransaction);
                                break;
                        }
                    }
                    if(withdrawAmount==0) //if withdraw is completed successfully
                    {
                        Atm.setNotes(duplicateTransaction);
                        user.setBalance(user.getBalance()-storeamount); //set new balance after withdrawing amount
                        for (String givenNotes:notesTransaction){ // display notes withdrawn
                            System.out.println("-"+givenNotes);
                        }
                        user.getTransactions().add(new Transactions(user.getId(), "Withdrawn", storeamount));// add transaction
                        return;
                    }
                    else {
                        System.out.println("No denomination...Enter another amount...");
                        return;
                    }
                }
            }
            System.out.println("Insufficient amount in ATM");
            return ;
        }
        System.out.println("Insufficient amount in Your Account");
    }

    private static long performWithdraw(long storeamount, Notes duplicateNotes, ArrayList<String> notesTransaction) { //perform withdraw
        long count = (storeamount / Integer.parseInt(duplicateNotes.getNotes()));// calculate no.of needed for withdrawal
        if (Long.parseLong(duplicateNotes.getNotes()) <= storeamount && 0 < duplicateNotes.getNotecount()) { // withdraw amount must be greater than notes
            if ((count <= duplicateNotes.getNotecount())) { //if count is less than note count in atm
                storeamount = storeamount - (count * Integer.parseInt(duplicateNotes.getNotes())); //user amount to be reduced by multiplying notes and count
                duplicateNotes.setNotecount(duplicateNotes.getNotecount() - count); //set note count by reducing given count of note by user
                notesTransaction.add("You got " + duplicateNotes.getNotes() + ", count " + count);// add how much notes given
                return storeamount;
            }else {
                storeamount=storeamount-(duplicateNotes.getNotecount()*Integer.parseInt(duplicateNotes.getNotes())); //user amount to be reduced by multiplying notes and available count of notes in atm
                notesTransaction.add("You got " + duplicateNotes.getNotes() + " count " +duplicateNotes.getNotecount());// add how much notes given
                duplicateNotes.setNotecount(0); //all notes been given hence set notcount to 0
                return storeamount;
            }
        }
        return storeamount;
    }

    public static void checkBalance(User user) { // method to check user balance

        System.out.println("Current Balance:" + user.getBalance()); //user balance
    }

    public static void changePassword(User user, Scanner scanner) { //method to change pin
        for (int i = 0; i <= 2; i++) {
            System.out.println("Enter current password:");
            String currentPassword = scanner.nextLine();
            if (currentPassword.equals(user.getPin())) { //if given password matches original password
                System.out.println("Enter new password:");// get new password
                String newPassword = scanner.nextLine();
                user.setPin(newPassword); //set new password
                System.out.println("Password changed successfully.");
                return;
            } else {
                System.out.println("Incorrect password...Try again.");
            }
        }
        System.out.println("Access denied.");
    }

    public static void viewTransactions(User user) {  //method to view transaction
        System.out.println("Your Transaction History:");
        for (Transactions transaction : user.getTransactions()) { //iterate through transaction
            System.out.println(transaction.getName() + " has " + transaction.getType() + " Rs." + transaction.getAmount());
        }
    }
}






