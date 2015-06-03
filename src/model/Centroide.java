package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Centroide {

    private final Pixel centro;
    private final List<Pixel> pixels = new ArrayList<>();

    public Centroide(Pixel centro) {
        this.centro = centro;
    }

    public Pixel getCentro() {
        return centro;
    }

    public BufferedImage getImage() {
        
        Point max = new Point(0,0);
        Point min = new Point(0,0);
        getSize(max, min);

        BufferedImage out = new BufferedImage((int) (max.getX() - min.getX()) + 1, (int) (max.getY() - min.getY()) + 1, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = out.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, out.getWidth(), out.getHeight());

        pixels.stream().forEach((p) -> {
            int x = (int) (p.getPonto().getX() - min.getX());
            int y = (int) (p.getPonto().getY() - min.getY());
            out.setRGB(x, y, p.getCor().getRGB());
        });

        return out;
    }

    public Pixel recalcularCentro() {
        Pixel centroide = new Pixel(new Point(0, 0));
        int cont = 0;

        for (Pixel ponto : pixels) {
            centroide.getPonto().setLocation(ponto.getPonto().getX() + centroide.getPonto().getX(), ponto.getPonto().getY() + centroide.getPonto().getY());
            cont++;
        }
        centroide.getPonto().setLocation(centroide.getPonto().getX() / cont, centroide.getPonto().getY() / cont);
        centroide.setCor(Color.red);
        return centroide;
    }

    public double distancia(Pixel ponto) {
        return ponto.getPonto().distance(centro.getPonto());
    }

    public void adicionarPonto(Pixel ponto) {
        pixels.add(ponto);
    }

    public int size() {
        return pixels.size();
    }

    public double distanciaMedia() {
        double soma = 0;
        int cont = 0;
        for (Pixel ponto : pixels) {
            soma += ponto.getPonto().distance(centro.getPonto());
            cont++;
        }
        return cont == 0 ? 0 : soma / cont;
    }

    public void getSize(Point max, Point min) {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        for (Pixel p : pixels) {
            if (p.getPonto().getX() > maxX) {
                maxX = (int) p.getPonto().getX();
            }
            if (p.getPonto().getY() > maxY) {
                maxY = (int) p.getPonto().getY();
            }
            if (p.getPonto().getX() < minX) {
                minX = (int) p.getPonto().getX();
            }
            if (p.getPonto().getY() < minY) {
                minY = (int) p.getPonto().getY();
            }
        }
        max.setLocation(maxX, maxY);
        min.setLocation(minX, minY);
    }

}
