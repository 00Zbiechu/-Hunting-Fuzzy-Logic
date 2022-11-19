package sterowanie;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;


public class ApplicationWindow extends JFrame {


    private JSpinner animationSpeed;
    private JButton addRideButton;
    private JButton startButton;
    private JButton resetButton;
    private JLabel speedOfAnimationLabel;
    private ManagementPanel managementPanel;
    private SimulationPanel simulationPanel;

    private JFrame frame;
    private boolean animacjaWlaczona;
    private Thread watekAnimacji;


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


        createJLabels();
        createButtons();
        createSpinner();
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

                    panelPomocniczy.add(speedOfAnimationLabel);
                    panelPomocniczy.add(animationSpeed);

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

    private void createJLabels() {


        speedOfAnimationLabel = new JLabel();
            speedOfAnimationLabel.setText("Predkośc animacji");



    }


    private void createSpinner() {

        animationSpeed = new JSpinner();
        animationSpeed.setModel(new SpinnerNumberModel(10, 10, 50, 1));
        animationSpeed.setValue(10);

    }




    private void setAddRide(ActionEvent evt) {

        simulationPanel.setDodaniePrzejzdu(true);
    }

    private void startRide() {

        if (!animacjaWlaczona) {                                                // sprawdzenie czy animacja jest wlaczona
            animacjaWlaczona = true;
            // wlaczenie animacji
            // tworzenie watku dla animacji
            watekAnimacji = new Thread(() -> {

                int liczbaAnimacj = liczbaAnimacjiNaSekunde();              // pobranie liczby animacji na sekunde od 10 do 50
                while (animacjaWlaczona) {                                  // petla sprawdzajaca warunek konca czy osiagnieto koniec mapy
                    animacjaWlaczona = simulationPanel.wykonajRuch();       // wykonania ruchu oraz sprawdzenie czy ruch jest mozliwy
                    try {
                       Thread.sleep(liczbaAnimacj);                 // uspienie watku
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ApplicationWindow.class.getName()).log(Level.SEVERE, null, ex);
                        Thread.currentThread().interrupt(); // restore interrupted status
                    }
                }
                JOptionPane.showMessageDialog(frame, "Animacja zakończona");// poinformowanie uzytkownika o zakonczeniu animacji
                simulationPanel.resetujProgram();
                watekAnimacji.interrupt();
                animacjaWlaczona=false;    // po wyjsciu z petli watek zostaje zniszczony
            });
            watekAnimacji.start();                                              // wystartowanie watku
        }
    }

    private void reset() {

        // poinformowanie uzytkownika o zakonczeniu animacji
       if(!watekAnimacji.isInterrupted()){ // sprawdzenie
         watekAnimacji.interrupt();
        }

        simulationPanel.resetujProgram();

    }

    private synchronized int liczbaAnimacjiNaSekunde() {
        return 1000 / Integer.parseInt(animationSpeed.getValue().toString());
    }


}
