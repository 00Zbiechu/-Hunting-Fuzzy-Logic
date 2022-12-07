package threads;

import controll.ApplicationWindow;
import lombok.AllArgsConstructor;
import panels.SimulationPanel;

import javax.swing.*;

@AllArgsConstructor
public class AnimationThread extends Thread {


    private SimulationPanel simulationPanel;


    private JFrame frame;


    public void run() {


        int animationCount = 1000 / 20;    //animacje na sekunde ( w tym przypadku 20 animacji/s)
        while (ApplicationWindow.animationEnabled) {
            ApplicationWindow.animationEnabled = simulationPanel.makeMoveRepaint(); // wykonania ruchu oraz sprawdzenie czy ruch jest mozliwy

            try {

                sleep(animationCount);                 // uspienie watku na 1000/20 ms = 50 ms

            } catch (InterruptedException ex) {
                interrupt();
                ApplicationWindow.animationEnabled = false;
            }
        }
        JOptionPane.showMessageDialog(frame, "Animacja zakończona");// poinformowanie uzytkownika o zakonczeniu animacji
        simulationPanel.resetProgram();
        if (isInterrupted()) {
            interrupt();
            ApplicationWindow.animationEnabled = false;    // po wyjsciu z petli watek zostaje zniszczony
        }
    }


}

