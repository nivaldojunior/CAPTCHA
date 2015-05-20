package model;

import java.awt.Color;
import java.awt.Point;

public class Pixel {

    private Color cor;
    private Point ponto;

    public Pixel(Color cor, Point ponto) {
        this.cor = cor;
        this.ponto = ponto;
    }

    public Pixel(Point ponto) {
        this.ponto = ponto;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public Point getPonto() {
        return ponto;
    }

    public void setPonto(Point ponto) {
        this.ponto = ponto;
    }

}
