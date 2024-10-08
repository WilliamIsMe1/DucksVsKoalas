package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Burger;
import entity.Koala;
import entity.MonsterManager;
import entity.Player;
import entity.ProjectileManager;

public class GamePanel extends JPanel implements Runnable {
	
	// SCREEN SETTINGS
	static final long serialVersionUID = 1L;
	public final int screenWidth = 1024;
	public final int screenHeight = 768;
	public final int spriteScale = 6;
	
	// DEBUG MODE
	public boolean debug = false;
	
	// FPS
	public final int FPS = 60;
	private int currentFPS = 0;
	private long lastTimeCheck = 0;
	private int frameCount = 0;
	
	KeyHandler keyH = new KeyHandler(this);
	Thread gameThread;
	public Player player = new Player(this, keyH);
	ProjectileManager projM = new ProjectileManager(this, keyH, new Burger());
	Background background = new Background(this, 5);
    MonsterManager monsterM = new MonsterManager(this, new Koala());
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) {
			update();
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime /= 1000000;
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				nextDrawTime += drawInterval;
				Thread.sleep((long) remainingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// FPS Calculation
			frameCount++;
			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				currentFPS = frameCount;
				frameCount = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}
	}
	
	public void update() {
		player.update();
		projM.update();
		monsterM.update();
		background.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		background.draw(g2);
		player.draw(g2);
		monsterM.draw(g2);
		projM.draw(g2);
		
		// Draw FPS on screen
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		g2.drawString("FPS: " + currentFPS, 10, 20);
		
		g2.dispose();
	}
}
