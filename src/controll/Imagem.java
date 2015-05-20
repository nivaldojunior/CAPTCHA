package controll;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Imagem {

    public static void exibir(Image image) {

        ImageIcon icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout());
        contentPane.add(new JScrollPane(imageLabel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

    }

    public static void salvar(BufferedImage image, String name) throws IOException {

        File file = new File(name);
        file.createNewFile();
        ImageIO.write(image, "PNG", file);

    }

    public static List<BufferedImage> segmentar(BufferedImage img, int segmentos) {

        Kmeans kmeans = new Kmeans(img, segmentos);
        return kmeans.getImages();

    }

}
