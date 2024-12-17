import java.util.UUID;


public class Transaction {
    enum TRANSFER_CATEGORY {
        DEBITS,
        CREDITS
    }

    private final UUID id;
    private final String recipient;
    private final String sender;
    public final TRANSFER_CATEGORY transferCategory;
    Integer transferAmount;

    public Transaction(User recipient, User sender, TRANSFER_CATEGORY transferCategory, Integer transferAmount, UUID id) throws IllegalTransactionException {
        if (transferCategory == TRANSFER_CATEGORY.DEBITS && transferAmount > 0) {
            recipient.setBalance(recipient.getBalance() + transferAmount);
        } else if (transferCategory == TRANSFER_CATEGORY.CREDITS && transferAmount < 0 && sender.getBalance() >= -transferAmount) {
            sender.setBalance(sender.getBalance() + transferAmount);
        } else throw new IllegalTransactionException("Not enough balance");
        this.id = id;
        this.recipient = recipient.getUserName();
        this.sender = sender.getUserName();
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
    }

    public UUID getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public TRANSFER_CATEGORY getTransferCategory() {
        return transferCategory;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    @Override
    public String toString() {
        String debits = "INCOME";
        String credits = "OUTCOME";
        if (transferCategory == TRANSFER_CATEGORY.DEBITS) {
            return recipient + " <- "
                    + sender + ", "
                    + transferAmount + ", "
                    + debits + " "
                    + id;
        } else return recipient + " -> "
                + sender + ", "
                + transferAmount + ", "
                + credits + " "
                + id;
    }
}
