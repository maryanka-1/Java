package edu.school21.printer.app;
import edu.school21.printer.logic.ImageConverter;
import java.io.*;

public class Program {
    public static void main(String[] args) throws IOException {
        String[] data = parse(args);
        String img = "target/it.bmp";
        ImageConverter converter = new ImageConverter(data[0], data[1], img);
        converter.convert();
    }

    public static String[] parse(String[] args) {
        String[] params = new String[2];
        if (args.length != 2 || !args[0].startsWith("--black=") || !args[1].startsWith("--white=")) {
            System.out.println("Usage: java Program --black=0 --white=.");
            System.exit(-1);
        } else {
            String[] black = args[0].split("=");
            String[] white = args[1].split("=");
            params[0] = black[1];
            params[1] = white[1];
        }
        return params;
    }
}