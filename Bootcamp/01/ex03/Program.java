import java.util.UUID;

public class Program {
    public static void main(String[] args) throws Exception {

        User Anna = new User("Anna", 2000);
        User Max = new User("Max", 2000);
        UUID forSearch = UUID.randomUUID();

        Transaction t1 = new Transaction(Anna, Max, Transaction.TRANSFER_CATEGORY.CREDITS, -500);
        Transaction t2 = new Transaction(Anna, Max, Transaction.TRANSFER_CATEGORY.DEBITS, 100);
        Transaction t3 = new Transaction(Max, Anna, Transaction.TRANSFER_CATEGORY.CREDITS, -1000);

        TransactionsLinkedList linkedList = new TransactionsLinkedList();
        linkedList.add(t1);
        linkedList.add(t2);
        linkedList.add(t3);

        Transaction[] transactions = linkedList.toArray();
        for (Transaction t : transactions) {
            System.out.println(t);
        }

        try {
            linkedList.remove(t3.getId());
            linkedList.remove(forSearch);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Transaction[] transactionsAfterDelete = linkedList.toArray();
        for (Transaction t : transactionsAfterDelete) {
            System.out.println(t);
        }


    }
}
