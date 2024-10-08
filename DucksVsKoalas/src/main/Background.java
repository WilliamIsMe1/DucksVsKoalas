package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
    GamePanel gp;
    BufferedImage img;
    int backgroundX, backgroundY;
    double scrollSpeed;

    public Background(GamePanel gp, double scrollSpeed) {
        this.gp = gp;
        this.scrollSpeed = scrollSpeed;
        getBackgroundImage();
    }

    public void getBackgroundImage() {
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/backgrounds/stars.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        backgroundX -= scrollSpeed;

        if (backgroundX <= -gp.screenWidth) {
            backgroundX = 0;
        }
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(img, backgroundX, backgroundY, gp.screenWidth, gp.screenHeight, null);
        g2.drawImage(img, backgroundX + gp.screenWidth, backgroundY, gp.screenWidth, gp.screenHeight, null);
    }
}
