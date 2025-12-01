/**
 * COSC 11 Final Project
 * @version Fall 2025
 * @author Katie Tracy
 * 
 * This file contains the main method to simulate amenu-driven
 * banking application with deposit and withdraw functionalities. 
 * 
 */
import java.util.Scanner;

public class BankingApplication {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        BankAccount[] accounts = new BankAccount[10];
        int nextAvailableIndex = 0;   // Tracks the next open spot in the array

        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");

            // Basic input check
            if (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number 1-5.");
                input.nextLine(); // clear invalid
                continue;
            }

            int choice = input.nextInt();
            input.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // Create New Account
                    if (nextAvailableIndex >= accounts.length) {
                        System.out.println("What kind of account would you like to create? (Checking or Savings): ");
                        String dummyType = input.nextLine(); // just to mirror the prompts
                        System.out.print("What is your initial deposit?: $");
                        double dummyDeposit = input.nextDouble();
                        input.nextLine(); // consume newline
                        System.out.println("Account cannot be created.  System has reached capacity.");
                    } else {
                        nextAvailableIndex = createNewAccount(input, accounts, nextAvailableIndex);
                    }
                    break;

                case 2:
                    // Deposit Money
                    performDeposit(input, accounts);
                    break;

                case 3:
                    // Withdraw Money
                    performWithdrawal(input, accounts);
                    break;

                case 4:
                    // Show Account Summary
                    showAccountSummary(input, accounts);
                    break;

                case 5:
                    // Exit
                    System.out.println("Exiting Banking Application. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-5.");
                    break;
            }

            System.out.println(); // blank line for readability
        }

        input.close();
    }

    public static void printMenu() {
        System.out.println("\t BANKING MENU");
        System.out.println("========================");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Show Account Summary");
        System.out.println("5. Exit");
    }

    // Option 1: Create new account
    private static int createNewAccount(Scanner input, BankAccount[] accounts, int nextAvailableIndex) {
        System.out.print("What kind of account would you like to create? (Checking or Savings): ");
        String type = input.nextLine();

        // Simple validation of type
        if (!type.equalsIgnoreCase("Checking") && !type.equalsIgnoreCase("Savings")) {
            System.out.println("Account type not recognized. Account not created.");
            return nextAvailableIndex;
        }

        System.out.print("What is your initial deposit?: $");
        if (!input.hasNextDouble()) {
            System.out.println("Invalid amount. Account not created.");
            input.nextLine(); // clear invalid
            return nextAvailableIndex;
        }

        double initialDeposit = input.nextDouble();
        input.nextLine(); // consume newline

        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative. Account not created.");
            return nextAvailableIndex;
        }

        // Create account
        BankAccount newAccount = new BankAccount(
                capitalize(type),
                initialDeposit,
                nextAvailableIndex
        );
        accounts[nextAvailableIndex] = newAccount;

        System.out.println("Congratulations!  Account #" + nextAvailableIndex
                + " - " + newAccount.getAccountType() + " has been created successfully.");

        nextAvailableIndex++;
        return nextAvailableIndex;
    }

    // Option 2: Deposit
    private static void performDeposit(Scanner input, BankAccount[] accounts) {
        System.out.print("Enter the account number you would like to deposit into: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid account number.");
            input.nextLine();
            return;
        }

        int accountNum = input.nextInt();
        input.nextLine(); // consume newline

        if (!isValidAccount(accountNum, accounts)) {
            System.out.println("Account does not exist.");
            return;
        }

        BankAccount account = accounts[accountNum];
        System.out.printf("Account #%d Balance: $%.2f%n", accountNum, account.getBalance());

        System.out.print("How much would you like to deposit?: $");
        if (!input.hasNextDouble()) {
            System.out.println("Invalid amount.");
            input.nextLine();
            return;
        }

        double amount = input.nextDouble();
        input.nextLine(); // consume newline

        if (amount <= 0) {
            System.out.println("Deposit could not be completed. Amount must be greater than $0.");
            return;
        }

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        System.out.printf("$%.2f deposited successfully. Balance is now $%.2f.%n", amount, newBalance);
    }

    // Option 3: Withdraw
    private static void performWithdrawal(Scanner input, BankAccount[] accounts) {
        System.out.print("Enter the account number you would like to withdraw from: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid account number.");
            input.nextLine();
            return;
        }

        int accountNum = input.nextInt();
        input.nextLine(); // consume newline

        if (!isValidAccount(accountNum, accounts)) {
            System.out.println("Account does not exist.");
            return;
        }

        BankAccount account = accounts[accountNum];
        System.out.printf("Account #%d Balance: $%.2f%n", accountNum, account.getBalance());

        System.out.print("How much would you like to withdraw?: $");
        if (!input.hasNextDouble()) {
            System.out.println("Invalid amount.");
            input.nextLine();
            return;
        }

        double amount = input.nextDouble();
        input.nextLine(); // consume newline

        if (amount <= 0) {
            System.out.println("Withdrawal could not be completed. Amount must be greater than $0.");
            return;
        }

        if (amount > account.getBalance()) {
            System.out.println("Withdrawal could not be completed. Amount exceeds balance.");
            return;
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        System.out.printf("$%.2f withdrawn successfully. Balance is now $%.2f.%n", amount, newBalance);
    }

    // Option 4: Show account summary
    private static void showAccountSummary(Scanner input, BankAccount[] accounts) {
        System.out.print("\nWhich account would you like summarized?: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid account number.");
            input.nextLine();
            return;
        }

        int accountNum = input.nextInt();
        input.nextLine(); // consume newline

        if (!isValidAccount(accountNum, accounts)) {
            System.out.println("Account does not exist.");
            return;
        }

        BankAccount account = accounts[accountNum];
        System.out.println(account.toString());
    }

    // Helper: validate account index
    private static boolean isValidAccount(int accountNum, BankAccount[] accounts) {
        return accountNum >= 0 && accountNum < accounts.length && accounts[accountNum] != null;
    }

    // Helper: capitalize first letter nicely (for type)
    private static String capitalize(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }
        text = text.toLowerCase();
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
