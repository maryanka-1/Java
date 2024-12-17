public class User {
    private final int id;
    private String userName;
    private int balance;

    public User(String userName, int balance) {
        this.userName = userName;
        this.id = UserIdsGenerator.getInstance().generateId();
        this.balance = balance;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return userName + " " + balance + " " + id;
    }
}
