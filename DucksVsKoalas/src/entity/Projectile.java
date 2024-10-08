package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import error.CustomAbstractError;

public class Projectile extends Entity {
	public int damage;
	public BufferedImage image;
	
	public void getImage() throws CustomAbstractError {
		if (this instanceof Projectile) {
			throw new CustomAbstractError(this.getClass());
		}
	}

	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
	public void update() {
		super.update();
	}
}
