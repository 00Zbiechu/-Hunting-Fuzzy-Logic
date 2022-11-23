package sterowanie;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;


public class ApplicationWindow extends JFrame {


    private JButton addRideButton;
    private JButton startButton;
    private JButton resetButton;
    private ManagementPanel managementPanel;
    private SimulationPanel simulationPanel;

    private JFrame frame;
    private boolean animacjaWlaczona;
    //private Thread watekAnimacji;

    private AnimationThread animationThread;



    public ApplicationWindow() {

        initComponents();

        frame = this;
            setResizable(false);
            setTitle("Prey and Predators");
            setVisible(true);
            setSize(new Dimension(1000,500));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }


    private void initComponents() {


        createButtons();
        createPanel();


    }

    private void createPanel() {

        setLayout(new GridLayout(1,2));

        managementPanel = new ManagementPanel();
        managementPanel.setLayout(new BorderLayout());

        JPanel panelPomocniczy = new JPanel();
            panelPomocniczy.setSize(new Dimension(200,200));

            panelPomocniczy.add(addRideButton);
            panelPomocniczy.add(startButton);
            panelPomocniczy.add(resetButton);


                    managementPanel.add(panelPomocniczy,BorderLayout.NORTH);




        simulationPanel = new SimulationPanel();
            simulationPanel.setBackground(new Color(0, 0, 0));
            simulationPanel.setSize(new Dimension(500,500));


            add(managementPanel);
            add(simulationPanel);

    }

    private JButton createOwnButtons(String text){

        JButton jButton = new JButton(text);
            jButton.setBackground(Color.WHITE);
            jButton.setForeground(Color.BLACK);

            return jButton;
    }

    private void createButtons() {

        addRideButton = createOwnButtons("Dodaj ofiarę");
        startButton =  createOwnButtons("Start");
        resetButton = createOwnButtons("Resetuj");

            addRideButton.addActionListener(this::setAddRide);
            startButton.addActionListener(evt -> startRide());
            resetButton.addActionListener(evt -> reset());


    }








    private void setAddRide(ActionEvent evt) {

        simulationPanel.setDodaniePrzejzdu(true);
    }

    private void startRide() {

        if (!animacjaWlaczona) {                                                // sprawdzenie czy animacja jest wlaczona
            animacjaWlaczona = true;
            // wlaczenie animacji
            // tworzenie watku dla animacji
            animationThread = new AnimationThread(simulationPanel, animacjaWlaczona, this);

        }
        animationThread.start();

    }

    private void reset() {

        // poinformowanie uzytkownika o zakonczeniu animacji
       if(!animationThread.isInterrupted()){ // sprawdzenie
           animationThread.interrupt();
        }

        simulationPanel.resetujProgram();

    }



}
