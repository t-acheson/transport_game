package com.hotmomcircle.transport_game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.object.Bicycle_OBJ;
import com.hotmomcircle.transport_game.object.Car_OBJ;
import com.hotmomcircle.transport_game.object.Transport_OBJ;
import com.hotmomcircle.transport_game.entity.Route;
import com.hotmomcircle.transport_game.tools.Camera;
import com.hotmomcircle.transport_game.tools.WorldMap;
import com.hotmomcircle.transport_game.tools.pathfinding.AStar;
import com.hotmomcircle.transport_game.tools.pathfinding.PathfindingGraph;
import com.hotmomcircle.transport_game.entity.Hub;
import com.hotmomcircle.transport_game.ui.Planning;
import com.hotmomcircle.transport_game.ui.Points;
import com.hotmomcircle.transport_game.ui.TimerUI;
import com.hotmomcircle.transport_game.ui.WorldMapUI;
import com.hotmomcircle.transport_game.ui.gemArrow;
import com.hotmomcircle.transport_game.ui.LevelStart;
import com.hotmomcircle.transport_game.ui.EducationalPopup;
import com.hotmomcircle.transport_game.ui.LevelStart;
import com.hotmomcircle.transport_game.ui.Pause;
import com.hotmomcircle.transport_game.ui.gemCounter;
//map imports below 
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task; 



// Screen of the level the player is currently playing
// Separation of game and level to allow 
public class GameScreen implements Screen, Json.Serializable {

	TransportGame game;
	ParentGame parentGame;

	SpriteBatch batch;

	private int originalTileSize = 16;
	public int scale = 2;
	private int tileSize = originalTileSize * scale;
	
	//initializing map 
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	Texture img;
	public Player player;
	public ArrayList<Transport_OBJ> bike_OBJs = new ArrayList<Transport_OBJ>();
	public ArrayList<Transport_OBJ> car_OBJs = new ArrayList<Transport_OBJ>();
	   
	public Camera camera;
	// for the world map on press of "M"
	public boolean showWorldMap = false;
	public WorldMap worldMap;
	WorldMapUI worldMapUI;
	Stage worldMapStage;


	// for the educationalPopups
	public EducationalPopup educationalPopup;
	public boolean showPopup = false;
	// Pathfinding resources
	public PathfindingGraph pathfindingGraph;
	public AStar astar; // algorithm for finding the path
	
	// Texture playerMap = new Texture("assets/phoneScreen.png");
	
	public Array<Gem> gems = new Array<Gem>();;

	public Array<Hub> busHubs;
	public Array<Hub> luasHubs;
	   
   // Variables associated with the pause / game state
	private int GAME_STATE;
	private final int GAME_RUNNING = 0;
	private final int GAME_PAUSED = 1;
	public Skin skin;
	public BitmapFont font;
	
	public LevelStart levelStart;
	public Stage startStage;
	public boolean isLevelStart = true;
	

	public Pause pauseUI;
	public Stage pauseStage;

	//UI Skin

	// Stage for UI components
	private Stage stage;
	private Table table;

	// Planning UI
	public Planning planningUI;

	// scores need to be public so Player can modify
	public Points points;
	public Points carbon;
	public Points freshness;

	public AssetManager assetManager;

	//gemArrow instance 
	private gemArrow gemArrowUI;
	private gemCounter gemCounter;
	public LevelEndScreen levelEndScreen;
	public boolean levelEnd = false;
	public boolean levelCompleted;

