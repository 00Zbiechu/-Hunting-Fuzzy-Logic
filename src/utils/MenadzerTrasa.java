package utils;

import java.util.ArrayList;
import sterowanieautem.LogikaRozmyta;
import sterowanieautem.PanelRysowania;

public class MenadzerTrasa {


    private ArrayList<Przeszkoda> przeszkody;               // lista przeszkod
    private Auto auto;                                      // auto ktorym bedziemy sterowac

    private double os_x;                                    // wspolrzedne wyjsciowe slurzace do sterowania
    private double os_y;

    private final int czuloscRuchu = 4;                       // zmiena odpowiedzialna za czulosc ruchu dla kazdego zbioru jesli dokonamy zmiany musi byc inna , by ruch wydawal sie liniowy nie skokowy
    private LogikaRozmyta fuzzy;
    public MenadzerTrasa() {
        this.przeszkody = new ArrayList<>();
        fuzzy = new LogikaRozmyta();

    }
    public void resetujProgram() {
        this.przeszkody = new ArrayList<>(); // zerowanie punktow
        auto = null;                        // kasowanie auta
    }
    public void dodajPrzeszkode(int x, int y) {
        this.przeszkody.add(new Przeszkoda(x, y));
    }
    public ArrayList<Przeszkoda> getPrzeszkody() {
        return przeszkody;
    }
    public void dodajAuto(int x, int y) {
        this.auto = new Auto(x, y);

    }
    public boolean wykonajRuch(PanelRysowania panel) {                     // ruch wykonujemy tak dopoki nie ominiemy przeszkody
        if(auto!=null) {                                                                // pobranie najbliszego punktu do ominiecja
            if (auto.getX() < panel.getWidth() - auto.getWidth() / 2) {     // auto nie jest na koncu mapy nastepuje ruch
                Przeszkoda najblisza = przeszkody.get(0);
                double r = 0;
                for (int i = 1; i < przeszkody.size(); i++) {                       // szukanie najbliszego punktu do ominiecia
                    Przeszkoda temp = przeszkody.get(i);
                    r = najblisza.policzOdleglosc(auto);
                    if (r > temp.policzOdleglosc(auto)) {                          // jesli znajdziemy blizszy zamieniamy
                        najblisza = temp;
                    }
                }
                int rx = najblisza.getX() - auto.getX();                           // pobranie roznicy medzy punktami
                int ry = najblisza.getY() - auto.getY();
                System.out.println("rx " + rx + "  ry " + ry);
                if (Math.abs(rx) > 100) {                                         // maksymalna wartosc i minimalna zbiorow dla wartosci wejsciowych wynosi -100 i 100 
                    rx = rx > 0 ? 100 : -100;
                }
                if (Math.abs(ry) > 100) {
                    ry = ry > 0 ? 100 : -100;
                }
                fuzzy.Fuzzify(rx, ry);                                      // obliczenia zbiorow
                os_x = fuzzy.GetDirectionX();       //pobraniemetodasierodka ciezkosci warrtosci wyjsciowych 
                os_y = fuzzy.GetDirectionY();
                System.out.println("::os_x " + os_x + "  os_y " + os_y);
                auto.setX(auto.getX() + (int) os_x / czuloscRuchu);             // ustawianie wspolrzednych 4 oznacza czuloscruchu
                auto.setY(auto.getY() - (int) os_y / czuloscRuchu);
                return true;
        } else {      
            return false; // auto dojechalo do mety
        }
        }else {      
            return false; // auto dojechalo do mety
        }
    }

    public Auto getAuto() {
        return auto;
    }
    public void setAuto(Auto auto) {
        this.auto = auto;
    }
    // metody zwaracaj wartosc do Okna i wyswietlane sa w labelce ster os x i ster os y
    public double getOs_x() {
        return os_x;
    }
    public double getOs_y() {
        return os_y;
    }
}
