package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Koala extends Monster {

	GamePanel gp;
	
	public Koala() {
	}
	
	public Koala(int x, int y, GamePanel gp) {
		this.x = x;
		this.y = y;
		this.gp = gp;
		getImage();
		setDefaultValues();
	}
	
	public void getImage() {
	    try {
	        System.out.println("Loading monster image...");
	        this.image = ImageIO.read(getClass().getResourceAsStream("/monster/koala.png"));
	        System.out.println("Monster image loaded successfully");
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Monster image failed to load");
	    } catch (Throwable e) {
	    	e.printStackTrace();
	    	System.out.println("Something failed to load");
	    }
	}
	
	public void setDefaultValues() {
		this.boundingBox = new Rectangle();
		boundingBox.setSize(16 * gp.spriteScale, 16 * gp.spriteScale);
		damage = 1;
		health = 1;
		velocityX = -6;
	}
	public void update() {
		super.update();
	}
	public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, x, y, 16 * gp.spriteScale, 16 * gp.spriteScale, null);
            // DEBUG graphics
            if (gp.debug) {
                g2.setColor(Color.green);
                g2.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
                g2.drawString("VelocityX: " + velocityX, x + gp.spriteScale * 16 + 10, y + 15);
                g2.drawString("VelocityY: " + velocityY, x + gp.spriteScale * 16 + 10, y + 45);
                g2.drawString("Position: (" + x + ", " + y + ")", x + gp.spriteScale * 16 + 10, y + 75);
            }
        } else {
            System.out.println("Monster image is null!");  // Debug message
        }
	}
}
