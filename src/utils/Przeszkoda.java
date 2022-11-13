package utils;

import java.awt.Color;
import java.util.Random;


public class Przeszkoda {
    private int x;
    private int y;
    private int width = 10; // nie final bo nie połaczy sie przeszkod
    private int height = 10;
    private Color kolor;

    public Przeszkoda() {
    }

    public Przeszkoda(int x, int y) {
        this.x = x;
        this.y = y;
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        this.setKolor(new Color(255,0,0));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getKolor() {
        return kolor;
    }

    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }
    
    public double policzOdleglosc(Auto auto){
        double r=0;
        // zwaracamy najbliszy punkt wedlug rowania r = Math.sqrt( (x2-x1)^2 + (y2-y1)^2)
        r=Math.sqrt(Math.pow(this.x-auto.getX(),2)+Math.pow(this.y-auto.getY(),2));
        return r;
    }
    

}
