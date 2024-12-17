import java.util.UUID;

public class Program {
    public static void main(String[] args) throws Exception {
        User Anna = new User("Anna", 2000);
        User Max = new User("Max", 2000);
        User John = new User("John", 2000);
        TransactionsService tS = new TransactionsService();
        tS.addUser(Anna);
        tS.addUser(Max);
        tS.addUser(John);
        try {
            tS.makeTransaction(1, 2, 100);
            tS.makeTransaction(1, 2, 30);
            tS.makeTransaction(2, 3, 10);
            tS.makeTransaction(1, 3, 30);
            tS.makeTransaction(3, 1, 30);
            tS.makeTransaction(2, 1, 20);
            tS.makeTransaction(2, 3, 60);
            tS.makeTransaction(3, 2, 50);

            Transaction[] tsOf1 = tS.transactionsOfUser(1);
            Transaction[] tsOf2 = tS.transactionsOfUser(2);
            Transaction[] tsOf3 = tS.transactionsOfUser(3);

            UUID idTransactionForDelete = tsOf1[0].getId();
            UUID idTransactionForDeleteFalse = tsOf3[0].getId();//для проверки работы функции delete

            System.out.println("Транзакции пользователя 1");
            for (Transaction t : tsOf1) {
                System.out.println(t);
            }
            tS.deleteTransactionOfUser(1, idTransactionForDelete);
            tsOf1 = tS.transactionsOfUser(1);

            System.out.println("Транзакции пользователя 1 после удаления транзакции");
            for (Transaction t : tsOf1) {
                System.out.println(t);
            }
            System.out.println(tS.getBalance(1));

            System.out.println("Транзакции пользователя 2");
            for (Transaction t : tsOf2) {
                System.out.println(t);
            }
            System.out.println(tS.getBalance(2));

            System.out.println("Транзакции пользователя 3");
            for (Transaction t : tsOf3) {
                System.out.println(t);
            }
            System.out.println(tS.getBalance(3));

            Transaction[] unpairTransactions = tS.checkUnpairedTransaction();
            System.out.println("Нет парной транзакции: ");
            for (Transaction t : unpairTransactions) {
                System.out.println(t);
            }
        } catch (IllegalTransactionException | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
