package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassificadorCentroide {

    private double tolerancia = 0.001;
    protected List<Centroide> centroides;
    private Color corNaoClassificado = Color.WHITE;

    public ClassificadorCentroide(BufferedImage imagem, int nrClasses) {     
        centroides = new ArrayList<>();
        criarCentroides(imagem, nrClasses);
    }

    public double getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(double tolerancia) {
        this.tolerancia = tolerancia;
    }

    public Color getCorNaoClassificado() {
        return corNaoClassificado;
    }

    public void setCorNaoClassificado(Color corNaoClassificado) {
        this.corNaoClassificado = corNaoClassificado;
    }

    protected final List<Centroide> processar(BufferedImage imagem) {
        List<Centroide> centroidesAntigos = null;
        while (true) {
            associarAosCentroides(imagem);
            if (naoMudou(centroidesAntigos)) {
                return centroides;
            }
            centroidesAntigos = centroides;
            
            centroides = new ArrayList<>();
            for (Centroide c : centroidesAntigos) {
                centroides.add(new Centroide(c.recalcularCentro()));
            }
        }
    }

    private double calcularErro(List<Centroide> centroidesAntigos) {
        double distanciaAnterior = 0;
        double distanciaAtual = 0;
        for (int i = 0; i < centroidesAntigos.size(); i++) {
            distanciaAnterior += centroidesAntigos.get(i).distanciaMedia();
            distanciaAtual += centroides.get(i).distanciaMedia();
        }
        return Math.abs(distanciaAtual - distanciaAnterior) / (distanciaAnterior + distanciaAtual);
    }

    private void associarAosCentroides(BufferedImage imagem) {
        for (int x = 0; x < imagem.getWidth(); x++) {
            for (int y = 0; y < imagem.getHeight(); y++) {
                if (imagem.getRGB(x, y) != corNaoClassificado.getRGB()) {
                    associarAoCentroide(new Pixel(new Color(imagem.getRGB(x, y)),new Point(x,y)));
                }
            }
        }
    }

    private boolean naoMudou(List<Centroide> centroidesAntigos) {
        if (centroidesAntigos == null) {
            return false;
        }
        return calcularErro(centroidesAntigos) <= tolerancia;
    }

    private void criarCentroides(BufferedImage imagem, int nrClasses) {
        
        int height = imagem.getHeight();
        int width = imagem.getWidth();
        
        int alturaMedia = height / 2;
        int primeiroPonto = (width / nrClasses) / 2;
        int pontosSeguinte = width / nrClasses;
        
        for(int i = 0; i < nrClasses; i++){
            centroides.add(new Centroide(new Pixel(new Point(primeiroPonto + i * pontosSeguinte, alturaMedia))));
        }    

    }
    
    public List<BufferedImage> getImages (){
        List<BufferedImage> images = new ArrayList<>();
        centroides.stream().forEach((c) -> {
            images.add(c.getImage());
        });
        return images;
    }

    protected abstract void associarAoCentroide(Pixel pixel);
}
