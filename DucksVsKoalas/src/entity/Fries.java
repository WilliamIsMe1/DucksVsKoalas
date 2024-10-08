package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Fries extends Projectile {
	GamePanel gp;
	
	public Fries() {
	}
	public Fries(int x, int y, GamePanel gp) {
		this.x = x;
		this.y = y;
		velocityX = 16;
		damage = 1;
		this.gp = gp;
		getImage();
		setDefaultValues();
	}
	public void setDefaultValues() {
		this.boundingBox = new Rectangle();
		boundingBox.setSize(14 * gp.spriteScale, 13 * gp.spriteScale);
	}
	@Override
	public void getImage() {
	    try {
	        System.out.println("Loading fries image...");
	        this.image = ImageIO.read(getClass().getResourceAsStream("/projectile/fries.png"));
	        System.out.println("Fries image loaded successfully");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void update() {
		super.update();
	}
	
	public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, x, y, 14 * gp.spriteScale, 13 * gp.spriteScale, null);
            // DEBUG graphics
            if (gp.debug) {
                g2.setColor(Color.green);
                g2.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
                g2.drawString("VelocityX: " + velocityX, x + gp.spriteScale * 16 + 10, y + 15);
                g2.drawString("VelocityY: " + velocityY, x + gp.spriteScale * 16 + 10, y + 45);
                g2.drawString("Position: (" + x + ", " + y + ")", x + gp.spriteScale * 16 + 10, y + 75);
            }
        } else {
            System.out.println("Projectile image is null!");  // Debug message
        }
	}
}
