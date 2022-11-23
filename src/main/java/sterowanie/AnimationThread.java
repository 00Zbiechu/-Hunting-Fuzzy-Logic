package sterowanie;

import lombok.AllArgsConstructor;

import javax.swing.*;

@AllArgsConstructor
public class AnimationThread extends Thread{


    private SimulationPanel simulationPanel;

    private boolean animacjaWlaczona;

    private JFrame frame;




    public void run(){


        int liczbaAnimacj = 1000/20;              // pobranie liczby animacji na sekunde od 10 do 50
        while (animacjaWlaczona) {                                  // petla sprawdzajaca warunek konca czy osiagnieto koniec mapy
            animacjaWlaczona = simulationPanel.wykonajRuch();       // wykonania ruchu oraz sprawdzenie czy ruch jest mozliwy

            try {

                sleep(liczbaAnimacj);                 // uspienie watku

            } catch (InterruptedException ex) {

                interrupt(); // restore interrupted status
            }
        }
        JOptionPane.showMessageDialog(frame, "Animacja zakończona");// poinformowanie uzytkownika o zakonczeniu animacji
        simulationPanel.resetujProgram();
        interrupt();
        animacjaWlaczona=false;    // po wyjsciu z petli watek zostaje zniszczony
        }


    }

