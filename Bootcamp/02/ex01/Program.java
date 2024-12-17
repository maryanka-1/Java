import java.io.*;
import java.util.*;

public class Program {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Main <text1.txt> <text2.txt>");
        } else {
            FrequencyWords simp = new FrequencyWords(args[0], args[1]);
            simp.run();
        }
    }
}