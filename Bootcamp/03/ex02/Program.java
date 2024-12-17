import java.io.*;
import java.nio.file.*;
import java.sql.Array;
import java.util.*;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        int[] params = parse(args);
        Calculate calculate = new Calculate(params[0], params[1]);
        calculate.run();
    }

    public static int[] parse(String[] args) {
        int[] params = new int[2];
        if (args.length != 2 || !args[0].startsWith("--arraySize=") && !args[1].startsWith("--threadsCount=")) {
            System.out.println("Usage: java Program --arraySize=13 --threadsCount=3");
        } else {
            String[] arraySize = args[0].split("=");
            String[] threadsCount = args[1].split("=");
            try {
                params[0] = Integer.parseInt(arraySize[1]);
                params[1] = Integer.parseInt(threadsCount[1]);
                if (params[0] > 2000000 || params[1] > params[0]) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("You can only use integers from 1 to 2000000 to specify " +
                        "the array size and number of threads. " +
                        "\nThe number of threads cannot exceed the size of the array.");
                System.exit(-1);
            }
        }
        return params;
    }
}
