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
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private ManagementPanel managementPanel;
    private SimulationPanel simulationPanel;

    private JLabel valueOsX;
    private JLabel valueOsY;
    private JFrame frame;
    private boolean animacjaWlaczona;
    private Thread watekAnimacji;
    private boolean pause;

    public ApplicationWindow() {

        initComponents();

        frame = this;
            setResizable(false);
            setTitle("Prey and Predators");
            setVisible(true);
            setSize(new Dimension(1000,500));
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

                    panelPomocniczy.add(jLabel3);
                    panelPomocniczy.add(animationSpeed);

                    managementPanel.add(panelPomocniczy,BorderLayout.NORTH);

//                managementPanel.add(jLabel1);
//                managementPanel.add(valueOsX);
//                managementPanel.add(jLabel2);
//                managementPanel.add(valueOsY);



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


        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        valueOsX = new JLabel();
        valueOsY = new JLabel();


        jLabel1.setText("ster_os_y");
        jLabel2.setText("ster_os_x");
        jLabel3.setText("Predkośc animacji");

        valueOsX.setText("value");
        valueOsY.setText("value");

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
                        valueOsX.setText("" + simulationPanel.getMenadzer().getOs_x());            // pobranie wartosci
                        valueOsY.setText("" + simulationPanel.getMenadzer().getOs_y());
                       Thread.sleep(liczbaAnimacj);                 // uspienie watku
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ApplicationWindow.class.getName()).log(Level.SEVERE, null, ex);
                        Thread.currentThread().interrupt(); // restore interrupted status
                    }
                }
                JOptionPane.showMessageDialog(frame, "Animacja zakończona");// poinformowanie uzytkownika o zakonczeniu animacji
                valueOsX.setText("value os x");            // pobranie wartosci
                valueOsY.setText("value os y");
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
        valueOsX.setText("value os x");            // pobranie wartosci
        valueOsY.setText("value os y");

    }

    private synchronized int liczbaAnimacjiNaSekunde() {
        return (int) 1000 / Integer.parseInt(animationSpeed.getValue().toString());
    }


}
