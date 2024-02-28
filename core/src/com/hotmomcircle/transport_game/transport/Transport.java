package com.hotmomcircle.transport_game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hotmomcircle.transport_game.Game;

// Transport superclass.
// Foot, bike and car will all be instances of this class
// It will own the image for each method of transport (player walking, player on bike, car)
//It will also own speed, but not position as this will be handled by the player class

public class Transport {
	private Game game;
	private int footprint;
	private int staminaCost;
	
	public String name;
	public int speed;
	public Texture image;
	public Texture up1;
	public Texture up2;
	public Texture down1;
	public Texture down2;
	public Texture left1;
	public Texture left2;
	public Texture right1;
	public Texture right2;

	public Transport(Game game, String name, int speed, Texture[] images) {
		this.game = game;
		this.name = name;
		this.speed = speed;
		
		this.up1 = images[0];
		this.up2 = images[1];
		this.down1 = images[2];
		this.down2 = images[3];
		this.left1 = images[4];
		this.left2 = images[5];
		this.right1 = images[6];
		this.right2 = images[7];
		
		
	}
	
	public void render(SpriteBatch batch) {
		Texture currImg = getCurrentImage();
		batch.draw(currImg, game.getPlayerX(), game.getPlayerY(), 0, 0, currImg.getWidth(), currImg.getHeight(), game.scale, game.scale, 0, 0, 0, currImg.getWidth(), currImg.getHeight(), false, false);
	}
	
	public Texture getCurrentImage() {
		return down1;
	}
}