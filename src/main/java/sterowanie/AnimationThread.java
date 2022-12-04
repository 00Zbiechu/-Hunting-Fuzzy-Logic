package sterowanie;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;

@AllArgsConstructor
public class AnimationThread extends Thread{


    private SimulationPanel simulationPanel;


    private JFrame frame;




    public void run(){


        int liczbaAnimacj = 1000/20;              // pobranie liczby animacji na sekunde od 10 do 50
        while (ApplicationWindow.animacjaWlaczona) {                                  // petla sprawdzajaca warunek konca czy osiagnieto koniec mapy
            ApplicationWindow.animacjaWlaczona = simulationPanel.wykonajRuch();       // wykonania ruchu oraz sprawdzenie czy ruch jest mozliwy

            try {

                sleep(liczbaAnimacj);                 // uspienie watku

            } catch (InterruptedException ex) {
                ApplicationWindow.animacjaWlaczona = false;
                interrupt();
            }
        }
        JOptionPane.showMessageDialog(frame, "Animacja zakończona");// poinformowanie uzytkownika o zakonczeniu animacji
        simulationPanel.resetujProgram();
        interrupt();
        ApplicationWindow.animacjaWlaczona=false;    // po wyjsciu z petli watek zostaje zniszczony
        }


    }

