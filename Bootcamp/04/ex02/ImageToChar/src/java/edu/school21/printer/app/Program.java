package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

import java.io.*;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(separators = "=")

public class Program {
    @Parameter(names = "--black", required = true)
    private static String black;
    @Parameter(names = "--white", required = true)
    private static String white;


    public static void main(String[] args) throws IOException {
        JCommander.newBuilder()
                .addObject(new Program())
                .build()
                .parse(args);
        new ImageConverter(black, white).convert();
    }
}


