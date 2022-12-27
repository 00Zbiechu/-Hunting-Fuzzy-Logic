package panels;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ManagementPanel extends JPanel {

    BufferedImage backgroundImage;

    @SneakyThrows
    public ManagementPanel(){

        backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("managementBackgound.png"));

    }



    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

}
