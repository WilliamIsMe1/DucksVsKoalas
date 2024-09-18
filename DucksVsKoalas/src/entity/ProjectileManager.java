package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {
	GamePanel gp;
	public List<Projectile> projectiles;
	KeyHandler keyH;
	public int cooldown;
	
	public void update() {
		if (keyH.shotPressed == true && cooldown == 0) {
			projectiles.add(new Burger(gp.player.x + 16 * gp.spriteScale + 1, gp.player.y, gp));
			cooldown = 15;
		}
		if (projectiles != null) {
			List<Projectile> toRemove = new ArrayList<>();
			for (Projectile p : projectiles) {
			    p.update();
			    if (p.x > gp.screenWidth) {
			        toRemove.add(p);
			        System.out.println("Projectile removed");
			    }
			}
			projectiles.removeAll(toRemove);
		}
		if (cooldown != 0) {
			cooldown--;			
		}

	}
	public void draw(Graphics2D g2) {
	    if (projectiles != null) {
	        for (Projectile p : projectiles) {
	            if (p.image != null) {
	                g2.drawImage(p.image, p.x, p.y, 16 * gp.spriteScale, 16 * gp.spriteScale, null);
	            } else {
	                System.out.println("Projectile image is null!");  // Debug message
	            }
	        }
	    }
	}

	public ProjectileManager(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		cooldown = 0;
		projectiles = new ArrayList<Projectile>();
	}
}
