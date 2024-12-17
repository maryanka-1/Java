import java.util.Scanner;
import java.util.UUID;

public class Program {
    public static void main(String[] args) throws ErrorData, UserNotFoundException {
        Menu menu = new Menu();
        try {
            menu.runMenu(args);
        } catch (ErrorData | UserNotFoundException | TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
