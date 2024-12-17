package edu.school21.printer.logic;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageConverter {
    private Attribute insteadBlack;
    private Attribute insteadWhite;

    public ImageConverter(String black, String white) {
        insteadBlack = toAttribute(black);
        insteadWhite = toAttribute(white);
    }

    public void convert() throws IOException {
        File file = new File("target/it.bmp");
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                if (color == Color.BLACK.getRGB()) {
                    System.out.print(Ansi.colorize(" ", insteadBlack));
                } else {
                    System.out.print(Ansi.colorize(" ", insteadWhite));
                }
            }
            System.out.println();
        }
    }

    private Attribute toAttribute(String attribute) {
        return switch (attribute.toLowerCase()) {
            case "black" -> Attribute.BLACK_BACK();
            case "green" -> Attribute.GREEN_BACK();
            case "blue" -> Attribute.BLUE_BACK();
            case "red" -> Attribute.RED_BACK();
            case "yellow" -> Attribute.YELLOW_BACK();
            case "magenta" -> Attribute.MAGENTA_BACK();
            case "cyan" -> Attribute.CYAN_BACK();
            default -> Attribute.WHITE_BACK();
        };
    }
}
