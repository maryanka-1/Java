import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] array = sc.next().toCharArray();
        int[] count = new int[65535];
        int sizeNewArray = 0;
        for (int i = 0; i < array.length; i++) {
            int letter = (int) array[i];
            count[letter]++;
            if (count[letter] == 1) {
                sizeNewArray++;
            }
        }
        int[] numOfInput = new int[sizeNewArray];
        int[] symbol = new int[sizeNewArray];
        int next = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                numOfInput[next] = count[i];
                symbol[next] = i;
                next++;

            }
        }
        for (int i = 0; i < numOfInput.length - 1; i++) {
            for (int j = 0; j < numOfInput.length - i - 1; j++) {
                if (numOfInput[j] < numOfInput[j + 1]) {
                    int temp = numOfInput[j];
                    int tempSymbol = symbol[j];
                    numOfInput[j] = numOfInput[j + 1];
                    symbol[j] = symbol[j + 1];
                    numOfInput[j + 1] = temp;
                    symbol[j + 1] = tempSymbol;
                }
            }
        }
        int top = 10;
        if (sizeNewArray < top) {
            top = sizeNewArray;
        }
        printDiagram(numOfInput, symbol, top);
    }

    public static void printDiagram(int[] num, int[] symbol, int top) {
        double coefficient = (double) num[0] / 10;
        int[][] matrix = new int[11][top];
        int counterCol = 0;
        for (int j = 0; j < top; j++) {
            int a = Math.abs((int) (num[j] / coefficient) - 10);
            matrix[a][j] = num[j];
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < top; j++) {
                if (matrix[i][j] != 0) {
                    if (matrix[i][j] < 10) {
                        System.out.print(" ");
                    }
                    System.out.print(matrix[i][j] + "  ");
                    counterCol++;
                }
                if (matrix[i][j] == 0 && j < counterCol) {
                    System.out.print(" #  ");
                }
            }
            System.out.print('\n');
        }
        for (int i = 0; i < top; i++) {
            System.out.print(" " + (char) symbol[i] + "  ");
        }
        System.out.print('\n');
    }
}