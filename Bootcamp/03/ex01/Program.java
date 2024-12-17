import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        int count = parse(args);
        PrintThread thread1 = new PrintThread("Egg", count);
        PrintThread thread2 = new PrintThread("Heg", count);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }


    }

    public static int parse(String[] args) {
        int a = 0;
        if (args.length != 1) {
            System.out.println("Usage: java Main --count=50");
        } else {
            String[] tmp = args[0].split("=");
            try {
                a = Integer.parseInt(tmp[1]);
            } catch (java.lang.Exception e) {
                System.out.println("It's not a integer");
            }
        }
        return a;
    }
}
