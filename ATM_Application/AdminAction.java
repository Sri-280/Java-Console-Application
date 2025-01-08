package ATM_Application;
import java.util.Scanner;
import Notes.Notes;

public class AdminAction {
    public static Accounts adminLogin(Scanner scanner) {
        for (int i = 0; i <= 2; i++) {
            System.out.print("Enter Admin ID:");
            String adminId = scanner.nextLine();
            System.out.print("Enter Admin Pin:");
            String adminPin = scanner.nextLine();
            for (Accounts admin : Atm.getAccounts()) {
                if (admin instanceof Admin) {
                    if (admin.getId().equals(adminId) && admin.getPin().equals(adminPin)) {
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

        public static void addUser (Scanner scanner){
            System.out.println("Enter User ID:");
            String userID = scanner.nextLine();
            for (Accounts userCheck : Atm.getAccounts()) {
                if(userCheck instanceof User){
                if (userCheck.getId().equals(userID)) {
                    System.out.println("User ID exists already");
                    return;
                }
                }
            }
            System.out.println("Enter User Password:");
            String userPassword = scanner.nextLine();
            User addNewUser = new User(userID, userPassword,0.0);
            Atm.getAccounts().add(addNewUser);
            System.out.println("Account added Successfully");
        }


        public static void deleteUser (Scanner scanner){
            System.out.println("Enter user ID: ");
            String userID = scanner.nextLine();
            for (Accounts user : Atm.getAccounts()) {
                if (user.getId().equals(userID)) {
                    Atm.getAccounts().remove(user);
                    System.out.println("Account deleted Successfully");
                    return ;
                }
            }
            System.out.println("User not found.");
        }

        public static void viewAllUsers () {
            System.out.println("All Users: ");
            for (Accounts user : Atm.getAccounts()) {
                System.out.println("Account: " + user.getId());
              //  System.out.println("Current Balance: " + user.getBalance());
            }
        }

        public static boolean depositToATM (Admin admin, Scanner scanner){
            System.out.println("Enter amount to deposit: ");
            long amount = Long.parseLong(scanner.nextLine());
            System.out.println("Enter no.of 2000 notes you deposit: ");
            long note2k = Long.parseLong(scanner.nextLine());
            System.out.println("Enter no.of 500 notes you deposit: ");
            long note5h = Long.parseLong(scanner.nextLine());
            System.out.println("Enter no.of 200 notes you deposit: ");
            long note2h = Long.parseLong(scanner.nextLine());
            System.out.println("Enter no.of 100 notes you deposit: ");
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
                Atm.setAtmbalance(Atm.getAtmbalance() + amount);
                admin.getTransactions().add(new Transactions(admin.getId(), "Deposit", amount));
                System.out.println("Amount deposited is Rs." + amount);
                System.out.println("Current ATM balance: Rs." + Atm.getAtmbalance());
                return true;
            }
            System.out.println("Invalid denomination");
            return false;
        }

        public static void viewTransaction (Accounts admin, Scanner scanner, String neededTransaction){
            switch (neededTransaction) {

                case "Admin" -> {
                    for (Transactions adminTransaction : admin.getTransactions()) {
                        System.out.println(adminTransaction.getName() + " has " + adminTransaction.getType() + " Rs." + adminTransaction.getAmount());
                    }
                }

                case "User" -> {
                    for (Accounts user : Atm.getAccounts()) {
                        System.out.println("-" + user.getId());
                    }
                    System.out.println("Enter the user ID:");
                    String userId = scanner.nextLine();
                    for (Accounts user : Atm.getAccounts()) {
                        if (user.getId().equals(userId)) {
                            for (Transactions userTransaction : user.getTransactions()) {
                                System.out.println(userTransaction.getName() + " has " + userTransaction.getType() + " Rs." + userTransaction.getAmount());
                            }
                            return;
                        }
                    }
                    System.out.println("User not found.");
                }

                case "All" -> {
                    for (Transactions adminTransaction : admin.getTransactions()) {
                        System.out.println(adminTransaction.getName() + " has " + adminTransaction.getType() + " Rs." + adminTransaction.getAmount());
                    }
                    for (Accounts user : Atm.getAccounts()) {
                        for (Transactions userTransaction : user.getTransactions()) {
                            System.out.println(userTransaction.getName() + " has " + userTransaction.getType() + " Rs." + userTransaction.getAmount());
                        }
                    }
                }
            }
        }
    }




























