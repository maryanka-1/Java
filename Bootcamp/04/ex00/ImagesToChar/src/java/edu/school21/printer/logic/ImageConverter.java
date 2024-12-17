package edu.school21.printer.logic;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageConverter {
    private String black;
    private String white;
    String file;

    public ImageConverter(String black, String white, String file) {
        this.black = black;
        this.white = white;
        this.file = file;
    }

    public void convert() throws IOException {
        File img = new File(file);
        BufferedImage image = ImageIO.read(img);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(image.getRGB(x, y));
                if (c.equals(Color.BLACK)) {
                    System.out.print(black);
                } else System.out.print(white);
            }
            System.out.println();
        }
    }
}
