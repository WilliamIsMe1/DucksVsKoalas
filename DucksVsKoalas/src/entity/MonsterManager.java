package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class MonsterManager {
	GamePanel gp;
	public List<Monster> monsters;
	public Monster currentMonster; // Stores the current monster type

	public int cooldown;

	public void update() {
	    // Decrement the cooldown regardless of whether monsters are being updated
	    if (cooldown > 0) {
	        cooldown--;
	    }

	    if (cooldown == 0) {
	        // Spawn the current monster type
	        try {
	            Monster newMonster = currentMonster.getClass().getConstructor(int.class, int.class, GamePanel.class)
	                    .newInstance(gp.screenWidth, (int) (Math.random() * (gp.screenHeight - 16 * gp.spriteScale)), gp);
	            monsters.add(newMonster);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        // Set cooldown after monster spawn
	        cooldown = 60;
	    }

	    // Update monsters and handle removal of off-screen ones
	    if (monsters != null) {
	        List<Monster> toRemove = new ArrayList<>();
	        for (Monster m : monsters) {
	            m.update(); // Ensure the monster's position is being updated
	            if (m.x < 0 - (gp.spriteScale * 16)) { // If the monster goes off-screen
	                toRemove.add(m);
	                System.out.println("Monster removed");
	            }
	        }
	        monsters.removeAll(toRemove);
	    }
	}


	public void draw(Graphics2D g2) {
		// Enable anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (monsters != null) {
			for (Monster m : monsters) {
				m.draw(g2);
			}
		}
	}

	// Method to switch projectile type
	public void switchMonster(Monster _m) {
		currentMonster = _m; // Switch to the new projectile type
	}

	public MonsterManager(GamePanel gp, Monster currentMonster) {
		this.gp = gp;
		this.currentMonster = currentMonster;
		cooldown = 0;
		monsters = new ArrayList<>();
	}
}
