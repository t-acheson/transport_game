package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable{
	
//	Screen settings
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;//768 px
	final int screenHeight = tileSize * maxScreenRow; //576 px
	final int FPS = 60;
	
	KeyHandler keyH = new KeyHandler(); //Listens for key strokes
	Thread gameThread; 
	Player player = new Player(this, keyH);
	
//	Set player's default position 
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //Allows the next frame to be rendered off screen before it's shown
		this.addKeyListener(keyH); 
		this.setFocusable(true); //Sets the focusable state of this Component to the specified value.
		
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); //Passing game panel class to the constructor to instantiate thread
		gameThread.start();
	}

	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime =  System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta > 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			
			}
			
			if(timer > 1000000000) {
				System.out.println("FPS: "+ drawCount);
				drawCount = 0;
				timer = 0;
			}
			

			
			
		}
	}
	
	public void update() {

		player.update();
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		player.draw(g2);
		
		g2.dispose();
	}
	
}
