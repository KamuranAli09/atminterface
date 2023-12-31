import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultipleAccountATM {

    public static class User {
        private String username;
        private int pin;
        private double balance;
        private List<String> transactionHistory;

        public User(String username, int pin) {
            this.username = username;
            this.pin = pin;
            this.balance = 1000.00;
            this.transactionHistory = new ArrayList<>();
        }

        public String getUsername() {
            return username;
        }

        public int getPin() {
            return pin;
        }

        public double getBalance() {
            return balance;
        }

        public List<String> getTransactionHistory() {
            return transactionHistory;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                transactionHistory.add("Deposit: $" + amount);
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                transactionHistory.add("Withdraw: $" + amount);
                return true;
            }
            return false;
        }

        public void transfer(User recipient, double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                recipient.deposit(amount);
                transactionHistory.add("Transfer to " + recipient.getUsername() + ": $" + amount);
            }
        }
    }

    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        initializeUserAccounts();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter PIN: ");
            int pin = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            User currentUser = authenticateUser(username, pin);

            if (currentUser != null) {
                performTransactions(scanner, currentUser);
            } else {
                System.out.println("Authentication failed. Please try again.");
            }
        }
    }

    private static void initializeUserAccounts() {
        users.add(new User("Nayana", 1904));
        users.add(new User("Pradhaan", 1369));
        users.add(new User("John", 5395));
        users.add(new User("Peter", 1102));
    }

    private static User authenticateUser(String username, int pin) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPin() == pin) {
                return user;
            }
        }
        return null;
    }

    private static void performTransactions(Scanner scanner, User user) {
        while (true) {
            displayATMMenu(user.getUsername());
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println();

            switch (choice) {
                case 1:
                    checkBalance(user);
                    break;
                case 2:
                    displayTransactionHistory(user);
                    break;
                case 3:
                    performWithdrawal(scanner, user);
                    break;
                case 4:
                    performDeposit(scanner, user);
                    break;
                case 5:
                    performTransfer(scanner, user);
                    break;
                case 6:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayATMMenu(String username) {
        System.out.println("\nWelcome, " + username + "!");
        System.out.println("ATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Transaction History");
        System.out.println("3. Withdraw");
        System.out.println("4. Deposit");
        System.out.println("5. Transfer");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }

    private static void checkBalance(User user) {
        System.out.println("Your balance: $" + user.getBalance());
    }

    private static void displayTransactionHistory(User user) {
        System.out.println("Transaction History:");
        for (String transaction : user.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private static void performWithdrawal(Scanner scanner, User user) {
        System.out.print("Enter the amount to withdraw: $");
        double withdrawAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        boolean withdrawSuccess = user.withdraw(withdrawAmount);
        if (withdrawSuccess) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    private static void performDeposit(Scanner scanner, User user) {
        System.out.print("Enter the amount to deposit: $");
        double depositAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        user.deposit(depositAmount);
        System.out.println("Deposit successful.");
    }

    private static void performTransfer(Scanner scanner, User user) {
        System.out.print("Enter the recipient's username: ");
        String recipientUsername = scanner.nextLine();
        System.out.print("Enter the amount to transfer: $");
        double transferAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        User recipient = findUserByUsername(recipientUsername);
        if (recipient != null) {
            user.transfer(recipient, transferAmount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Recipient not found.");
        }
    }

    private static void logout() {
        System.out.println("Logging out...");
    }

    private static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
