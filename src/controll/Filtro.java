package controll;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Filtro {

    public static BufferedImage threshold(BufferedImage image, int limiar) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16);
                int g = (int) ((rgb & 0x0000FF00) >>> 8);
                int b = (int) (rgb & 0x000000FF);
                int media = (r + g + b) / 3;

                if (media < limiar) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                } else {
                    image.setRGB(i, j, Color.WHITE.getRGB());
                }
            }
        }
        return image;
    }

    public static BufferedImage filtroDoNivaldo(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = out.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        for (int i = 1; i < width - 2; i++) {
            for (int j = 1; j < height - 2; j++) {

                int p1 = image.getRGB(i - 1, j - 1);
                int rgb1 = (int) (p1 & 0x000000FF);

                int p2 = image.getRGB(i - 1, j);
                int rgb2 = (int) (p2 & 0x000000FF);

                int p3 = image.getRGB(i - 1, j + 1);
                int rgb3 = (int) (p3 & 0x000000FF);

                int p4 = image.getRGB(i, j + 1);
                int rgb4 = (int) (p4 & 0x000000FF);

                int p5 = image.getRGB(i + 1, j + 1);
                int rgb5 = (int) (p5 & 0x000000FF);

                int p6 = image.getRGB(i + 1, j);
                int rgb6 = (int) (p6 & 0x000000FF);

                int p7 = image.getRGB(i + 1, j - 1);
                int rgb7 = (int) (p7 & 0x000000FF);

                int p8 = image.getRGB(i, j - 1);
                int rgb8 = (int) (p8 & 0x000000FF);

                int p9 = image.getRGB(i, j);
                int rgb9 = (int) (p9 & 0x000000FF);

                int sumRGB = (rgb1 + rgb2 + rgb3 + rgb4 + rgb5 + rgb6 + rgb7 + rgb8 + rgb9) / 2;

                if (sumRGB >= 255) {
                    out.setRGB(i, j, Color.WHITE.getRGB());
                } else {
                    Color color = new Color(sumRGB, sumRGB, sumRGB);
                    out.setRGB(i, j, color.getRGB());
                }

            }

        }
        return out;
    }

}
