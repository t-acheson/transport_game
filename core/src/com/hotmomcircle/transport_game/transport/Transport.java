package com.hotmomcircle.transport_game.transport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.entity.Player;

// Transport superclass.
// Foot, bike and car will all be instances of this class
// It will own the image for each method of transport (player walking, player on bike, car)
//It will also own speed, but not position as this will be handled by the player class

public class Transport {
	private GameScreen game;
	public String name;
	public int speed;
	public Player player;
	// passing these as strings because that's the argument
	// Points.setText method takes (because extends Label)
	private String footprint;
	private String staminaCost;
	private String direction = "down";
	
	private long imgDuration = 500000000; //How long each image should be displayed
	private long imgChangeTime = 0; //Time since the image was last changed
	private int imgIdx = 0;
	private int imgScale; //Size of the image relative to the tile size, assumes all images same size
	
	public Texture image;
	public Texture[] up;
	public Texture[] down;
	public Texture[] left;
	public Texture[] right;

	public Transport(GameScreen game, Player player, String name, int speed, Texture[] images, String footprint, String staminaCost) {
		this.game = game;
		this.name = name;
		this.speed = speed;
		this.footprint = footprint;
		this.staminaCost = staminaCost;
		this.player = player;
		
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
		update();
//		System.out.println(currImg.getWidth());
//		System.out.println(currImg.getHeight());
//		System.out.println(game.getTileSize());
//		batch.draw(currImg, game.player.getX(), game.player.getY(), 0, 0, currImg.getWidth(), currImg.getHeight(), game.scale, game.scale, 0, 0, 0, currImg.getWidth(), currImg.getHeight(), false, false);
		batch.draw(currImg, game.player.getX(), game.player.getY(), 0, 0, game.getTileSize(), game.getTileSize(), 1, 1, 0, 0, 0, currImg.getWidth(), currImg.getHeight(), false, false);
	}
	
	public Texture getCurrentImage() throws Exception {
		
		if(TimeUtils.nanoTime() - imgChangeTime > imgDuration && isMoving()) {
			imgChangeTime = TimeUtils.nanoTime();
			imgIdx = (imgIdx + 1) % 2;
		}
		
		switch(getDirection()) {
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

	public String getFootprint() {
		return footprint;
	}

	public String getStaminaCost() {
		return staminaCost;
	}

	public void update() {
		//		Move the player 
		// define speed at render time
		float speed = getSpeed() * Gdx.graphics.getDeltaTime();

		// determine movement direction
		// TODO find a way to reintroduce the moonwalk bug, i mean FEATURE
		float dx = 0;
		float dy = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			direction = "up";
			dy += speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			direction = "down";
			dy -= speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			direction = "left";
			dx -= speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			direction = "right";
			dx += speed;
		}

		// the diagonal vector is the same as the 
		// square root of the sum of the squared
		// vertical and horizontal vectors

		float movementMagnitude = (float) Math.sqrt(dx * dx + dy * dy);
		if (movementMagnitude > speed) {
			// if it exceeds it, we normalise the speed
			// by the magnitude
			dx = dx / movementMagnitude * speed;	
			dy = dy / movementMagnitude * speed;
		}

		// finally apply the movement
		this.player.incX(dx);
		this.player.incY(dy);

		this.player.rectangle.x = this.player.getX();
		this.player.rectangle.y = this.player.getY();
	}

	private float getSpeed() {
		// TODO Auto-generated method stub
		return this.speed;
	}

	public boolean isMoving() {
		boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
		boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
		boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
		boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

		if (up || down || left || right) {
			String staminaCost = getStaminaCost();
			String footprint = getFootprint();
			this.game.freshness.setText(staminaCost);
			this.game.carbon.setText(footprint);
		}
		return up || down || left || right;
	}

	public String getDirection() {
		return direction;
	}
	
}