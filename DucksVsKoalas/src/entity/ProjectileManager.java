package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {
	GamePanel gp;
	public List<Projectile> projectiles;
	public Projectile currentProjectile; // Stores the current projectile type
	KeyHandler keyH;
	public int cooldown;

	public void update() {
		if (keyH.shotPressed && cooldown == 0) {
			// Use the current projectile type to shoot
			try {
				Projectile newProjectile = currentProjectile.getClass().getConstructor(int.class, int.class, GamePanel.class).newInstance(gp.player.x + 16 * gp.spriteScale, gp.player.y, gp);
				projectiles.add(newProjectile);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		// Enable anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (projectiles != null) {
			for (Projectile p : projectiles) {
				p.draw(g2);
			}
		}
	}

	// Method to switch projectile type
	public void switchProjectile(Projectile _p) {
		currentProjectile = _p; // Switch to the new projectile type
	}

	public ProjectileManager(GamePanel gp, KeyHandler keyH, Projectile currentProjectile) {
		this.gp = gp;
		this.keyH = keyH;
		this.currentProjectile = currentProjectile;
		cooldown = 0;
		projectiles = new ArrayList<>();
	}
}
