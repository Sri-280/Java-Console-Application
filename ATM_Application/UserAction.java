package ATM_Application;

import java.util.ArrayList;
import java.util.Scanner;
import Notes.Notes;

public class UserAction {
    public static Accounts userLogin(Scanner scanner) {
        for (int i = 0; i <= 2; i++) {
            System.out.println("Enter User ID:");
            String userID = scanner.nextLine();
            System.out.println("Enter User Password:");
            String userPassword = scanner.nextLine();
            for (Accounts user : Atm.getAccounts()) {
                if (user instanceof User) {
                    if (user.getId().equals(userID) && user.getPin().equals(userPassword)) {
                        System.out.println("Login Successful");
                        return user;
                    }
                }
            }
            System.out.println("Invalid...Try Again...");
        }
        System.out.println("Too many failed attempts. Access denied.");
        return null;
    }

    public static void deposit(User user, Scanner scanner) {
        System.out.println("Enter amount to deposit: ");
        long amount = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 2000 notes you deposit:");
        long note2k = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 500 notes you deposit");
        long note5h = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 200 notes you deposit");
        long note2h = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 100 notes you deposit");
        long note1h = Long.parseLong(scanner.nextLine());
        long denominationamount = note2k * 2000 + note5h * 500 + note2h * 200 + note1h * 100;
        if (amount == denominationamount) {
            System.out.println("Denomination matched the given Amount");
            for (Notes notes : Atm.getNotes()) {
                String paper = notes.getNotes();
                switch (paper) {
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
            user.deposit(amount);
            Atm.updateAtmBalance(amount);
            user.getTransactions().add(new Transactions(user.getId(), "Deposit", amount));
            System.out.println("Deposited amount successfully.");
            System.out.println("Amount deposited is Rs." + amount);
            System.out.println("Current ATM balance: Rs." + Atm.getAtmbalance());
            return ;
        }
        System.out.println("Invalid denomination");
    }

    public static void withdraw(User user, Scanner scanner) throws CloneNotSupportedException {
        ArrayList<String> notesTransaction=new ArrayList<>();
        ArrayList<Notes> duplicateTransaction=new ArrayList<Notes>();
        System.out.println("Enter amount to withdraw: ");
        long withdrawAmount=Long.parseLong(scanner.nextLine());
        if(withdrawAmount<=user.getBalance()){
            if(withdrawAmount<=Atm.getAtmbalance()){
                long storeamount=withdrawAmount;
                for (Notes atmNotes:Atm.getNotes()){
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
                    if(withdrawAmount==0)
                    {
                        Atm.setNotes(duplicateTransaction);
                        user.setBalance(user.getBalance()-storeamount);
                        for (String givenNotes:notesTransaction){
                            System.out.println("-"+givenNotes);
                        }
                        Atm.getTransactions().add(new Transactions(user.getId(), "Withdrawn",storeamount ));
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

    private static long performWithdraw(long storeamount, Notes duplicateNotes, ArrayList<String> notesTransaction) {
        long count = (storeamount / Integer.parseInt(duplicateNotes.getNotes()));
        if (Long.parseLong(duplicateNotes.getNotes()) <= storeamount && 0 < duplicateNotes.getNotecount()) {
            if ((count <= duplicateNotes.getNotecount())) {
                storeamount = storeamount - (count * Integer.parseInt(duplicateNotes.getNotes()));
                duplicateNotes.setNotecount(duplicateNotes.getNotecount() - count);
                notesTransaction.add("You got " + duplicateNotes.getNotes() + " count " + count);
                return storeamount;
            }else {
                storeamount=storeamount-(duplicateNotes.getNotecount()*Integer.parseInt(duplicateNotes.getNotes()));
                notesTransaction.add("You got " + duplicateNotes.getNotes() + " count " +duplicateNotes.getNotecount());
                duplicateNotes.setNotecount(0);
                return storeamount;
            }
        }
        return storeamount;
    }

    public static void checkBalance(User user) {

        System.out.println("Current Balance:" + user.getBalance());
    }

    public static void changePassword(User user, Scanner scanner) {
        for (int i = 0; i <= 4; i++) {
            System.out.println("Enter current password:");
            String currentPassword = scanner.nextLine();
            if (currentPassword.equals(user.getPin())) {
                System.out.println("Enter new password:");
                String newPassword = scanner.nextLine();
                user.setPin(newPassword);
                System.out.println("Password changed successfully.");
                return;
            } else {
                System.out.println("Incorrect password...Try again.");
            }
        }
        System.out.println("Access denied.");
    }

    public static void viewTransactions(User user) {
        System.out.println("Your Transaction History:");
        for (Transactions transaction : user.getTransactions()) {
            System.out.println(transaction.getName() + " has " + transaction.getType() + " Rs." + transaction.getAmount());
        }
    }
}

//        System.out.println("Enter amount to withdraw:");
//        long amount = Long.parseLong(scanner.nextLine());
//        if (user.withdraw(amount)) {
//            if (Atm.getAtmbalance() >= amount) {
//                Atm.updateAtmBalance(-amount);
//                user.getUserTransactions().add(new Transactions(user.getId(), "Withdraw", amount));
//                System.out.println("Successfully withdrawn.");
//                System.out.println("Current Balance: " + user.getBalance());
//            } else {
//                System.out.println("Insufficient ATM balance.");
//                System.out.println("Current ATM balance: Rs." + Atm.getAtmbalance());
//            }
//        } else {
//            System.out.println("Insufficient account balance.");
//            System.out.println("Current Balance: " + user.getBalance());
//        }
//      }


























