package controll;


import lombok.SneakyThrows;
import panels.ManagementPanel;
import panels.SimulationPanel;
import threads.AnimationThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;


public class ApplicationWindow extends JFrame {

    private JButton addPreyButton;
    private JButton startButton;
    private JButton resetButton;
    private ManagementPanel managementPanel;
    private SimulationPanel simulationPanel;

    private JFrame frame;
    public static boolean animationEnabled;

    private AnimationThread animationThread;


    public ApplicationWindow() {

        initComponents();

        frame = this;
        setResizable(false);
        setTitle("Prey and Predators");
        setVisible(true);
        setSize(new Dimension(1000, 500));
        centreWindow(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }


    private void initComponents() {


        createButtons();
        createPanel();


    }

    @SneakyThrows
    private void createPanel() {

        setLayout(new GridLayout(1, 2));

        managementPanel = new ManagementPanel();
        managementPanel.setLayout(new BorderLayout());

        JPanel divPanel = new JPanel();
        divPanel.setBackground(new Color(51, 26, 0) );
        divPanel.setSize(new Dimension(200, 200));

        divPanel.add(addPreyButton);
        divPanel.add(startButton);
        divPanel.add(resetButton);


        managementPanel.add(divPanel, BorderLayout.NORTH);


        simulationPanel = new SimulationPanel();
        simulationPanel.setBackground(new Color(0, 0, 0));
        simulationPanel.setSize(new Dimension(500, 500));


        add(managementPanel);
        add(simulationPanel);

    }

    private JButton createOwnButtons(String text) {

        JButton jButton = new JButton(text);
        jButton.setBackground(Color.WHITE);
        jButton.setForeground(Color.BLACK);

        return jButton;
    }

    private void createButtons() {

        addPreyButton = createOwnButtons("Dodaj ofiarę");
        startButton = createOwnButtons("Start");
        resetButton = createOwnButtons("Resetuj");

        addPreyButton.addActionListener(this::setPreyAddButtonState);
        startButton.addActionListener(evt -> startThreadWithHunting());
        resetButton.addActionListener(evt -> reset());


    }


    private void setPreyAddButtonState(ActionEvent evt) {

        simulationPanel.setPreyAddButtonClicked(true);
    }

    private void startThreadWithHunting() {

        if (!animationEnabled) {
            animationEnabled = true;
            animationThread = new AnimationThread(simulationPanel, this);
            animationThread.start();
        } else {
            JOptionPane.showMessageDialog(frame, "Animacja trwa.");
        }

    }

    private void reset() {

        if (animationThread != null && !animationThread.isInterrupted()) {
            animationThread.interrupt();
        }

        simulationPanel.resetProgram();

    }


}
