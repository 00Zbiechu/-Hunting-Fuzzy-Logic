package sterowanie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import lombok.SneakyThrows;
import utils.Prey;
import utils.MenadzerTrasa;
import utils.Predator;

import javax.imageio.ImageIO;
import javax.swing.*;


public class PanelRysowania extends JPanel {

    private MenadzerTrasa menadzer;

    private boolean dodaniePrzejzdu;  // gdy zmiena false dodajemy przeszkody jak true to auto i zmieniamy zpowrotem
                                      // zmienna zmieania klikniecie w przycisk dodaj aut wywolujac seter a klikniecie ustawia auto 
    private boolean preySet;     // zmienna informujaca czy auto zostalo dodane

    public PanelRysowania() {
        this.menadzer = new MenadzerTrasa();
        this.addMouseListener(new MouseAdapter() { // dodanie slchacza na klikniecie
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dodaniePrzejzdu == false) {
                    menadzer.addPredator(e.getX(), e.getY());
                } else {
                    if (preySet == false) {
                        menadzer.addPrey(e.getX(), e.getY());
                        preySet = true;
                        dodaniePrzejzdu = false;
                    }
                }
                repaint();
            }

        });
    }

    private void setBackgoundAsForest(){



    }

    @SneakyThrows
    @Override
    public synchronized void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g);

        g2d.setStroke(new BasicStroke(5)); // ustawienie grubosci lini
        g2d.setColor(Color.GREEN);
        g2d.drawLine(830, 0, 830, 400);  // rusowanie mety

        if (menadzer.getPredators() != null) {

            File path = new File("src/main/resources");
            BufferedImage image = ImageIO.read(new File(path, "wolf.png"));

            // rysowanie przeszkod na panelu
            for (Predator p : menadzer.getPredators()) {

                g.drawImage(image, p.getX() - p.getWidth() / 2,  p.getY() - p.getHeight() / 2, null);

            }
        }
        if (menadzer.getPrey() != null && preySet) { // rysowanie ofiary jesli została ustawiona

            Prey prey = menadzer.getPrey();

            File path = new File("src/main/resources");
            BufferedImage image = ImageIO.read(new File(path, "deer.png"));

            g.drawImage(image, prey.getX() - prey.getWidth() / 2,  prey.getY() - prey.getHeight() / 2, null);

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

            return false; // zwrocenie informacji ze ruch jest nie wykonalny
        }
    }

    public MenadzerTrasa getMenadzer() {
        return menadzer;
    }


//    private void initComponents() {
//
//        setBackground(new java.awt.Color(153, 153, 153));
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 400, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 300, Short.MAX_VALUE)
//        );
//    }
}
