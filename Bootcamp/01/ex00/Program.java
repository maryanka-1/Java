public class Program {
    public static void main(String[] args) throws Exception {
        User Anna = new User("Anna", 1000);
        User Sergey = new User("Sergey", 2000);
        System.out.println(Anna);
        System.out.println(Sergey);
        System.out.println();
        Transaction first = new Transaction(Anna, Sergey, Transaction.TRANSFER_CATEGORY.DEBITS, 200);

        System.out.println(first);
        System.out.println(Anna);
        System.out.println(Sergey);
        System.out.println();

        Transaction second = new Transaction(Sergey, Anna, Transaction.TRANSFER_CATEGORY.CREDITS, -1000);
        System.out.println(second);
        System.out.println(Anna);
        System.out.println(Sergey);
        System.out.println();

        Transaction third = new Transaction(Sergey, Anna, Transaction.TRANSFER_CATEGORY.DEBITS, 1000);
        System.out.println(third);
        System.out.println(Anna);
        System.out.println(Sergey);
        System.out.println();

    }
}