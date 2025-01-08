package ATM_Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Listofnotes.Note100;
import Listofnotes.Note200;
import Listofnotes.Note500;
import Listofnotes.Note2000;
import Notes.Notes;


public class Atm {
    private static long atmbalance; //to store atm balance
   // private static final ArrayList<User> users = new ArrayList<>();  //arraylist to stores user
   // private static final ArrayList<Admin> admins = new ArrayList<>();  //to store admins
    private static ArrayList<Transactions> transactions = new ArrayList<>();  // to store the transaction
    private static ArrayList<Notes> notes = new ArrayList<Notes>(Arrays.asList(new Note2000("2000",0),new Note500("500",0),new Note200("200",0),new Note100("100",0) ));  //stores notes with initial count as 0
    private static ArrayList<Accounts> accounts = new ArrayList<>();


    public Atm() {   //constructor for Atm class
        atmbalance = 0;   //initializing atmbalance equals 0
    }
    public static long getAtmbalance() {  //to get current atmbalance
        return atmbalance;
    }
    public static void setAtmbalance(long balance) {  //set atmbalance
        atmbalance = balance;  //set given balance as new atmbalance
    }
    public static void updateAtmBalance(long amount) {
        atmbalance += amount;
    }
    public static ArrayList<Transactions> getTransactions(){ //update atmbalance after every transaction
        return transactions;
    }

    public static ArrayList<Notes> getNotes(){
        return notes;
    }
    public static void setNotes(ArrayList<Notes> notes){
        Atm.notes= notes;
    }

    public static ArrayList<Accounts> getAccounts() {
        return accounts;
    }

    public static void setAccounts(ArrayList<Accounts> accounts) {
        Atm.accounts = accounts;
    }

    public void startAtm() throws CloneNotSupportedException { //main method
        Scanner scanner = new Scanner(System.in);
        Atm.getAccounts().add(new Admin("admin","1234"));
        Atm.getAccounts().add(new User("1","1",0.0));
        System.out.println("Welcome to the ATM");

        while (true) {
            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit"); //options to choose their role
            System.out.print("Choose an option: ");// enter their choice
            int choice = Integer.parseInt(scanner.nextLine());//entered value stored in choice

            switch (choice) {
                case 1 :   //if choice==1
                    handleAdminLogin(scanner);  //calls the admin login method
                    break;
                case 2 :   //if choice==2
                    handleUserLogin(scanner);   //calls the user login method
                    break;
                case 3 :   //if choice==3
                {
                    System.out.println("Thank you for using the ATM.");
                    scanner.close();
                    return;  //exit the loop
                }
                default :  // if any other value is given except 1,2,3 this case is used
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleAdminLogin(Scanner scanner) {  //method for admin login
        Admin admin = (Admin) AdminAction.adminLogin(scanner); //calls methods from adminaction
        if (admin != null) {   //if admin login is success
            boolean isAdminLoggedIn = true;
            while (isAdminLoggedIn) {
            System.out.println("WELCOME TO ADMIN PAGE\nAdmin choice:");
            System.out.println("1. Add User\n2. Delete User\n3. View All Users\n4. Deposit to ATM\n5. View Transactions\n6. Logout");//menu options
                System.out.print("Choose an option: ");// choice to be entered
                int adminChoice = Integer.parseInt(scanner.nextLine());

                switch (adminChoice) {
                    case 1:   // if choice==1
                        AdminAction.addUser(scanner);   //calls
                        break;
                    case 2:   // if choice==2
                        AdminAction.deleteUser(scanner);
                        break;
                    case 3:   // if choice==3
                        AdminAction.viewAllUsers();
                        break;
                    case 4:   // if choice==4
                        AdminAction.depositToATM(admin, scanner);
                        break;
                    case 5:   // if choice==5
                    {
                        System.out.println("View Transactions:");
                        System.out.println("1. Admin Transactions\n2. User Transactions\n3. All Transactions");
                        int transChoice = Integer.parseInt(scanner.nextLine());
                        switch (transChoice) {
                            case 1:
                                AdminAction.viewTransaction(admin, scanner, "Admin");
                                continue;
                            case 2:
                                AdminAction.viewTransaction(admin, scanner, "User");
                                continue;
                            case 3:
                                AdminAction.viewTransaction(admin, scanner, "All");
                                continue;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    case 6:   // if choice==6
                        isAdminLoggedIn = false;
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }


    private void handleUserLogin(Scanner scanner) throws CloneNotSupportedException {
        User user = (User) UserAction.userLogin(scanner);
        if (user != null) {
            boolean isUserLoggedIn = true;
            while (isUserLoggedIn) {
            System.out.println("WELCOME TO USER PAGE\nUser Menu:");
            System.out.println("1. Deposit\n2. Withdraw\n3. Check Balance\n4. Change Password\n5. View Transactions\n6. Logout");
                System.out.print("Choose an option: ");
                int userChoice = Integer.parseInt(scanner.nextLine());


                switch (userChoice) {
                    case 1 :
                        UserAction.deposit(user, scanner);
                        break;
                    case 2 :
                        UserAction.withdraw(user, scanner);
                        break;
                    case 3 :
                        UserAction.checkBalance((User) user);
                        break;
                    case 4 :
                        UserAction.changePassword(user, scanner);
                        break;
                    case 5 :
                        UserAction.viewTransactions(user);
                        break;
                    case 6 :
                        isUserLoggedIn = false;
                        return;
                    default :
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}




















































