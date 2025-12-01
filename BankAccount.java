public class BankAccount {

    private String type;       // "Checking" or "Savings"
    private double balance;
    private int accountNum;    // index in the BankAccount[] array

    // Constructor
    public BankAccount(String type, double initialBalance, int accountNum) {
        this.type = type;
        this.balance = initialBalance;
        this.accountNum = accountNum;
    }

    // Getters and setters
    public String getAccountType() {
        return type;
    }

    public void setAccountType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public int getAccountNumber() {
        return accountNum;
    }

    @Override
    public String toString() {
        // Account summary
        String result = "";
        result += "Account #" + accountNum + " - " + type + "\n";
        result += "====================\n";
        result += String.format("Balance: $%.2f", balance);
        return result;
    }
}
