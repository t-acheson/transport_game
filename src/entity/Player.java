package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH; 
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH; 
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			System.out.println(getClass().getResourceAsStream("/player/player_right1.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left1.png"));
			left2= ImageIO.read(getClass().getResourceAsStream("/player/player_left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right2.png"));
			System.out.println("Images loaded");
			System.out.println(up1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed) {
			
			y -= speed; //In java, top left is 0, 0
			direction = "up";
		}
		
		if(keyH.downPressed) {
			y += speed; //In java, top left is 0, 0
			direction = "down";
		} 
		
		if(keyH.leftPressed) {
			x -= speed; //In java, top left is 0, 0
			direction = "left";
		}
		
		if(keyH.rightPressed) {
			x += speed; //In java, top left is 0, 0
			direction = "right";
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			image = up1;
			break;
		case "down":
			image = down1;
			break;
		case "left":
			image = left1;
			break;
		case "right":
			image = right1;
			break;
		default:
			image = down1;
			break;
		}
//		System.out.println(x);
//		System.out.println(y);
//		System.out.println(direction);
//		System.out.println(image);
		
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
		
		
	}
}
