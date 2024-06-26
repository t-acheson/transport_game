package com.hotmomcircle.transport_game.entity;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.transport.GuidedTransport;
import com.hotmomcircle.transport_game.transport.Transport;

//This will hold the player class. 
//Player should be able to move, be drawn, and will own the transport methods
// Player should also have the code to handle movement

public class Player extends Entity {
	
	GameScreen game;
	private Transport[] transport = new Transport[5]; // [foot, bike, car]
	public int transIdx = 0; //Index corresponding to which transport the player is currently on
	public int FOOT = 0;
	public int BIKE = 1;
	public int CAR = 2;
	public int BUS = 3;
	public int LUAS = 4;
	private int stamina;
	public Rectangle playerRectangle;

	public Sound bikeEffect;
	public Sound carEffect;
	
	public float prevx = 0;
	public float prevy = 0;

	private String direction = "down";
	private boolean hasInteracted = false;
	private ArrayList<Obstacle> boundaryRoads;
	private ArrayList<Obstacle> boundaryRoadsAndPaths;
	
	public Player(GameScreen game, int locX, int locY, int width, int height, String imagePath) {
		super(game, locX, locY, width, height, imagePath);
		this.game = game;
//		Initialize the textures	and create transport classes
//		Game assetManager has already loaded these
		String[] paths = {
			    "./foot/player_up1.png",
			    "./foot/player_up2.png",
			    "./foot/player_down1.png",
			    "./foot/player_down2.png",
			    "./foot/player_left1.png",
			    "./foot/player_left2.png",
			    "./foot/player_right1.png",
			    "./foot/player_right2.png"
			};
		
		Texture[] playerTextures = new Texture[8];

		for(int i = 0; i<paths.length; i++) {
			playerTextures[i] = game.assetManager.get(paths[i], Texture.class);
		}

		transport[0] = new Transport(game, this, "Foot", 200, playerTextures, "0", "5");
		
		
		
		Texture[] bikeTextures = new Texture[8];
		
		String[] bikePaths = {
			    "./bicycle/bike_up1.png",
			    "./bicycle/bike_up2.png",
			    "./bicycle/bike_down1.png",
			    "./bicycle/bike_down2.png",
			    "./bicycle/bike_left1.png",
			    "./bicycle/bike_left2.png",
			    "./bicycle/bike_right1.png",
			    "./bicycle/bike_right2.png"
			};

		
		for(int i = 0; i<bikePaths.length; i++) {
			bikeTextures[i] = game.assetManager.get(bikePaths[i], Texture.class);
		}
		
		
		// footprint for the bike? we gotta nerf it somehow, could just crank up the stamina cost?
		transport[1] = new Transport(game, this, "Bicycle", 300, bikeTextures, "2", "10"); 
		
		Texture[] carTextures = new Texture[8];
		
		String[] carPaths = {
			    "./car/car_up.png",
			    "./car/car_up.png",
			    "./car/car_down.png",
			    "./car/car_down.png",
			    "./car/car_left.png",
			    "./car/car_left.png",
			    "./car/car_right.png",
			    "./car/car_right.png"
			};
		
		for(int i = 0; i<carPaths.length; i++) {
			carTextures[i] = game.assetManager.get(carPaths[i], Texture.class);
		}
		
		transport[2] = new Transport(game, this, "Car", 400, carTextures, "10", "0"); 

		Texture[] busTextures = new Texture[8];
		
		String[] busPaths = {
			    "./bus/bus_up.png",
			    "./bus/bus_up.png",
			    "./bus/bus_down.png",
			    "./bus/bus_down.png",
			    "./bus/bus_left.png",
			    "./bus/bus_left.png",
			    "./bus/bus_right.png",
			    "./bus/bus_right.png"
			};
		
		for(int i = 0; i<busPaths.length; i++) {
			busTextures[i] = game.assetManager.get(busPaths[i], Texture.class);
		}
		
		transport[3] = new GuidedTransport(game, this, "Bus", 400, busTextures, "5", "0"); 
		
		Texture[] luasTextures = new Texture[8];
		
		String[] luasPaths = {
			    "./luas/luas_up.png",
			    "./luas/luas_up.png",
			    "./luas/luas_down.png",
			    "./luas/luas_down.png",
			    "./luas/luas_left.png",
			    "./luas/luas_left.png",
			    "./luas/luas_right.png",
			    "./luas/luas_right.png"
			};
		
		for(int i = 0; i<luasPaths.length; i++) {
			luasTextures[i] = game.assetManager.get(luasPaths[i], Texture.class);
		}
		
		transport[4] = new GuidedTransport(game, this, "Luas", 400, luasTextures, "5", "0"); 
		
		bikeEffect = Gdx.audio.newSound(Gdx.files.internal("bikebell.mp3"));
		long bikeId = bikeEffect.play(0f);
		bikeEffect.setLooping(bikeId, false);

		carEffect = Gdx.audio.newSound(Gdx.files.internal("carhorn.mp3"));
		long carId = carEffect.play(0f);
		carEffect.setLooping(carId, false);
	}
	
