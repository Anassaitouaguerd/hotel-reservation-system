public class User {
    private int userId;
    private int balance;

    public User(int userId, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.userId = userId;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    // Deduct money from balance
    public void deductBalance(int amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("User{ID=%d, Balance=%d}", userId, balance);
    }
}
