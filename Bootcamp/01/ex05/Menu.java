import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionsService tS;

    public Menu() {
        tS = new TransactionsService();
    }

    private static void printMenu() {
        System.out.println("1. Add a user " + "\n" +
                "2. View user balances " + "\n" +
                "3. Perform a transfer " + "\n" +
                "4. View all transactions for a specific user " + "\n" +
                "5. DEV – remove a transfer by ID " + "\n" +
                "6. DEV – check transfer validity " + "\n" +
                "7. Finish execution"
        );
    }

    private void press1(Scanner scan) throws ErrorData, UserNotFoundException {
        boolean retry = false;
        System.out.println("Enter a user name and a balance");
        do {
            String[] com = scan.nextLine().split(" ");
            if (com.length == 2 && com[0].matches("^[a-zA-Z]+$") && com[1].matches("^[0-9]+$")) {
                tS.addUser(new User(com[0], Integer.parseInt(com[1])));
                System.out.println("User with id = " + tS.getIdLastUser() + " is added");
                System.out.println("---------------------------------------------------------");
                retry = false;
            } else {
                retry = true;
                System.out.println("Mistake. Repeat");
            }
        } while (retry);
        printMenu();
    }

    private void press2(Scanner scanner) throws UserNotFoundException {
        System.out.println("Enter a user ID");
        String name;
        int balance;
        boolean retry = false;
        do {
            try {
                int idUser = Integer.parseInt(scanner.nextLine());
                balance = tS.getBalance(idUser);
                name = tS.getName(idUser);
                System.out.println(name + " - " + balance);
                System.out.println("---------------------------------------------------------");
                retry = false;
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage() + '\n' + "Repeat input");
                retry = true;
            }
        } while (retry);
        printMenu();
    }

    private void press3(Scanner scanner) {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        boolean retry = false;
        do {
            String[] com = scanner.nextLine().split(" ");
            if (com.length == 3 && com[0].matches("^[0-9]+$") &&
                    com[1].matches("^[0-9]+$") &&
                    com[2].matches("^[0-9]+$")) {
                try {
                    tS.makeTransaction(Integer.parseInt(com[1]), Integer.parseInt(com[0]), Integer.parseInt(com[2]));
                    System.out.println("The transfer is completed");
                    System.out.println("---------------------------------------------------------");
                    retry = false;
                } catch (UserNotFoundException | IllegalTransactionException e) {
                    System.out.println(e.getMessage() + '\n' + "Repeat input");
                    retry = true;
                }
            } else {
                retry = true;
                System.out.println("Mistake input. Repeat");
            }
        } while (retry);
        printMenu();
    }

    private void press4(Scanner scanner) throws UserNotFoundException {
        System.out.println("Enter a user ID");
        boolean retry = false;
        do {
            try {
                int idUser = Integer.parseInt(scanner.nextLine());
                Transaction[] transOfUser = tS.transactionsOfUser(idUser);
                for (Transaction t : transOfUser) {
                    System.out.println("To " + tS.getName(idUser) + "(id = " + idUser + ") " + t.getTransferAmount() + " with id = " + t.getId());
                }
                System.out.println("---------------------------------------------------------");
                retry = false;
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage() + '\n' + "Repeat input");
                retry = true;
            }
        } while (retry);
        printMenu();
    }

    private void press5(Scanner scanner) throws UserNotFoundException, TransactionNotFoundException {
        System.out.println("Enter a user ID and a transfer ID");
        boolean retry = false;
        do {
            try {
                String[] com = scanner.nextLine().split(" ");
                Transaction transaction = null;
                try {
                    transaction = tS.getTransaction(Integer.parseInt(com[0]), UUID.fromString(com[1]));
                    tS.deleteTransactionOfUser(Integer.parseInt(com[0]), UUID.fromString(com[1]));

                    System.out.println("Transfer To " +
                            tS.getName(Integer.parseInt(com[0])) +
                            "(id = " + com[0] + ") " +
                            transaction.getTransferAmount() +
                            " removed");
                    System.out.println("---------------------------------------------------------");
                    retry = false;
                } catch (UserNotFoundException | TransactionNotFoundException | NumberFormatException e) {
                    System.out.println("uncorrect input, repeat input");
                    retry = true;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage() + '\n' + "Repeat input");
                retry = true;
            }
        } while (retry);
        printMenu();
    }

    private void press6(Scanner scanner) throws UserNotFoundException {
        System.out.println("Check results:");
        try {
            Transaction[] unpair = tS.checkUnpairedTransaction();
            for (Transaction t : unpair) {
                System.out.println(t.getRecipient() +
                        "(id =" + t.getIdRecipient() +
                        ") has an unacknowledged transfer id = " +
                        t.getId() + " from " +
                        t.getSender() +
                        "(id =" + t.getIdSender() +
                        ") for " +
                        t.getTransferAmount());
                System.out.println("---------------------------------------------------------");
            }
            if (unpair.length == 0) {
                System.out.println("Unacknowledged transfer not found");
                System.out.println("---------------------------------------------------------");
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        printMenu();
    }

    public void runMenu(String[] args) throws ErrorData, UserNotFoundException, TransactionNotFoundException {
        printMenu();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int userChoice = Integer.parseInt(sc.nextLine().strip());
            switch (userChoice) {
                case 1:
                    press1(sc);
                    break;
                case 2:
                    press2(sc);
                    break;
                case 3:
                    press3(sc);
                    break;
                case 4:
                    press4(sc);
                    break;
                case 5:
                    if (args.length > 0 && args[0].equals("--profile=dev")) {
                        press5(sc);
                    } else {
                        System.out.println("You don`t have access rights to perform this operation");
                        System.out.println("---------------------------------------------------------");
                        printMenu();
                    }
                    break;
                case 6:
                    if (args.length > 0 && args[0].equals("--profile=dev")) {
                        press6(sc);
                    } else {
                        System.out.println("You don`t have access rights to perform this operation");
                        System.out.println("---------------------------------------------------------");
                        printMenu();
                    }
                    break;
                case 7:
                    return;
            }
        }
    }
}
