package sterowanie;

import lombok.SneakyThrows;
import utils.Predator;
import utils.Prey;
import utils.RoadManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;


public class SimulationPanel extends JPanel {

    private RoadManager menadzer;

    private boolean dodaniePrzejzdu;  // gdy zmiena false dodajemy przeszkody jak true to auto i zmieniamy zpowrotem
    // zmienna zmieania klikniecie w przycisk dodaj aut wywolujac seter a klikniecie ustawia auto
    private boolean preySet;     // zmienna informujaca czy auto zostalo dodane

    BufferedImage backgroundImage;

    @SneakyThrows
    public SimulationPanel() {

        this.menadzer = new RoadManager();

        this.backgroundImage = ImageIO.read(new File("src/main/resources/Background.png"));

        this.addMouseListener(new MouseAdapter() { // dodanie slchacza na klikniecie
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dodaniePrzejzdu == false) {
                    menadzer.addPredator(e.getX(), e.getY());
                } else if (preySet == false) {
                    menadzer.addPrey(e.getX(), e.getY());
                    preySet = true;
                    dodaniePrzejzdu = false;
                } else {
                    dodaniePrzejzdu = false;
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
        g.drawImage(backgroundImage, 0, 0, null); //dodwanie tła panelu

        g2d.setStroke(new BasicStroke(5)); // ustawienie grubosci lini
        g2d.setColor(Color.RED);
        g2d.drawLine(450, 0, 450, 1000);  // rusowanie mety


        if (menadzer.getPredators() != null) {

            File path = new File("src/main/resources");
            BufferedImage image = ImageIO.read(new File(path, "wolf.png"));

            // rysowanie przeszkod na panelu
            for (Predator p : menadzer.getPredators()) {

                g.drawImage(image, p.getX() - p.getWidth() / 2, p.getY() - p.getHeight() / 2, null);

            }
        }

        if (menadzer.getPrey() != null && preySet) { // rysowanie ofiary jesli została ustawiona

            Prey prey = menadzer.getPrey();

            File path = new File("src/main/resources");
            BufferedImage image = ImageIO.read(new File(path, "deer.png"));

            g.drawImage(image, prey.getX() - prey.getWidth() / 2, prey.getY() - prey.getHeight() / 2, null);

        }

    }

    public void resetujProgram() {
        this.menadzer.resetujProgram();
        preySet = false;
        repaint();
    }


    public void setDodaniePrzejzdu(boolean dodaniePrzejzdu) {
        this.dodaniePrzejzdu = dodaniePrzejzdu;
    }

    public boolean wykonajRuch() {

        if (menadzer.wykonajRuch(this)) { // sprawdzenie czy ruch mozna wykonac
            repaint(); // malowanie
            return true;
        } else {
            return false; // zwrocenie informacji ze ruch jest niewykonalny
        }
    }

    public RoadManager getMenadzer() {
        return menadzer;
    }


}
