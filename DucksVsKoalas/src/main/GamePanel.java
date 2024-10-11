package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Burger;
import entity.CollisionManager;
import entity.Koala;
import entity.MonsterManager;
import entity.Player;
import entity.ProjectileManager;

public class GamePanel extends JPanel implements Runnable {
	
    private JFrame debugWindow; // For the player info window
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
	public ProjectileManager projM = new ProjectileManager(this, keyH, new Burger());
	Background background = new Background(this, 5);
    public MonsterManager monsterM = new MonsterManager(this, new Koala());
	CollisionManager collisionM = new CollisionManager(this);
    
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
	
	public void toggleInfoWindow() {
	    if (debugWindow == null) {
	        // Create the debug window if it doesn't exist
	        debugWindow = new JFrame("Player Info");
	        debugWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        debugWindow.setSize(300, 200);

	        // Make sure it stays on top but doesn't take focus away from the game window
	        debugWindow.setAlwaysOnTop(true);  
	        debugWindow.setFocusableWindowState(false); // Prevents stealing focus
	        
	        // Create the panel that shows the player's information
	        JPanel infoPanel = new JPanel();
	        infoPanel.setLayout(new BorderLayout());

	        JLabel hpLabel = new JLabel("HP: " + player.hp);
	        JLabel velocityLabel = new JLabel("VelocityX: " + player.velocityX);
	        JLabel positionLabel = new JLabel("Position: (" + player.x + ", " + player.y + ")");

	        infoPanel.add(hpLabel, BorderLayout.NORTH);
	        infoPanel.add(velocityLabel, BorderLayout.CENTER);
	        infoPanel.add(positionLabel, BorderLayout.SOUTH);

	        debugWindow.add(infoPanel);
	        debugWindow.setVisible(true);

	    } else {
	        // Close the window if it's already open
	        debugWindow.dispose();
	        debugWindow = null;
	    }
	}

	
	public void update() {
		player.update();
		projM.update();
		monsterM.update();
		background.update();
		collisionM.update();
		if (debugWindow != null && debugWindow.getContentPane().getComponentCount() > 0) {
		    JPanel infoPanel = (JPanel) debugWindow.getContentPane().getComponent(0); 
		    if (infoPanel.getComponentCount() >= 3) {
		        JLabel hpLabel = (JLabel) infoPanel.getComponent(0);
		        JLabel velocityLabel = (JLabel) infoPanel.getComponent(1);
		        JLabel positionLabel = (JLabel) infoPanel.getComponent(2);

		        hpLabel.setText("HP: " + player.hp);
		        velocityLabel.setText("Velocity: (" + player.velocityX + ", " + player.velocityY + ")");
		        positionLabel.setText("Position: (" + player.x + ", " + player.y + ")");
		    }
		}


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
