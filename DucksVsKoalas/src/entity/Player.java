package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	
	BufferedImage up, center, down, up2, center2, down2;
	// STATS
	
	public int hp;
	public int ammo;

	// STATE
	
	public String direction;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
	    hp = 3;
	    ammo = 10;
	    x = 16 * gp.spriteScale;
	    y = 384 - 8 * gp.spriteScale;
	    direction = "idle";
	    
	    // Set the boundingBox size to 16 * gp.spriteScale by 16 * gp.spriteScale
	    boundingBox = new Rectangle(x + (4 * gp.spriteScale), y, 12 * gp.spriteScale, 9 * gp.spriteScale);
	}

	public void getPlayerImage() {
		
		try {
			
			this.up = ImageIO.read(getClass().getResourceAsStream("/player/up.png"));
			this.up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
			this.center = ImageIO.read(getClass().getResourceAsStream("/player/center.png"));
			this.center2 = ImageIO.read(getClass().getResourceAsStream("/player/center2.png"));
			this.down = ImageIO.read(getClass().getResourceAsStream("/player/down.png"));
			this.down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));

			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
	    // Draw the player
		BufferedImage image = null;
		switch(direction) {
		case "up":
			image = up;
			break;
		case "center":
			image = center;
			break;
		case "down":
			image = down;
			break;
		}
		g2.drawImage(image, x - (4*gp.spriteScale), y - (4*gp.spriteScale), 16 * gp.spriteScale, 16 * gp.spriteScale, null);
		if (gp.debug) {
		    // Set the text color
		    g2.setColor(Color.green);
		    
		    // Set a larger font size (e.g., double the default size)
//		    Font originalFont = g2.getFont();  // Store the original font
//		    Font largerFont = originalFont.deriveFont(originalFont.getSize() * 2.0f);  // Create a larger font
//		    g2.setFont(largerFont);  // Set the new font
		    
		    // Draw velocity and position info next to the square
		    g2.drawString("VelocityX: " + velocityX, x + gp.spriteScale * 16 + 10, y + 15);
		    g2.drawString("VelocityY: " + velocityY, x + gp.spriteScale * 16 + 10, y + 45);
		    g2.drawString("Position: (" + x + ", " + y + ")", x + gp.spriteScale * 16 + 10, y + 75);
		    
		    // Draw the bounding box as a green outline
		    g2.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
		    
		    // Reset to the original font after drawing
//		    g2.setFont(originalFont);
		    
		    // Set the text color back to white
		    g2.setColor(Color.white);
		}

	}

	public void update() {

	    // Update velocityY based on key presses
	    if (keyH.upPressed == true || keyH.downPressed == true) {
	        if (keyH.upPressed == true && keyH.downPressed == false && velocityY > -8) {
	            velocityY -= 1;
	        }
	        if (keyH.upPressed == false && keyH.downPressed == true && velocityY < 8) {
	            velocityY += 1;
	        }
	    } else {
	        // Smooth deceleration when no key is pressed
	        if (velocityY > 0) {
	            velocityY -= 1;
	        } else if (velocityY < 0) {
	            velocityY += 1;
	        }
	    }

	    // Prevent moving above or below the screen boundaries
	    if (y < 0) {
	        y = 0;              // Top boundary
	        velocityY = 0;       // Stop upward movement
	    }
	    if (y > 768 - (9 * gp.spriteScale)) {
	        y = 768  - (9 * gp.spriteScale); // Bottom boundary
	        velocityY = 0;      // Stop downward movement
	    }

	    // Update direction based on velocityY
	    if (velocityY < 0) {
	        direction = "up";
	    } else if (velocityY > 0) {
	        direction = "down";
	    } else {
	        direction = "center";
	    }
	    

	    // Call the superclass update method
	    super.update();
	    
	}

}
