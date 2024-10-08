package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import error.CustomAbstractError;

public class Monster extends Entity {
	public int damage;
	public BufferedImage image;
	public int health;
	
	public void getImage() throws CustomAbstractError {
		if (this instanceof Monster) {
			throw new CustomAbstractError(this.getClass());
		}
	}

	public void draw(Graphics2D g2) {
		
	}
	public void update() {
		super.update();
	}
}
