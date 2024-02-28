package com.hotmomcircle.transport_game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.hotmomcircle.transport_game.TransportGame;

// Transport superclass.
// Foot, bike and car will all be instances of this class
// It will own the image for each method of transport (player walking, player on bike, car)
//It will also own speed, but not position as this will be handled by the player class

public class Transport {
	private TransportGame game;
	public String name;
	public int speed;
	private int footprint;
	private int staminaCost;
	
	private long imgDuration = 500000000; //How long each image should be displayed
	private long imgChangeTime = 0; //Time since the image was last changed
	private int imgIdx = 0;
	
	public Texture image;
	public Texture[] up;
	public Texture[] down;
	public Texture[] left;
	public Texture[] right;
	

	public Transport(TransportGame game, String name, int speed, Texture[] images) {
		this.game = game;
		this.name = name;
		this.speed = speed;
		
		up = new Texture[2];
		down = new Texture[2];
		left = new Texture[2];
		right = new Texture[2];
		
		this.up[0] = images[0];
		this.up[1] = images[1];
		this.down[0] = images[2];
		this.down[1] = images[3];
		this.left[0] = images[4];
		this.left[1] = images[5];
		this.right[0] = images[6];
		this.right[1] = images[7];
		
		
	}
	
	public void render(SpriteBatch batch) throws Exception{
		Texture currImg = getCurrentImage();
		batch.draw(currImg, game.player.getX(), game.player.getY(), 0, 0, currImg.getWidth(), currImg.getHeight(), game.scale, game.scale, 0, 0, 0, currImg.getWidth(), currImg.getHeight(), false, false);
	}
	
	public Texture getCurrentImage() throws Exception {
		
		if(TimeUtils.nanoTime() - imgChangeTime > imgDuration) {
			imgChangeTime = TimeUtils.nanoTime();
			imgIdx = (imgIdx + 1) % 2;
		}
		
		switch(game.player.getDirection()) {
		case "up":
			return up[imgIdx];
		case "down":
			return down[imgIdx];
		case "left":
			return left[imgIdx];
		case "right":
			return right[imgIdx];
		default:
			throw new Exception("Error: incorrect direction string");
		}
	}
	
}