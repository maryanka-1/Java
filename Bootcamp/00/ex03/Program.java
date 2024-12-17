import java.util.Scanner;

import static java.lang.Math.pow;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String strWeek = sc.nextLine();
        long result = 0;
        int countWeek = 1;
        while (countWeek < 19 && !(strWeek.equals("42"))) {
            if (!strWeek.equals("Week " + countWeek)) {
                System.out.println(strWeek);
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            result = result + minNumber(sc) * (long) (pow(10, countWeek - 1));
            countWeek++;
            strWeek = sc.nextLine();
        }
        sc.close();
        for (int i = 1; result != 0; i++) {
            System.out.print("Week " + i + " ");
            long j = result % 10;
            while (j > 0) {
                System.out.print("=");
                j--;
            }
            System.out.print(">\n");
            result /= 10;
        }
    }

    public static long minNumber(Scanner sc) {
        long min = 10;
        for (int i = 0; i < 5; i++) {
            long num = sc.nextLong();
            if (num < min) min = num;
        }
        sc.nextLine();
        return min;
    }
}