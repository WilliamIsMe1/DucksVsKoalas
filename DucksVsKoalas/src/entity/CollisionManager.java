package entity;

import java.util.Iterator;
import java.util.List;

import main.GamePanel;

public class CollisionManager {

    GamePanel gp;

    public CollisionManager(GamePanel gp) {
        this.gp = gp;
    }

    public void update() {
        // Get references to the player, projectiles, and monsters
        Player player = gp.player;
        List<Projectile> projectiles = gp.projM.projectiles;
        List<Monster> monsters = gp.monsterM.monsters;

        // Check for collisions between player and monsters
        Iterator<Monster> monsterIterator = monsters.iterator();
        while (monsterIterator.hasNext()) {
            Monster monster = monsterIterator.next();
            if (player.boundingBox.intersects(monster.boundingBox)) {
                System.out.println("Player collided with a monster!");
                player.hp--; // Example: reduce player HP
                monsterIterator.remove(); // Safely remove the monster
                break; // Exit loop to avoid further processing
            }
        }

        // Check for collisions between projectiles and monsters
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            monsterIterator = monsters.iterator(); // Restart the monster iterator for each projectile
            while (monsterIterator.hasNext()) {
                Monster monster = monsterIterator.next();
                if (projectile.boundingBox.intersects(monster.boundingBox)) {
                    System.out.println("Projectile hit a monster!");
                    monsterIterator.remove(); // Safely remove the monster
                    projectileIterator.remove(); // Safely remove the projectile
                    break; // Exit inner loop after handling collision
                }
            }
        }
    }
}

