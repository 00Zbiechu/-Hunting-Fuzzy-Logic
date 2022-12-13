package objects;

import lombok.Data;
import controll.FuzzyLogic;
import lombok.Getter;
import panels.SimulationPanel;

import javax.swing.*;
import java.util.ArrayList;

public class RoadManager {

    private ArrayList<Predator> predators;
    private Prey prey;

    @Getter
    private double os_x;  // wspolrzedne wyjsciowe do sterowania

    @Getter
    private double os_y;

    private final int moveSensitive = 4; // zmiena odpowiedzialna za czulosc ruchu dla kazdego zbioru jesli dokonamy zmiany musi byc inna , by ruch wydawal sie liniowy nie skokowy
    private FuzzyLogic fuzzy;

    public RoadManager() {
        this.predators = new ArrayList<>();
        fuzzy = new FuzzyLogic();

    }


    public Prey getPrey() {
        return prey;
    }


    public void resetProgram() {

        this.predators = new ArrayList<>(); // zerowanie drapieżników
        prey = null;                        // kasowanie ofiary

    }

    public void addPredator(int x, int y) {

        this.predators.add(new Predator(x, y));

    }

    public ArrayList<Predator> getPredators() {

        return predators;

    }

    public void addPrey(int x, int y) {
        this.prey = new Prey(x, y);

    }

    public boolean makeMove(SimulationPanel panel) {


        if (prey == null) {
            return false;
        }

        // sprawdzenie czy ofara doszła do mety
        if (prey.getX() >= panel.getWidth() - prey.getWidth() / 2) {
            return false;
        }

        //sprawdzenie czy dodano jakiegos predatora
        if (predators.isEmpty()) {
            return false;
        }

        // pobranie pierwszego predatora z listy
        Predator closestPredator = predators.get(0);

        double r = 0;

        // szukanie najbliszego predatora do ominiecia
        for (int i = 1; i < predators.size(); i++) {

            Predator temp = predators.get(i);

            r = closestPredator.getDistanceFromPrey(prey);

            if (r > temp.getDistanceFromPrey(prey)) {
                // jesli znajdziemy blizszy zamieniamy
                closestPredator = temp;

            }

        }

        // pobranie roznicy medzy drapieżnikiem a ofiarą
        int rx = closestPredator.getX() - prey.getX();
        int ry = closestPredator.getY() - prey.getY();


        if (Math.abs(rx) > 100) {  // maksymalna wartosc i minimalna zbiorow dla wartosci wejsciowych wynosi -100 i 100
            rx = rx > 0 ? 100 : -100;
        }
        if (Math.abs(ry) > 100) {
            ry = ry > 0 ? 100 : -100;
        }
        fuzzy.fuzzify(rx, ry);  // obliczenia zbiorow
        os_x = 12.48d;
        os_y = fuzzy.getOutY(); //pobranie metody środka ciężkości wartości wyjsciowych

        prey.setX(prey.getX() + (int) os_x / moveSensitive); // ustawianie wspolrzednych 4 oznacza czulosc ruchu
        prey.setY(prey.getY() - (int) os_y / moveSensitive);

        //Poruszanie predatorami na podstawie pozycji ofiary + przypadek schwytania ofiary
        for (Predator p : predators) {


            // sprawdzenie odleglosci miedzy predatorem, a ofiara jesli < 15 to ofiara schwytana
            if (p.getDistanceFromPrey(prey) < 15) {

                JOptionPane.showMessageDialog(panel, "Ofiara została schwytana.");
                panel.resetProgram();
                return false;
            }


             //poruszanie wilka po osi x
                if (p.getX() < prey.getX()) {
                    p.setX(p.getX() + 1);
                } else {
                    p.setX(p.getX() - 1);
                }

            //poruszanie wilka po osi y
                if (p.getY() < prey.getY()) {
                    p.setY(p.getY() + 1);
                } else {
                    p.setY(p.getY() - 1);
                }

            }




        return true;


    }


}
