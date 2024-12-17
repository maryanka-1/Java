import java.util.UUID;

public class TransactionsService {
    private UsersList userlist;

    public TransactionsService() {
        userlist = new UsersArrayList();
    }

    public void addUser(User user) {
        userlist.addUser(user);

    }

    public int getBalance(int id) throws UserNotFoundException {
        try {
            return userlist.getUserId(id).getBalance();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    public void makeTransaction(int idRecipient,
                                int idSender,
                                int amount) throws IllegalTransactionException, UserNotFoundException { // получатель, отправитель, сумма
        if (amount <= getBalance(idSender)) {
            UUID id = UUID.randomUUID();
            userlist.getUserId(idRecipient).getTransactionsList().add(new Transaction(
                    userlist.getUserId(idRecipient),
                    userlist.getUserId(idSender),
                    Transaction.TRANSFER_CATEGORY.DEBITS,
                    amount,
                    id));
            userlist.getUserId(idSender).getTransactionsList().add(new Transaction(
                    userlist.getUserId(idRecipient),
                    userlist.getUserId(idSender),
                    Transaction.TRANSFER_CATEGORY.CREDITS,
                    -amount,
                    id));
        } else throw new IllegalTransactionException(
                "You do not have enough balance to make a transaction"
        );
    }

    public Transaction[] transactionsOfUser(int id) throws UserNotFoundException {
        try {
            return userlist.getUserId(id).getTransactionsList().toArray();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    public void deleteTransactionOfUser(int idUser, UUID idTransaction) throws UserNotFoundException, TransactionNotFoundException {
        try {
            userlist.getUserId(idUser).getTransactionsList().remove(idTransaction);
        } catch (TransactionNotFoundException e) {
            throw new TransactionNotFoundException(e.getMessage());
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    public Transaction[] checkUnpairedTransaction() throws UserNotFoundException {
        TransactionsList unpairTransaction = new TransactionsLinkedList();
        boolean check = false;
        for (int i = 0; i < userlist.getUserCount(); i++) {
            Transaction[] transactionsOfUser1 = userlist.getUserIndex(i).getTransactionsList().toArray();
            for (int trUser1 = 0; trUser1 < transactionsOfUser1.length; trUser1++) {
                check = false;
                Transaction trCheck1 = transactionsOfUser1[trUser1];
                for (int j = 0; j < userlist.getUserCount(); j++) {
                    if (i == j) continue;
                    Transaction[] transactionsOfUser2 = userlist.getUserIndex(j).getTransactionsList().toArray();
                    for (int trUser2 = 0; trUser2 < transactionsOfUser2.length; trUser2++) {
                        Transaction trCheck2 = transactionsOfUser2[trUser2];
                        if (trCheck1.getId().equals(trCheck2.getId())) {
                            check = true;
                            break;
                        }
                    }
                    if (check) break;

                }
                if (!check) {
                    unpairTransaction.add(trCheck1);
                }
            }
        }
        return unpairTransaction.toArray();
    }
}
