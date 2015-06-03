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
        BufferedImage subimage = image.getSubimage(12, 8, 163, 30);
        Imagem.exibir(subimage);
        //BufferedImage img = Filtro.threshold(subimage, 128);
        //Imagem.exibir(img);
        BufferedImage filtroDoNivaldo = Filtro.filtroDoNivaldo(subimage);
        Imagem.exibir(filtroDoNivaldo);
        List<BufferedImage> segmentos = Imagem.segmentar(filtroDoNivaldo, 6);
        for(BufferedImage x: segmentos){
            Imagem.exibir(x);
        }
        String str = null;
        Tesseract instance = Tesseract.getInstance();
        instance.setTessVariable("tessedit_char_whitelist", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        try {
            str = instance.doOCR(filtroDoNivaldo).trim().toLowerCase();
        } catch (TesseractException ex) {
            
        }

        instance.setPageSegMode(10);

        if (str.length() != 6) {
            StringBuilder str2 = new StringBuilder();
            for (BufferedImage bi : segmentos) {
                try {
                    str2.append(instance.doOCR(bi).trim().toLowerCase());
                } catch (TesseractException ex) {
                    
                }
            }
            return str2.toString();
        } else {
            return str;
        }

    }
}
