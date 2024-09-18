package entity;

import java.awt.Rectangle;

public class Entity {
	public int x, y;
	public int velocityX;
	public int velocityY;
	public void update() {
		this.x += velocityX;
		this.y += velocityY;
		boundingBox.setLocation(x, y);

	}
	public Rectangle boundingBox;
}