	@Override
	public void render(SpriteBatch batch) throws Exception {
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && hasInteracted) {
			// used to signal to the game that a transport change has occurred and a popup needs to be shown
			game.showPopup = true;
		
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !hasInteracted) {
			interact();
			 
			switch(currTransport().name) {
			case "Foot":
				interact();
				break;
			case "Bicycle":
				game.addBike(Math.round(this.x), Math.round(this.y));				
				getOnFoot();
				
				break;
			case "Car":
				game.addCar(Math.round(this.x), Math.round(this.y));
				getOnFoot();
				break;
			}
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
			game.levelEnd = true;
			game.levelCompleted = true;
		}
//		Can press 'f' to go on foot
		if(Gdx.input.isKeyPressed(Input.Keys.F)) {
			getOnFoot();
		}
		
//		Can press 'c' to go in car
		if(Gdx.input.isKeyPressed(Input.Keys.C)) {
			getOnCar();
		}
		
		
//		Can press 'B' to get on bike
		if(Gdx.input.isKeyPressed(Input.Keys.B)) {
			getOnBike();
		}		

		currTransport().render(batch);
//		batch.draw(transport[transIdx].image, x, y, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), game.scale, game.scale, 0, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), false, false);
	
		hasInteracted = false;
	}
	
	public int getSpeed() {
		return transport[transIdx].speed;
	}
	
	
//	Returns the current transport method
	public Transport currTransport() {
		return transport[transIdx];
	}

	public Rectangle getPlayerRectangle() {
		return this.rectangle;
	}

	public void Collision(Rectangle playersPreviousPosition){

		this.rectangle = new Rectangle(
			playersPreviousPosition.x, 
			playersPreviousPosition.y, 
			playersPreviousPosition.getWidth(),
			 playersPreviousPosition.getHeight());
	}
	
//  Go on foot
	public void getOnFoot() {
		hasInteracted = true;
		transIdx = 0;
		game.showPopup = true;
	}
	
//	Changes player transport
	public void getOnBike() {
		hasInteracted = true;
//		Can only get on bike if you are on foot
		if(transIdx == 0) {
			transIdx = 1;
		}

		bikeEffect.play();
	}
	
//	 Changes player transport
	public void getOnCar() {
		hasInteracted = true;
//		Can only get in car if on foot
		if(transIdx == 0) {
			transIdx = 2;
		}

		carEffect.play();
	}

	//	 Changes player transport
	public void getOnBus() {
		hasInteracted = true;
//		Can only get on bus if on foot
		if(transIdx == 0) {
			transIdx = 3;
		}

	}

	public void getOnLuas() {
		hasInteracted = true;
	//		Can only get on Luas if on foot
			if(transIdx == 0) {
				transIdx = 4;
			}
	}
	
	// naive method to handle Player interaction

	public void interact() {
		
		if(hasInteracted)
			return;
		
		// interate through all "interactable objects"
		for (Hub hub: this.game.busHubs) {
			if (canGetOnTransport(hub.getRect())) {
				// if overlaps
				// call togglePlanning
				// pass Routes of overlapped Node
				this.game.planningUI.activatePlanning(hub.getConnected());
				break;
			}
		}

		for (Hub hub: this.game.luasHubs) {
			if (canGetOnTransport(hub.getRect())) {
				// if overlaps
				// call togglePlanning
				// pass Routes of overlapped Node
				this.game.planningUI.activatePlanning(hub.getConnected());
				break;
			}
		}
		// could implement different loops for interacting
		// with different things
		// if we need to interact with anything more
		// than these transport nodes 
	}

	public Transport[] getTransport() {
		return transport;
	}
	
	
	public int getTransIdx() {
		return transIdx;
	}
	
//	Can the player interact with an object
	public boolean canGetOnTransport(Rectangle rect) {
		return !hasInteracted && rect.contains(this.getX(), this.getY()) && transIdx == FOOT;
	}
	
}
