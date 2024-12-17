import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number;
        int countCoffee = 0;
        while ((number = scanner.nextInt()) != 42) {
            if (numberIsSimple(sumOfNumber(number))) {
                countCoffee++;
            }
        }
        System.out.println("Count of coffee-request – " + countCoffee);

    }

    public static int sumOfNumber(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public static boolean numberIsSimple(int sum) {
        boolean result = false;
        if (sum > 1) {
            for (int i = 2; i * i <= sum; i++) {
                if ((sum % i) == 0) {
                    return result;
                }
            }
            result = true;
        }
        return result;
    }
}