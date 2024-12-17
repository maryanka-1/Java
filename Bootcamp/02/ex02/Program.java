import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Program {
    public static void main(String[] args) throws IOException {
        try {
            FileManager directory = new FileManager(firstInputParse(args));
            directory.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    public static Path firstInputParse(String[] args) {
        Path startPath = null;
        if (args.length != 1) {
            System.out.println("The directory was entered incorrectly");
        } else {
            startPath = Path.of(args[0].split("=")[1]);
        }
        System.out.println(startPath);
        System.out.print("-> ");
        return startPath;
    }
}
