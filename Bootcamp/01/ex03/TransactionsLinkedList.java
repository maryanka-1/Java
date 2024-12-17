import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private Node first;
    private Node last;
    private int size = 0;

    private static class Node {
        private Node previous;
        private Transaction transaction;
        private Node next;

        public Node(Node previous, Transaction transaction, Node next) {
            this.previous = previous;
            this.transaction = transaction;
            this.next = next;
        }
    }


    @Override
    public void add(Transaction transaction) {
        if (size == 0) {
            first = new Node(null, transaction, null);
            last = first;
        } else {
            Node secondLast = last;
            last = new Node(secondLast, transaction, null);
            secondLast.next = last;
        }
        size++;
    }

    @Override
    public void remove(UUID id) throws TransactionNotFoundException {
        Node node = getNode(id);
        Node nodeNext = node.next;
        Node nodePrevious = node.previous;
        if (nodeNext != null && nodePrevious != null) {
            nodeNext.previous = nodePrevious;
            nodePrevious.next = nodeNext;
        }
        if (nodeNext != null && nodePrevious == null) {
            first = nodeNext;
        }
        if (nodeNext == null && nodePrevious != null) {
            last = nodePrevious;
        }
        size--;
    }

    private Node getNode(UUID id) throws TransactionNotFoundException {
        String idString = id.toString();
        Node node = first;
        while (node != null) {
            if (node.transaction.getId().toString().equals(idString)) {
                return node;
            }
            node = node.next;
        }
        throw new TransactionNotFoundException("Транзакции с заданным ID не найдено");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node node = first;
        for (int i = 0; i < size; i++) {
            transactions[i] = node.transaction;
            node = node.next;
        }
        return transactions;
    }
}
