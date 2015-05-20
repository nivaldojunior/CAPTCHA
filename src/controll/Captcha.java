package controll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Captcha {

    public static String captchaToString(BufferedImage image) {

        try {
            Imagem.salvar(image, "teste");
        } catch (IOException ex) {
           
        }
        BufferedImage img = Filtro.threshold(Filtro.filtroDoNivaldo(image.getSubimage(12, 8, 163, 30)), 128);
        List<BufferedImage> segmentos = Imagem.segmentar(img, 6);
        String str = null;
        Tesseract instance = Tesseract.getInstance();

        try {
            str = instance.doOCR(img).trim().toLowerCase().replaceAll("[\\s\\W_]+", "");
        } catch (TesseractException ex) {
            
        }

        instance.setPageSegMode(10);

        if (str.length() != 6) {
            StringBuilder str2 = new StringBuilder();
            for (BufferedImage bi : segmentos) {
                try {
                    str2.append(instance.doOCR(bi).trim().toLowerCase().replaceAll("[\\s\\W_]+", ""));
                } catch (TesseractException ex) {
                    str2.append("0");
                }
            }
            return str2.toString();
        } else {
            return str;
        }

    }
}
