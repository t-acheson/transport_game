package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.transport.Transport;
import com.hotmomcircle.transport_game.tools.pathfinding.AStar;
import com.hotmomcircle.transport_game.tools.pathfinding.Node;
import com.hotmomcircle.transport_game.tools.pathfinding.NodeFinder;

//This will hold the player class. 
//Player should be able to move, be drawn, and will own the transport methods
// Player should also have the code to handle movement

public class Player extends Entity {
	
	GameScreen game;
	private Transport[] transport = new Transport[3]; // [foot, bike, car]
	public int transIdx = 0; //Index corresponding to which transport the player is currently on
	public int FOOT = 0;
	public int BIKE = 1;
	public int CAR = 2;
	private int stamina;
	public Rectangle playerRectangle;
	
	private String direction = "down";
	private boolean hasInteracted = false;
	
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

		transport[0] = new Transport(game, "Foot", 200, playerTextures, "0", "-5");
		
		
		
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
		transport[1] = new Transport(game, "Bicycle", 300, bikeTextures, "2", "-10"); 
		
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
		
		transport[2] = new Transport(game, "Car", 400, carTextures, "10", "0"); 

	}
	
	@Override
	public void render(SpriteBatch batch) throws Exception {
		
		
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
//		Can press 'f' to go on foot
		if(Gdx.input.isKeyPressed(Input.Keys.F)) {
			getOnFoot();
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

		if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			System.out.println(NodeFinder.findNode(this.game.pathfindingGraph.graph, x, y));
			NodeFinder.findNeighbours(this.game.pathfindingGraph.graph, x, y);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			System.out.println(getX() + " " + getY());
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			Node source = NodeFinder.findNode(this.game.pathfindingGraph.graph, x, y);
			Node dest = NodeFinder.findNode(this.game.pathfindingGraph.graph, 6288, 4608);
			AStar.findPath(this.game.pathfindingGraph.graph, source, dest);
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
		x += dx;
		y += dy;

		this.rectangle.x = this.getX();
		this.rectangle.y = this.getY();
		
		
		// Player interaction
		
		currTransport().render(batch, dx, dy);
//		batch.draw(transport[transIdx].image, x, y, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), game.scale, game.scale, 0, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), false, false);
	
		hasInteracted = false;
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
		return this.rectangle;
	}
	
	public boolean isMoving(float dx, float dy) {
		boolean up = dy > 0 ? true : false;
		boolean down = dy < 0 ? true : false;
		boolean left = dx < 0 ? true : false;
		boolean right = dx > 0 ? true : false;

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
		hasInteracted = true;
		transIdx = 0;
	}
	
//	Changes player transport
	public void getOnBike() {
		hasInteracted = true;
//		Can only get on bike if you are on foot
		if(transIdx == 0) {
			transIdx = 1;
		}
	}
	
//	 Changes player transport
	public void getOnCar() {
		hasInteracted = true;
//		Can only get in car if on foot
		if(transIdx == 0) {
			transIdx = 2;
		}
	}
	
	// naive method to handle Player interaction

	public void interact() {
		
		if(hasInteracted)
			return;
		
		// interate through all "interactable objects"
		for (Hub hub: this.game.hubs) {
			// if overlaps
			if (canGetOnTransport(hub.rectangle)) {
				// if overlaps
				// call togglePlanning
				// pass Routes of overlapped Node
				System.out.println(hub);
				this.game.planningUI.activatePlanning(hub.getRoutes());
				this.game.camera.zoomOut();
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
		return !hasInteracted && getPlayerRectangle().overlaps(rect) && transIdx == FOOT;
	}
}
