package panels;

import lombok.SneakyThrows;
import objects.Predator;
import objects.Prey;
import objects.RoadManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;


public class SimulationPanel extends JPanel {

    private RoadManager manager;

    private boolean preyAddButtonClicked;  // zmienna informująca czy przycisk dodania ofary został klikniety
    private boolean preySet;     // zmienna informujaca czy ofaria zostalo dodane

    BufferedImage backgroundImage;

    @SneakyThrows
    public SimulationPanel() {

        this.manager = new RoadManager();

        backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("Background.png")));

        this.addMouseListener(new MouseAdapter() { // dodanie slchacza na klikniecie
            @Override
            public void mouseClicked(MouseEvent e) {
                if (preyAddButtonClicked == false) {
                    manager.addPredator(e.getX(), e.getY());
                } else if (preySet == false) {
                    manager.addPrey(e.getX(), e.getY());
                    preySet = true;
                    preyAddButtonClicked = false;
                } else {
                    manager.addPredator(e.getX(), e.getY());
                }

                repaint();
            }

        });


    }


    @SneakyThrows
    @Override
    public synchronized void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);

        g2d.setStroke(new BasicStroke(1)); // ustawienie grubosci lini
        g2d.setColor(Color.GREEN);
        g2d.drawLine(450, 0, 450, 1000);  // rysowanie mety


        if (manager.getPredators() != null) {

            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("wolf.png")));

            // rysowanie predatorów na panelu
            for (Predator p : manager.getPredators()) {

                g.drawImage(image, p.getX() - p.getWidth() / 2, p.getY() - p.getHeight() / 2, null);

            }
        }

        if (manager.getPrey() != null && preySet) { // rysowanie ofiary jesli została ustawiona

            Prey prey = manager.getPrey();

            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("deer.png")));

            g.drawImage(image, prey.getX() - prey.getWidth() / 2, prey.getY() - prey.getHeight() / 2, null);

        }

    }

    public void resetProgram() {
        this.manager.resetProgram();
        preySet = false;
        repaint();
    }


    public void setPreyAddButtonClicked(boolean preyAddButtonClicked) {
        this.preyAddButtonClicked = preyAddButtonClicked;
    }

    public boolean makeMoveRepaint() {

        if (manager.makeMove(this)) { // sprawdzenie czy ruch mozna wykonac
            repaint(); // malowanie
            return true;
        } else {
            return false; // zwrocenie informacji ze ruch jest niewykonalny
        }
    }


}
