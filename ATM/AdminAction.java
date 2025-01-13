package ATM;

import java.util.Scanner;
import Notes.Notes;

public class AdminAction {
    public static Accounts adminLogin(Scanner scanner) { // method to login admin account
        for (int i = 0; i <= 2; i++) { //3 attempts
            System.out.print("Enter Admin ID:");
            String adminId = scanner.nextLine();
            System.out.print("Enter Admin Pin:");
            String adminPin = scanner.nextLine();
            for (Accounts admin : Atm.getAccounts()) { // iterate through accounts
                if (admin instanceof Admin) { //check account belong to admin
                    if (admin.getId().equals(adminId) && admin.getPin().equals(adminPin)) { //check if id and pin matches
                        System.out.println("Login Successful");
                        return admin;
                    }
                } else {
                    System.out.println("Invalid Admin...Try Again...");
                }
            }
        }
        System.out.println("Too many failed attempts. Access denied.");
        return null;
    }

    public static void addUser (Scanner scanner){ //method to add user account
        System.out.println("Enter User ID:");
        String userID = scanner.nextLine();
        for (Accounts userCheck : Atm.getAccounts()) { // iterate through account check user id already exists
            if(userCheck instanceof User){ // check if account is user type
                if (userCheck.getId().equals(userID)) { // ensure given id is exists already
                    System.out.println("User ID exists already");
                    return;
                }
            }
        }
        System.out.println("Enter User Password:"); //if not then ask password to add new account
        String userPassword = scanner.nextLine();
        User addNewUser = new User(userID, userPassword,0.0); //new account
        Atm.getAccounts().add(addNewUser); //add account to the list
        System.out.println("Account added Successfully");
    }


    public static void deleteUser (Scanner scanner){ //method to delete user account
        System.out.println("Enter user ID: ");
        String userID = scanner.nextLine();
        for (Accounts user : Atm.getAccounts()) { //iterate through accounts
            if (user.getId().equals(userID)) { //if given id match with existing
                Atm.getAccounts().remove(user);// account being removed
                System.out.println("Account deleted Successfully");
                return ;
            }
        }
        System.out.println("User not found.");
    }

    public static void viewAllUsers () { //method to display all user
        System.out.println("All Users: ");
        for (Accounts user : Atm.getAccounts()) { //iterate through all account
            if (user instanceof User) { // check if its user type
                System.out.println("Account: " + user.getId()); // print each account
                //  System.out.println("Current Balance: " + user.getBalance());
            }
        }
    }

    public static boolean depositToATM (Admin admin, Scanner scanner){ //method to deposit amount in ATM
        System.out.println("Enter amount to deposit: ");// deposit amount
        long amount = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 2000 notes you deposit: "); //2000 note count
        long note2k = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 500 notes you deposit: ");//500 note count
        long note5h = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 200 notes you deposit: ");//200 note count
        long note2h = Long.parseLong(scanner.nextLine());
        System.out.println("Enter no.of 100 notes you deposit: ");//100 note count
        long note1h = Long.parseLong(scanner.nextLine());
        long denominationamount = note2k * 2000 + note5h * 500 + note2h * 200 + note1h * 100; //calculate total value
        if (amount == denominationamount) { // check if given amount matches with calculate value
            System.out.println("Denomination matched the given Amount");
            for (Notes notes : Atm.getNotes()) { //iterate through notes
                String paper = notes.getNotes();
                switch (paper) { // update note count
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
            Atm.setAtmbalance(Atm.getAtmbalance() + amount); // update atm balance
            admin.getTransactions().add(new Transactions(admin.getId(), "Deposit", amount)); // add new transaction to the list
            System.out.println("Amount deposited is Rs." + amount);
            System.out.println("Current ATM balance: Rs." + Atm.getAtmbalance());
            return true;
        }
        System.out.println("Invalid denomination");
        return false;
    }

    public static void viewTransaction (Accounts admin, Scanner scanner, String neededTransaction){ // method to view transaction
        switch (neededTransaction) { //based on type (admin,user or all)

            case "Admin" -> { //display admin transaction
                for (Transactions adminTransaction : admin.getTransactions()) { //iterate through admin transaction and display admin transaction
                    System.out.println(adminTransaction.getName() + " has " + adminTransaction.getType() + " Rs." + adminTransaction.getAmount());
                }
            }

            case "User" -> {  //display user transaction
                for (Accounts user : Atm.getAccounts()) { //iterate through accounts
                    if(user instanceof User){ //checks if account belongs to user
                        System.out.println("-" + user.getId()); // display all user id
                    }
                }
                System.out.println("Enter the user ID:");
                String userId = scanner.nextLine(); //enter one id to view its transaction
                for (Accounts user : Atm.getAccounts()) { //iterate through accounts
                    if (user instanceof User) {//checks if account belongs to user
                        if (user.getId().equals(userId)) { // if id matches
                            for (Transactions userTransaction : user.getTransactions()) { //iterate through admin transaction and display admin transaction
                                if (userTransaction != null) {// display transaction
                                    System.out.println(userTransaction.getName() + " has " + userTransaction.getType() + " Rs." + userTransaction.getAmount());
                                } else {
                                    System.out.println("No Transaction happened");
                                }
                            }
                            return;
                        }
                    }
                }
                System.out.println("User not found.");
            }

            case "All" -> {  //display all transaction
                for (Transactions adminTransaction : admin.getTransactions()) { //admin
                    System.out.println(adminTransaction.getName() + " has " + adminTransaction.getType() + " Rs." + adminTransaction.getAmount());
                }
                for (Accounts user : Atm.getAccounts()) {
                    for (Transactions userTransaction : user.getTransactions()) {  //user
                        System.out.println(userTransaction.getName() + " has " + userTransaction.getType() + " Rs." + userTransaction.getAmount());
                    }
                }
            }
        }
    }
}


