package utils;

import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.util.Random;

@Getter
@Setter
public class Przeszkoda {
    private int x;
    private int y;
    private int width = 10; // nie final bo nie połaczy sie przeszkod
    private int height = 10;
    private Color kolor;


    public Przeszkoda(int x, int y) {
        this.x = x;
        this.y = y;
        this.setKolor(new Color(255,0,0));
    }


    
    public double policzOdleglosc(Auto auto){

        //ziomek to z pitagorasa chyba liczy XD

        // zwaracamy najbliszy punkt wedlug rowania r = Math.sqrt( (x2-x1)^2 + (y2-y1)^2)
        return Math.sqrt(Math.pow(this.x-auto.getX(),2)+Math.pow(this.y-auto.getY(),2));
    }
    

}
