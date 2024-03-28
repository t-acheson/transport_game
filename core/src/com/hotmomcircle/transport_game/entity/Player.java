package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.utils.Array;

import org.w3c.dom.css.Rect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.transport.Transport;

//This will hold the player class. 
//Player should be able to move, be drawn, and will own the transport methods
// Player should also have the code to handle movement

public class Player extends Entity {
	
	GameScreen game;
	private Texture bikeImage;
	private Texture carImage;
	private Transport[] transport = new Transport[3]; // [foot, bike, car]
	private int transIdx = 0; //Index corresponding to which transport the player is currently on
	private int stamina;
	public Rectangle playerRectangle;
	
	private String direction = "down";
	
	
	public Player(GameScreen game, int locX, int locY, int width, int height, String imagePath) {
		super(locX, locY, width, height, imagePath);
		this.game = game;
//		Initialize the textures
//		Initialize player x and y positions

		Texture[] playerTextures = new Texture[8];
		playerTextures[0] = new Texture(Gdx.files.internal("./foot/player_up1.png"));
		playerTextures[1] = new Texture(Gdx.files.internal("./foot/player_up2.png"));
		playerTextures[2] = new Texture(Gdx.files.internal("./foot/player_down1.png"));
		playerTextures[3] = new Texture(Gdx.files.internal("./foot/player_down2.png"));
		playerTextures[4] = new Texture(Gdx.files.internal("./foot/player_left1.png"));
		playerTextures[5] = new Texture(Gdx.files.internal("./foot/player_left2.png"));
		playerTextures[6] = new Texture(Gdx.files.internal("./foot/player_right1.png"));
		playerTextures[7] = new Texture(Gdx.files.internal("./foot/player_right2.png"));

		transport[0] = new Transport(game, "Foot", 200, playerTextures, "0", "-5");
		
		
		Texture[] bikeTextures = new Texture[8];

		bikeTextures[0] = new Texture(Gdx.files.internal("./bicycle/bike_up1.png"));
		bikeTextures[1] = new Texture(Gdx.files.internal("./bicycle/bike_up2.png"));
		bikeTextures[2] = new Texture(Gdx.files.internal("./bicycle/bike_down1.png"));
		bikeTextures[3] = new Texture(Gdx.files.internal("./bicycle/bike_down2.png"));
		bikeTextures[4] = new Texture(Gdx.files.internal("./bicycle/bike_left1.png"));
		bikeTextures[5] = new Texture(Gdx.files.internal("./bicycle/bike_left2.png"));
		bikeTextures[6] = new Texture(Gdx.files.internal("./bicycle/bike_right1.png"));
		bikeTextures[7] = new Texture(Gdx.files.internal("./bicycle/bike_right2.png"));
		
		// footprint for the bike? we gotta nerf it somehow, could just crank up the stamina cost?
		transport[1] = new Transport(game, "Bicycle", 300, bikeTextures, "2", "-10"); 
		
		Texture[] carTextures = new Texture[8];

		carTextures[0] = new Texture(Gdx.files.internal("./car/car_up.png"));
		carTextures[1] = new Texture(Gdx.files.internal("./car/car_up.png"));
		carTextures[2] = new Texture(Gdx.files.internal("./car/car_down.png"));
		carTextures[3] = new Texture(Gdx.files.internal("./car/car_down.png"));
		carTextures[4] = new Texture(Gdx.files.internal("./car/car_left.png"));
		carTextures[5] = new Texture(Gdx.files.internal("./car/car_left.png"));
		carTextures[6] = new Texture(Gdx.files.internal("./car/car_right.png"));
		carTextures[7] = new Texture(Gdx.files.internal("./car/car_right.png"));
		
		transport[2] = new Transport(game, "Car", 400, carTextures, "10", "0"); 

	}
	
	@Override
	public void render(SpriteBatch batch) throws Exception {
		
//		Can press 'f' to go on foot
		if(Gdx.input.isKeyPressed(Input.Keys.B)) {
			getOnBike();
		}
		
//		Can press 'c' to go in car
		if(Gdx.input.isKeyPressed(Input.Keys.C)) {
			getOnCar();
		}
		
		
//		Can press 'B' to get on bike
		if(Gdx.input.isKeyPressed(Input.Keys.F)) {
			getOnFoot();
		}
		
		
//		Move the player 
		if(Gdx.input.isKeyPressed(Input.Keys.W)) { 
			direction = "up";
			y += getSpeed() * Gdx.graphics.getDeltaTime();
			}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) { 
			direction = "down";
			y -= getSpeed() * Gdx.graphics.getDeltaTime();
			}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) { 
			direction = "left";
			x -= getSpeed() * Gdx.graphics.getDeltaTime();
			}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) { 
			direction = "right";
			x += getSpeed() * Gdx.graphics.getDeltaTime();
			}

		this.rectangle.x = this.getX();
		this.rectangle.y = this.getY();
		
		
		// Player interaction
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			this.interact();
		}
		
		
		
		currTransport().render(batch);
//		batch.draw(transport[transIdx].image, x, y, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), game.scale, game.scale, 0, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), false, false);
	}
	
	public int getSpeed() {
		return transport[transIdx].speed;
	}
	
	public String getDirection() {
		return direction;
	}
	
	
//	Returns the current transport method
	public Transport currTransport() {
		return transport[transIdx];
	}

	public Rectangle getPlayerRectangle() {
		return playerRectangle;
	}
	
	public boolean isMoving() {
		boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
		boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
		boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
		boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

		if (up || down || left || right) {
			String staminaCost = transport[transIdx].getStaminaCost();
			String footprint = transport[transIdx].getFootprint();
			this.game.freshness.setText(staminaCost);
			this.game.carbon.setText(footprint);
		}
		return up || down || left || right;
	}
	
//  Go on foot
	public void getOnFoot() {
		transIdx = 0;
	}
	
//	Changes player transport
	public void getOnBike() {
//		Can only get on bike if you are on foot
		if(transIdx == 0) {
			transIdx = 1;
		}
	}
	
//	 Changes player transport
	public void getOnCar() {
//		Can only get in car if on foot
		if(transIdx == 0) {
			transIdx = 2;
		}
	}
	
	// naive method to handle Player interaction

	public void interact() {
		// interate through all "interactable objects"
		for (Node node: this.game.nodes) {
			// if overlaps
			if (this.rectangle.overlaps(node.rectangle)) {
				// if overlaps
				// call togglePlanning
				// pass Routes of overlapped Node
				System.out.println(node);
				this.game.planningUI.activatePlanning(node.getRoutes());
				break;
			}
		}
		// could implement different loops for interacting
		// with different things
		// if we need to interact with anything more
		// than these transport nodes 
	}
}
