package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Burger extends Projectile {

	public Burger(int x, int y, GamePanel gp) {
		this.x = x;
		this.y = y;
		velocityX = 12;
		damage = 1;
		getBurgerImage();
		setDefaultValues();
	}
	public void setDefaultValues() {
		this.boundingBox = new Rectangle();
	}
	public void getBurgerImage() {
	    try {
	        System.out.println("Loading burger image...");
	        this.image = ImageIO.read(getClass().getResourceAsStream("/projectile/burger.png"));
	        System.out.println("Burger image loaded successfully");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void update() {
		super.update();
	}
}
