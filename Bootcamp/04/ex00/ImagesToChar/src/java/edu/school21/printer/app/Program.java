package edu.school21.printer.app;
import edu.school21.printer.logic.ImageConverter;
import java.io.*;

public class Program {
    public static void main(String[] args) throws IOException {
        String[] data = parse(args);
        ImageConverter converter = new ImageConverter(data[0], data[1], data[2]);
        converter.convert();
    }

    public static String[] parse(String[] args) {
        String[] params = new String[3];
        if (args.length != 3 || !args[0].startsWith("--black=") || !args[1].startsWith("--white=") || !args[2].startsWith("--file=")) {
            System.out.println("Usage: java Program --black=0 --white=. --file=it.bmp");
            System.exit(-1);
        } else {
            String[] black = args[0].split("=");
            String[] white = args[1].split("=");
            String[] file = args[2].split("=");
            params[0] = black[1];
            params[1] = white[1];
            params[2] = file[1];
        }
        return params;
    }
}