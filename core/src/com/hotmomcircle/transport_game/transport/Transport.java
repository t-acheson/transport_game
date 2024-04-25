package com.hotmomcircle.transport_game.transport;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.entity.Obstacle;
import com.hotmomcircle.transport_game.entity.Player;

// Transport superclass.
// Foot, bike and car will all be instances of this class
// It will own the image for each method of transport (player walking, player on bike, car)
//It will also own speed, but not position as this will be handled by the player class

public class Transport {
	public GameScreen game;
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
	private ArrayList<Obstacle> boundaryRoads;
	private ArrayList<Obstacle> boundaryRoadsAndPaths;

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
		Texture currImg = update();
//		System.out.println(currImg.getWidth());
//		System.out.println(currImg.getHeight());
//		System.out.println(game.getTileSize());
		batch.draw(currImg, game.player.getX(), game.player.getY(), 0, 0, currImg.getWidth(), currImg.getHeight(), game.scale, game.scale, 0, 0, 0, currImg.getWidth(), currImg.getHeight(), false, false);
//		batch.draw(currImg, game.player.getX(), game.player.getY(), 0, 0, game.getTileSize(), game.getTileSize(), 1, 1, 0, 0, 0, currImg.getWidth(), currImg.getHeight(), false, false);
	}
	
	public Texture getCurrentImage(float dx, float dy) {
		
		if(TimeUtils.nanoTime() - imgChangeTime > imgDuration && isMoving(dx, dy)) {
			imgChangeTime = TimeUtils.nanoTime();
			imgIdx = (imgIdx + 1) % 2;
		}
		
		switch(getDirection(dx, dy)) {
		case "up":
			return up[imgIdx];
		case "down":
			return down[imgIdx];
		case "left":
			return left[imgIdx];
		case "right":
			return right[imgIdx];
		default:
		return down[0];
		}
	}

	public String getFootprint() {
		return footprint;
	}

	public String getStaminaCost() {
		return staminaCost;
	}

	public Texture update() {
		//		Move the player 
		// define speed at render time
		float speed = getSpeed() * Gdx.graphics.getDeltaTime();

		// determine movement direction
		// TODO find a way to reintroduce the moonwalk bug, i mean FEATURE
		float dx = 0;
		float dy = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			// direction = "up";
			dy += speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			// direction = "down";
			dy -= speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			// direction = "left";
			dx -= speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			// direction = "right";
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

		for (Obstacle obstacle: this.game.obstacles) {
			int collision = handleCollision(obstacle.rectangle, dx, dy);

			switch (collision) {
				case 1:
					dx = -dx;
					break;

				case 2:
					dy = -dy;
					break;

				case 3:
					dx = -dx;
					dy = -dy;
			
				default:
					break;
			}
		}

		if (player.transIdx == player.CAR) {
			boundaryRoads = new ArrayList<Obstacle>();

			for (Obstacle road: this.game.roads) {
				if (player.rectangle.overlaps(road.rectangle)) {
					boundaryRoads.add(road);
				}
			}

			boolean internalCollision = handleInteralCollision(boundaryRoads, dx, dy);

			if (internalCollision) {
				dx = 0;
				dy = 0;
			}
		}

		if (player.transIdx == player.BIKE) {
			boundaryRoadsAndPaths = new ArrayList<Obstacle>();

			for (Obstacle road: this.game.roads) {
				if (player.rectangle.overlaps(road.rectangle)) {
					boundaryRoadsAndPaths.add(road);
				}
			}

			for (Obstacle path: this.game.paths) {
				if (player.rectangle.overlaps(path.rectangle)) {
					boundaryRoadsAndPaths.add(path);
				}
			}

			boolean internalCollision = handleInteralCollision(boundaryRoadsAndPaths, dx, dy);

			if (internalCollision) {
				dx = 0;
				dy = 0;
			}
		}

		// finally apply the movement
		this.player.incX(dx);
		this.player.incY(dy);

		this.player.rectangle.x = this.player.getX();
		this.player.rectangle.y = this.player.getY();

		return getCurrentImage(dx, dy);
	}

	public float getSpeed() {
		// TODO Auto-generated method stub
		return this.speed;
	}

	public boolean isMoving(float dx, float dy) {
		boolean up = dy > 0 ? true : false;
		boolean down = dy < 0 ? true : false;
		boolean left = dx < 0 ? true : false;
		boolean right = dx > 0 ? true : false;

		if (up || down || left || right) {
			String staminaCost = getStaminaCost();
			String footprint = getFootprint();
			this.game.freshness.setText(staminaCost);
			this.game.carbon.setText(footprint);
		}

		return up || down || left || right;
	}

	public String getDirection(float dx, float dy) {
		if (dx != 0) {
			direction = dx > 0 ? "right" : "left";
		}

		else if (dy != 0) {
			direction = dy > 0 ? "up" : "down";
		}
		return direction;
	}

	private int handleCollision(Rectangle obstacle, float dx, float dy) {
		float[][] points = {
			{player.getX() + dx, player.getY() + dy},
			{player.getX() + dx, player.getY() + player.rectangle.getHeight() + dy},
			{player.getX() + player.rectangle.getWidth() + dx, player.getY() + dy},
			{player.getX() + player.rectangle.getWidth() + dx, player.getY() + player.rectangle.getHeight() + dy}
		};
		for (float[] point: points) {
			if (obstacle.contains(point[0], point[1])) {
				System.out.println(TimeUtils.nanoTime());
				// Calculate the overlap between player and obstacle
				float overlapX = Math.max(0, Math.min(player.getX() + dx + player.rectangle.getWidth(), obstacle.x + obstacle.width) - Math.max(player.getX() + dx, obstacle.x));
				float overlapY = Math.max(0, Math.min(player.getY() + dy + player.rectangle.getHeight(), obstacle.y + obstacle.height) - Math.max(player.getY() + dy, obstacle.y));
		
				// Adjust player position based on overlap and movement direction
				if (overlapX < overlapY) {
					return 1;
				} else if (overlapX > overlapY) {
					return 2;
				} else {
					return 3;
				}
			}
		}
		return 0;
}

	private boolean handleInteralCollision(ArrayList<Obstacle> boundaryRoads, float dx, float dy) {
		boolean ob = true;
		for (Obstacle road: boundaryRoads) {
			if (road.rectangle.contains(player.getX() + dx, player.getY() + dy)) {
				ob = false;
				break;
			}
		}
		return ob;
	}
	
}