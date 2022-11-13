/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sterowanieautem;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import utils.Auto;
import utils.MenadzerTrasa;
import utils.Przeszkoda;

/**
 *
 * @author serv
 */
public class PanelRysowania extends javax.swing.JPanel {

    private MenadzerTrasa menadzer;

    private boolean dodaniePrzejzdu;  // gdy zmiena false dodajemy przeszkody jak true to auto i zmieniamy zpowrotem
                                      // zmienna zmieania klikniecie w przycisk dodaj aut wywolujac seter a klikniecie ustawia auto 
    private boolean auto_ustawione;     // zmienna informujaca czy auto zostalo dodane 

    public PanelRysowania() {
        this.menadzer = new MenadzerTrasa();
        this.addMouseListener(new MouseAdapter() { // dodanie slchacza na klikniecie
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dodaniePrzejzdu == false) {
                    menadzer.dodajPrzeszkode(e.getX(), e.getY());
                } else {
                    if (auto_ustawione == false) {
                        menadzer.dodajAuto(e.getX(), e.getY());
                        auto_ustawione = true;
                        dodaniePrzejzdu = false;
                    }
                }
                repaint();
            }

        });
    }

    @Override
    public synchronized void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g);

        g2d.setStroke(new BasicStroke(5)); // ustawienie grubosci lini
        g2d.setColor(Color.GREEN);
        g2d.drawLine(830, 0, 830, 400);  // rusowanie mety
        if (menadzer.getPrzeszkody() != null) {
            for (Przeszkoda p : menadzer.getPrzeszkody()) { // rysowanie przeszkod na panelu
                g2d.setPaint(p.getKolor());
                g2d.fillRect(p.getX() - p.getWidth() / 2, p.getY() - p.getHeight() / 2, p.getWidth(), p.getHeight());

            }
        }
        if (menadzer.getAuto() != null && auto_ustawione) { // rysowanie auta jesli zostalo ustawione
            Auto auto = menadzer.getAuto();
            g2d.setPaint(auto.getKolor());
            g2d.fillOval(auto.getX() - auto.getWidth() / 2, auto.getY() - auto.getWidth() / 2, auto.getWidth(), auto.getWidth());

        }

    }

    public void resetujProgram() {
        this.menadzer.resetujProgram();
        auto_ustawione = false;
        repaint();
    }

    public boolean isDodaniePrzejzdu() {
        return dodaniePrzejzdu;
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


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
