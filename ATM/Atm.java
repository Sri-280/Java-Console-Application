package ATM;

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
    private static ArrayList<Accounts> accounts = new ArrayList<>(); // arraylist to store both admin and user account


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
        Atm.getAccounts().add(new Admin("admin","1234")); //new object is created in account arraylist for admin
        Atm.getAccounts().add(new User("","",0.0));  //new object is created in account arraylist for user
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
            while (true) {
                System.out.println("WELCOME TO ADMIN PAGE\nAdmin choice:");
                System.out.println("1. Add User\n2. Delete User\n3. View All Users\n4. Deposit to ATM\n5. View Transactions\n6. Logout");//menu options
                System.out.print("Choose an option: ");// choice to be entered
                int adminChoice = Integer.parseInt(scanner.nextLine());

                switch (adminChoice) {
                    case 1:   // if choice==1
                        AdminAction.addUser(scanner);   //calls addUser method from Adminaction cls
                        break;
                    case 2:   // if choice==2
                        AdminAction.deleteUser(scanner);//calls deleteUser method from Adminaction cls
                        break;
                    case 3:   // if choice==3
                        AdminAction.viewAllUsers(); //calls viewallUser method from Adminaction cls
                        break;
                    case 4:   // if choice==4
                        AdminAction.depositToATM(admin, scanner);//calls depositetoATM method from Adminaction cls
                        break;
                    case 5:   // if choice==5
                    {
                        System.out.println("View Transactions:");
                        System.out.println("1. Admin Transactions\n2. User Transactions\n3. All Transactions"); //options asked whose transaction need to be visited
                        int transChoice = Integer.parseInt(scanner.nextLine());
                        switch (transChoice) {
                            case 1: // if choice==1
                                AdminAction.viewTransaction(admin, scanner, "Admin");  //calls viewtransaction method and displays admin transaction
                                continue;
                            case 2: // if choice==2
                                AdminAction.viewTransaction(admin, scanner, "User");  //calls viewtransaction method and displays user transaction
                                continue;
                            case 3: // if choice==3
                                AdminAction.viewTransaction(admin, scanner, "All");  //calls viewtransaction method and displays all transaction
                                continue;
                            default: // if invalid choice
                                System.out.println("Invalid choice.");
                        }
                    }
                    case 6:   // if choice==6
                        return ; //exits the loop

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }


    private void handleUserLogin(Scanner scanner) throws CloneNotSupportedException {
        User user = (User) UserAction.userLogin(scanner); //calls method from useraction to login user account
        if (user != null) {
            while (true) {
                System.out.println("WELCOME TO USER PAGE\nUser Menu:");
                System.out.println("1. Deposit\n2. Withdraw\n3. Check Balance\n4. Change Password\n5. View Transactions\n6. Logout");
                System.out.print("Choose an option: ");
                int userChoice = Integer.parseInt(scanner.nextLine());


                switch (userChoice) {
                    case 1 : // if choice==1
                        UserAction.deposit(user, scanner); //calls deposite method from Useraction cls
                        break;
                    case 2 : // if choice==2
                        UserAction.withdraw(user, scanner);  //calls withdraw method from Useraction cls
                        break;
                    case 3 : // if choice==3
                        UserAction.checkBalance(user);  //calls checkbalance method from Useraction cls
                        break;
                    case 4 : // if choice==4
                        UserAction.changePassword(user, scanner);  //calls changepassword method from Useraction cls
                        break;
                    case 5 : // if choice==5
                        UserAction.viewTransactions(user); //calls view transaction method from Useraction cls
                        break;
                    case 6 : // if choice==6
                        return; //exit the loop
                    default : // if choice is invalid
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}

























