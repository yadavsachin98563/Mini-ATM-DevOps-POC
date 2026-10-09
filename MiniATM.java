//MiniATM.java

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MiniATM {

    private static final Scanner scanner = new Scanner(System.in);

    // ATM account details
    private static String accountHolder = "John Doe";
    private static String accountNumber = "1234567890";
    private static String pin = "1234";
    private static double balance = 10000.00;

    // Transaction history
    private static final List<String> transactions = new ArrayList<>();

    public static void main(String[] args) {

        initializeTransactions();

        System.out.println("====================================");
        System.out.println("        WELCOME TO MINI ATM");
        System.out.println("====================================");

        if (!authenticateUser()) {
            System.out.println("\nAccount locked.");
            System.out.println("Thank you for using Mini ATM.");
            return;
        }

        showMenu();

        scanner.close();
    }

    // --------------------------------------------------
    // Authentication
    // --------------------------------------------------

    private static boolean authenticateUser() {

        final int MAX_ATTEMPTS = 3;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            System.out.print("\nEnter your PIN: ");
            String enteredPin = scanner.nextLine();

            if (enteredPin.equals(pin)) {
                System.out.println("\nLogin successful!");
                System.out.println("Welcome, " + accountHolder + "!");
                return true;
            }

            int remainingAttempts = MAX_ATTEMPTS - attempt;

            if (remainingAttempts > 0) {
                System.out.println("Incorrect PIN.");
                System.out.println("Attempts remaining: " + remainingAttempts);
            }
        }

        return false;
    }

    // --------------------------------------------------
    // Main Menu
    // --------------------------------------------------

    private static void showMenu() {

        while (true) {

            System.out.println("\n====================================");
            System.out.println("              ATM MENU");
            System.out.println("====================================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Mini Statement");
            System.out.println("6. Change PIN");
            System.out.println("7. Logout");
            System.out.println("====================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    checkBalance();
                    break;

                case 2:
                    depositMoney();
                    break;

                case 3:
                    withdrawMoney();
                    break;

                case 4:
                    transferMoney();
                    break;

                case 5:
                    showMiniStatement();
                    break;

                case 6:
                    changePin();
                    break;

                case 7:
                    System.out.println("\nYou have been logged out.");
                    System.out.println("Thank you for using Mini ATM!");
                    return;

                default:
                    System.out.println("\nInvalid choice. Please select 1-7.");
            }
        }
    }

    // --------------------------------------------------
    // Check Balance
    // --------------------------------------------------

    private static void checkBalance() {

        System.out.println("\n------------------------------------");
        System.out.println("           ACCOUNT BALANCE");
        System.out.println("------------------------------------");
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Account Number : " + maskAccountNumber());
        System.out.printf("Available Balance: ₹%.2f%n", balance);
        System.out.println("------------------------------------");
    }

    // --------------------------------------------------
    // Deposit
    // --------------------------------------------------

    private static void depositMoney() {

        System.out.println("\n------------------------------------");
        System.out.println("             DEPOSIT");
        System.out.println("------------------------------------");

        double amount = readAmount("Enter deposit amount: ₹");

        balance += amount;

        transactions.add(
                String.format("Deposited ₹%.2f | Balance: ₹%.2f", amount, balance)
        );

        System.out.printf(
                "₹%.2f deposited successfully.%n",
        );

        System.out.printf(
                "New balance: ₹%.2f%n",
        );
    }

    // --------------------------------------------------
    // Withdraw
    // --------------------------------------------------

    private static void withdrawMoney() {

        System.out.println("\n------------------------------------");
        System.out.println("            WITHDRAW");
        System.out.println("------------------------------------");

        double amount = readAmount("Enter withdrawal amount: ₹");

        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        balance -= amount;

        transactions.add(
                String.format("Withdrawn ₹%.2f | Balance: ₹%.2f", amount, balance)
        );

        System.out.printf(
                "Please collect your cash: ₹%.2f%n",
        );

        System.out.printf(
                "Remaining balance: ₹%.2f%n",
        );
    }

    // --------------------------------------------------
    // Transfer
    // --------------------------------------------------

    private static void transferMoney() {

        System.out.println("\n------------------------------------");
        System.out.println("             TRANSFER");
        System.out.println("------------------------------------");

        System.out.print("Enter beneficiary account number: ");
        String beneficiaryAccount = scanner.nextLine().trim();

        if (!beneficiaryAccount.matches("\\d{10}")) {
            System.out.println(
                    "Invalid account number. It must contain 10 digits."
            );
            return;
        }

        if (beneficiaryAccount.equals(accountNumber)) {
            System.out.println(
                    "You cannot transfer money to your own account."
            );
            return;
        }

        double amount = readAmount("Enter transfer amount: ₹");

        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        balance -= amount;

        transactions.add(
                String.format(
                        "Transferred ₹%.2f to A/C ****%s | Balance: ₹%.2f",
                        amount,
                        beneficiaryAccount.substring(6),
                )
        );

        System.out.printf(
                "₹%.2f transferred successfully.%n",
        );

        System.out.printf(
                "Remaining balance: ₹%.2f%n",
        );
    }

    // --------------------------------------------------
    // Mini Statement
    // --------------------------------------------------

    private static void showMiniStatement() {

        System.out.println("\n====================================");
        System.out.println("           MINI STATEMENT");
        System.out.println("====================================");

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        int count = 1;

        for (String transaction : transactions) {
            System.out.println(count + ". " + transaction);
            count++;
        }

        System.out.println("------------------------------------");
        System.out.printf("Current Balance: ₹%.2f%n", balance);
        System.out.println("====================================");
    }

    // --------------------------------------------------
    // Change PIN
    // --------------------------------------------------

    private static void changePin() {

        System.out.println("\n------------------------------------");
        System.out.println("             CHANGE PIN");
        System.out.println("------------------------------------");

        System.out.print("Enter current PIN: ");
        String currentPin = scanner.nextLine();

        if (!currentPin.equals(pin)) {
            System.out.println("Incorrect current PIN.");
            return;
        }

        System.out.print("Enter new 4-digit PIN: ");
        String newPin = scanner.nextLine();

        if (!newPin.matches("\\d{4}")) {
            System.out.println("PIN must contain exactly 4 digits.");
            return;
        }

        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.nextLine();

        if (!newPin.equals(confirmPin)) {
            System.out.println("PIN confirmation does not match.");
            return;
        }

        if (newPin.equals(pin)) {
            System.out.println(
                    "New PIN must be different from the current PIN."
            );
            return;
        }

        pin = newPin;

        transactions.add("PIN changed successfully");

        System.out.println("PIN changed successfully.");
    }

    // --------------------------------------------------
    // Input Validation
    // --------------------------------------------------

    private static int readInt(String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }
        }
    }

    private static double readAmount(String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {

                double amount = Double.parseDouble(input);

                if (amount <= 0) {
                    System.out.println(
                            "Amount must be greater than zero."
                    );
                    continue;
                }

                return amount;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid amount. Please enter a valid number."
                );
            }
        }
    }

    // --------------------------------------------------
    // Helper Methods
    // --------------------------------------------------

    private static String maskAccountNumber() {

        return "******" + accountNumber.substring(6);
    }

    private static void initializeTransactions() {

        transactions.add(
                String.format(
                        "Opening Balance: ₹%.2f",
                )
        );
    }
}