	private float timeLeft;
	private TimerUI timer;




// New level
	public GameScreen(TransportGame game, ParentGame parentGame, JsonValue levelData) {
		this.game = game;
		this.parentGame = parentGame;
		
		loadAssets();
		
		int pX = levelData.get("player").getInt("x");
		int pY = levelData.get("player").getInt("y");
		player = new Player(this, pX, pY, 32, 32, "./foot/player_down1.png");

		timer = new TimerUI(String.valueOf(levelData.getInt("time")), skin);

//		Load gems from levels file
		for (JsonValue gemLoc = levelData.get("gems").child; gemLoc != null; gemLoc = gemLoc.next) {
			gems.add(new Gem(this, gemLoc.getInt("x"), gemLoc.getInt("y"), 16, 16));
		}
		
//		Load cars from levels file
		for (JsonValue carLoc = levelData.get("cars").child; carLoc != null; carLoc = carLoc.next) {
			car_OBJs.add(new Car_OBJ(this, carLoc.getInt("x"), carLoc.getInt("y"), true));
		}
		
		
//		Load bikes from levels file
		for (JsonValue bikeLoc = levelData.get("bikes").child; bikeLoc != null; bikeLoc = bikeLoc.next) {
			bike_OBJs.add(new Bicycle_OBJ(this, bikeLoc.getInt("x"), bikeLoc.getInt("y"), true));
		}
		

		
		

		initializeGame();
	}
	
//	Load level from json
	public GameScreen(TransportGame game, ParentGame parentGame, JsonValue levelData, JsonValue jsonMap) {
		this.game = game;
		this.font = game.font;
		this.parentGame = parentGame;
		// for the pause / play feature

		loadAssets();
//		Read in the serializable data
		read(null, jsonMap);
		

		initializeGame();
		
		
	}
//		Load assets - Load all textures, maps, etc here with the assetManager before going to the game screen.
//		Separated from initialize game as assets need to be loaded before player is loaded, player needs to be loaded before rest of game is initialized
	public void loadAssets() {
//		In the mean time show a loading screen
		assetManager = parentGame.assetManager;

		//loading map 
		assetManager.setLoader(TiledMap.class,  new TmxMapLoader());
		assetManager.load("finalDraft.tmx", TiledMap.class);
		
//		Load in the player transport
		String[] transportPaths = {
			    "./foot/player_up1.png", "./foot/player_up2.png",
			    "./foot/player_down1.png", "./foot/player_down2.png",
			    "./foot/player_left1.png", "./foot/player_left2.png",
			    "./foot/player_right1.png", "./foot/player_right2.png",
			    "./bicycle/bike_up1.png", "./bicycle/bike_up2.png",
			    "./bicycle/bike_down1.png", "./bicycle/bike_down2.png",
			    "./bicycle/bike_left1.png", "./bicycle/bike_left2.png",
			    "./bicycle/bike_right1.png", "./bicycle/bike_right2.png",
			    "./car/car_up.png", "./car/car_up.png",
			    "./car/car_down.png", "./car/car_down.png",
			    "./car/car_left.png", "./car/car_left.png",
			    "./car/car_right.png", "./car/car_right.png",
				"./bus/bus_left.png", "./bus/bus_right.png",
				"./bus/bus_up.png", "./bus/bus_down.png",
			};
		
		for(String path: transportPaths) {
			assetManager.load(path, Texture.class);
			
		}
		
//		Load in the objects (gem, bike_OBJ, car_OBJ
		String[] objectPaths = {
				"gem.png",
				"./objects/bicycle.png",
				"./objects/car_left.png"
		};
		
		for(String path: objectPaths) {
			assetManager.load(path, Texture.class);
			
		}
		
		assetManager.finishLoading();

		try {
			map = assetManager.get("finalDraft.tmx", TiledMap.class);
			System.out.println("Map loaded successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// graph representing the 'roads' layer
		pathfindingGraph = new PathfindingGraph(map, originalTileSize);
		
		this.font = game.font;
		this.skin = game.skin;

		this.batch = game.batch;
	}
	
//	Initializes the game. Put into separate function to allow multiple constructors to call it
	public void initializeGame() {

		
		// for the pause / play feature
		GAME_STATE = GAME_PAUSED;
		



		// initialise Node array
		busHubs = new Array<Hub>();
		luasHubs = new Array<Hub>();
		

		for (MapLayer layer : map.getLayers()) {
            // Check if the layer contains objects and is of guided transport
            if (layer.getObjects() != null && layer.getName().contains("bus")) {

                // Retrieve objects from the layer
                for (MapObject object : layer.getObjects()) {
					// get X and Y for each object
                    // pass to Hub constructor
					busHubs.add(hubCreator(object, "Bus", 3));
				}

				for (MapObject object : layer.getObjects()) {
					Hub newHub = hubCreator(object, "Bus", 3);

					for (Hub hub : busHubs) { 
						if (newHub.getX() != hub.getX() && newHub.getY() != hub.getY()) {
							hub.addHub(newHub);
						}
					}
				}
            }

			if (layer.getObjects() != null && layer.getName().contains("luas")) {

                // Retrieve objects from the layer
                for (MapObject object : layer.getObjects()) {
					// get X and Y for each object
                    // pass to Hub constructor
					luasHubs.add(hubCreator(object, "Luas", 4));
				}

				for (MapObject object : layer.getObjects()) {
					Hub newHub = hubCreator(object, "Luas", 4);

					for (Hub hub : luasHubs) { 
						if (newHub.getX() != hub.getX() && newHub.getY() != hub.getY()) {
							hub.addHub(newHub);
						}
					}
				}
            }
		}

		renderer = new OrthogonalTiledMapRenderer(map,3);
		//

		
		

		
		// create the camera and the SpriteBatch
		camera = new Camera(game, player);

		// Stage is the layer on which we draw the UI
		// Likely keeps things cleaner as we make
		// the map more complicated and add objects
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// Asset manager instansiation
		assetManager.load("uiskin.json", Skin.class);

		startStage = new Stage(new ScreenViewport());
		levelStart = new LevelStart(game, this, startStage, skin, parentGame.getCurrLevel());
		
		// table to hold UI elements
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(game.SCREEN_WIDTH / 9).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH / 9);
		table.left().top();

		// UI scores
		points = new Points("0", skin);
		carbon = new Points("0", skin);
		freshness = new Points("100", skin);
		
		gemArrowUI = new gemArrow(skin, player, gems, table); 
		gemCounter = new gemCounter(gems, skin);

		// fill table with UI scores
		table.add(new Label("Gems: ", skin));
		table.add(gemCounter).fillX().uniformX();
		table.add(new Label("Time: ", skin));
		table.add(timer).fillX().uniformX();
		table.add(new Label("Carbon: ", skin));
		table.add(carbon).fillX().uniformX();
		table.add(new Label("Fresh: ", skin));
		table.add(freshness).fillX().uniformX();
		// table.add(new Label("Arrow", skin));
		// table.add(gemArrowUI).fillX().uniformX();

		// Assuming you have a Skin instance for your UI
		
		// add table to stage
		stage.addActor(table);

		// Planning UI
		planningUI = new Planning(game, this, stage, skin, player);
		

		// Pause UI
		pauseStage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(pauseStage);

		pauseUI = new Pause(game, this, pauseStage, skin);

		worldMap = new WorldMap(renderer, map, batch, camera);
		worldMapStage = new Stage(new ScreenViewport());

		worldMapUI = new WorldMapUI(game, this, worldMapStage, skin);

		educationalPopup = new EducationalPopup(game, this, stage, skin, player);

		levelEndScreen = new LevelEndScreen(game, parentGame);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			System.out.println("X: " + player.getX() + ", Y: " + player.getY());
		}
		if(GAME_STATE == GAME_RUNNING) {
			timer.updateTimer(delta);
			
		}
		if (timer.getTime() <= 0){

			levelEnd = true;
			levelCompleted = false;
		}


		// public boolean levelEnd = false;
		// public boolean levelCompleted;
		if (gems.isEmpty()){
			levelEnd = true;
			levelCompleted = true;
		}


		if (levelEnd){
			if (levelCompleted){
				levelEndScreen.updateLevelEndScreen(true, parentGame.getCurrLevel(), points.getText().toString());
				game.setScreen(levelEndScreen);
			} else {
				levelEndScreen.gameOverScreen(parentGame.getCurrLevel(), points.getText().toString());
				game.setScreen(levelEndScreen);
			}
		}
		// levelEndScreen.updateLevelEndScreen(true, score);

		// game.setScreen(levelEndScreen);

		// pauses the game if it isnt already paused - prevents multiple inputs
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && GAME_STATE != GAME_PAUSED) {
			pause();
			pauseUI.showPause();
		} else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && GAME_STATE != GAME_RUNNING ) {
			resume();
		} 

		// shows world map
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			showWorldMap ^= true; // Toggle the state of showWorldMap
			if (showWorldMap) {
				System.out.println("show map");
			} else {
				System.out.println("hide map");
			}
		}
		
		if (isLevelStart) {
			renderer.setView(camera);
			camera.setPosition();
			camera.update();
			renderer.render();

			startStage.act(delta);
			startStage.draw();
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				isLevelStart = false;
				GAME_STATE = GAME_RUNNING;
			}
		}
		
		else if (GAME_STATE == GAME_PAUSED){
			pauseStage.act(delta);
			pauseStage.draw();
		}

		else if (showWorldMap) {
			worldMap.render(player, gems);
			if (!planningUI.active) {
				worldMapUI.showUI();
				worldMapStage.draw();
			}

		} else {

		
			ScreenUtils.clear(0, 0, 0.2f, 1);
			
			// map render 
			renderer.setView(camera);
			camera.setPosition();
			// camera.position.set(player.getX(),player.getY(), 0);


			renderer.render();
			//

			for (Gem gem : gems) {
				if (player.getRectangle().overlaps(gem.getRectangle())) {
					gems.removeValue(gem, true);
				points.setText("50");
				gemCounter.update();
				
				}
			}

		
		for(int i = 0; i < car_OBJs.size(); i++) {
			car_OBJs.get(i).update(i);
	}
		
		for(int i = 0; i < bike_OBJs.size(); i++) {
			bike_OBJs.get(i).update(i);
	}

		// tell the camera to update its matrices.
		camera.update();



		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		try {
			for (Transport_OBJ transport: car_OBJs) {
				if (transport != null) {					
					transport.render(batch);
				}
			}
			
			for (Transport_OBJ transport: bike_OBJs) {
				if (transport != null) {		
					transport.render(batch);
				}
			}
			
			
			
			for (Gem gem : gems) {
				gem.render(batch);
			}
			
//			Render the player last so they appear on top of everything
			
			if (!planningUI.active) {
				player.render(batch);
			}
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		batch.end();

		if (showPopup){
			showPopup = false;
			educationalPopup.showUI();
		}

	}
		 //Update the gemArrow UI with the current player and gem positions
		gemArrowUI.update(player, gems);
		// UI draw
		stage.act(delta);
		stage.draw();


		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		System.out.println("Game Paused");
		parentGame.saveGame();
		GAME_STATE = GAME_PAUSED;
		
	}

	
	@Override
	public void resume() {
		GAME_STATE = GAME_RUNNING;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

		// TODO check if right place for this disposal
		map.dispose();
		renderer.dispose();
		stage.dispose();
		assetManager.dispose(); // This will have to be removed from gamescreen when we have multiple levels and put into ParentGame

	}

	public int getTileSize() {
		return tileSize;
	}
	
	public void addBike(int x, int y) {
		bike_OBJs.add(new Bicycle_OBJ(this, x, y, true));
	}
	
	public void addCar(int x, int y) {
		car_OBJs.add(new Car_OBJ(this, x, y, true));
	}

	@Override
	public void write(Json json) {
		json.writeValue("playerX", player.getX());
		json.writeValue("playerY", player.getY());
		json.writeValue("cars", car_OBJs);
		json.writeValue("bikes", bike_OBJs);
		json.writeValue("gems", gems);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		int x = jsonData.getInt("playerX");
		int y = jsonData.getInt("playerY");
		player = new Player(this, x, y, tileSize, tileSize, "./foot/player_down1.png");
		
//		Load gems from levels file
		for (JsonValue gemLoc = jsonData.get("gems").child; gemLoc != null; gemLoc = gemLoc.next) {
			gems.add(new Gem(this, gemLoc.getInt("x"), gemLoc.getInt("y"), 16, 16));
		}
		
//		Load cars from levels file
		for (JsonValue carLoc = jsonData.get("cars").child; carLoc != null; carLoc = carLoc.next) {
			car_OBJs.add(new Car_OBJ(this, carLoc.getInt("x"), carLoc.getInt("y"), true));
		}
		
		
//		Load bikes from levels file
		for (JsonValue bikeLoc = jsonData.get("bikes").child; bikeLoc != null; bikeLoc = bikeLoc.next) {
			bike_OBJs.add(new Bicycle_OBJ(this, bikeLoc.getInt("x"), bikeLoc.getInt("y"), true));
		}
		
	}

	// utiltiy functions
	public Hub hubCreator(MapObject object, String type, int transIdx) {
		float locX = object.getProperties().get("x", Float.class) * 3;
		float locY = object.getProperties().get("y", Float.class) * 3;
		float width = object.getProperties().get("width", Float.class) * 3;
		float height = object.getProperties().get("height", Float.class) * 3;
				
		return new Hub(locX, locY, width, height, type, transIdx);
	}
	
}
