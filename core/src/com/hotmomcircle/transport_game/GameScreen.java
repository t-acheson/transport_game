package com.hotmomcircle.transport_game;

import java.util.ArrayList;
import java.util.regex.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.object.Bicycle_OBJ;
import com.hotmomcircle.transport_game.object.Car_OBJ;
import com.hotmomcircle.transport_game.object.Transport_OBJ;
import com.hotmomcircle.transport_game.entity.Route;
import com.hotmomcircle.transport_game.tools.Camera;
import com.hotmomcircle.transport_game.entity.Node;
import com.hotmomcircle.transport_game.ui.Planning;
import com.hotmomcircle.transport_game.ui.Points;
import com.hotmomcircle.transport_game.ui.gemArrow;
//map imports below 
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//

// This will be the screen 
public class GameScreen implements Screen {

	TransportGame game;

	SpriteBatch batch;

	private int originalTileSize = 16;
	public int scale = 2;
	private int tileSize = originalTileSize * scale;
	
	//initializing map 
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	Texture img;
	public Player player;
	public ArrayList<Transport_OBJ> transport_OBJs = new ArrayList<Transport_OBJ>();
	   
	public Camera camera;
	
	public Array<Gem> gems;

	// list of Nodes for interaction
	public Array<Node> nodes;
	// list of Routes for planning UI
	public Array<Route> routes;
	   
   // Variables associated with the pause / game state
	private int GAME_STATE;
	private final int GAME_RUNNING = 0;
	private final int GAME_PAUSED = 1;
	public Skin skin;
	public BitmapFont font;

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

	// asset manager to implement uiskin.json
	// TODO best practise to implement all our assets this way?
	public AssetManager assetManager;

	//gemArrow instance 
	private gemArrow gemArrowUI;


	public GameScreen(TransportGame game) {
		this.game = game;
		this.font = game.font;
		this.skin = game.skin;

		this.batch = game.batch;
		
		// for the pause / play feature
		GAME_STATE = GAME_RUNNING;

		
//		Load assets - Load all textures, maps, etc here with the assetManager before going to the game screen.
//		In the mean time show a loading screen
		assetManager = new AssetManager();

		//loading map 
		assetManager.setLoader(TiledMap.class,  new TmxMapLoader());
		assetManager.load("bigMap.tmx", TiledMap.class);
		
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
			    "./car/car_right.png", "./car/car_right.png"
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
			map = assetManager.get("bigMap.tmx", TiledMap.class);
			System.out.println("Map loaded successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// routes for node testing
		routes = new Array<Route>();
		for (int i = 1; i < 4; i++) {
			routes.add(new Route(this, 0, 0, 32, 32, "gem.png", 900, i * 100 + 100));
		}

		// initialise Node array
		nodes = new Array<Node>();

		for (MapLayer layer : map.getLayers()) {
			// handle collidable
			if (Pattern.compile("collidable").matcher(layer.getName()).find()) {
				// make collidable object
			}

			if (Pattern.compile("route").matcher(layer.getName()).find()) {
				System.out.println(layer.getName() + " route");
				if (Pattern.compile("bus").matcher(layer.getName()).find()) {
					// connect bus nodes together
				}

				if (Pattern.compile("tram").matcher(layer.getName()).find()) {
					// connect tram nodes together
				}
			}
		}


		renderer = new OrthogonalTiledMapRenderer(map,3);
		//

		player = new Player(this, 700, 300, 32, 32, "./foot/player_down1.png");
		
		gems = new Array<Gem>();
		gems.add(new Gem(this, 400, 400, 16, 16, "gem.png"));
		gems.add(new Gem(this, 200, 200, 16, 16, "gem.png"));
		gems.add(new Gem(this, 300, 300, 16, 16, "gem.png"));
		
		transport_OBJs.add(new Bicycle_OBJ(this, 300, 100, true));
		transport_OBJs.add(new Bicycle_OBJ(this, 400, 100, true));
		transport_OBJs.add(new Bicycle_OBJ(this, 500, 100, true));
		
		transport_OBJs.add(new Car_OBJ(this, 400, 150, true));
		
		// create the camera and the SpriteBatch
		camera = new Camera(game, player);

		// Stage is the layer on which we draw the UI
		// Likely keeps things cleaner as we make
		// the map more complicated and add objects
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// Asset manager instansiation
		assetManager.load("uiskin.json", Skin.class);


		// table to hold UI elements
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(game.SCREEN_WIDTH / 6).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH / 6);
		table.left().top();

		// UI scores
		points = new Points("0", skin);
		carbon = new Points("0", skin);
		freshness = new Points("100", skin);
		
		gemArrowUI = new gemArrow(skin, player, gems, table); 

		table.add(gemArrowUI).top().left();

		// fill table with UI scores
		table.add(new Label("Points: ", skin));
		table.add(points).fillX().uniformX();
		table.add(new Label("Carbon: ", skin));
		table.add(carbon).fillX().uniformX();
		table.add(new Label("Freshness: ", skin));
		table.add(freshness).fillX().uniformX();

		//initalise gemArrow 

		// add table to stage
		stage.addActor(table);

		// Planning UI
		planningUI = new Planning(game, this, stage, skin, player);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {

		// pauses the game if it isnt already paused - prevents multiple inputs
		if(Gdx.input.isKeyPressed(Input.Keys.P) && GAME_STATE != GAME_PAUSED) {
			pause();
		} 
		// resumes game if it isn't already running
		if(Gdx.input.isKeyPressed(Input.Keys.R) && GAME_STATE != GAME_RUNNING) {
			resume();
		} 
		if (GAME_STATE == GAME_PAUSED){
			// Clear the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			// draws the text on to the screen in the centre
			batch.begin();
			font.draw(batch, "Game Paused", game.getSCREEN_WIDTH() / 2 - 60, game.getSCREEN_HEIGHT() / 2 + 50);
			font.draw(batch, "Press 'R' to Resume", game.getSCREEN_WIDTH() / 2 - 90, game.getSCREEN_HEIGHT() / 2);
			batch.end();

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
				}
			}

		
		
		for(int i = 0; i < transport_OBJs.size(); i++) {
				transport_OBJs.get(i).update(i);
		}

		// tell the camera to update its matrices.
		camera.update();



		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		try {
			for (Transport_OBJ transport: transport_OBJs) {
				if (transport != null) {					
					transport.render(batch);
				}
			}
			
			for (Gem gem : gems) {
				gem.render(batch);
			}
			
			for (Node node: nodes) {
				node.render(batch);
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

		// UI draw
		stage.act(delta);
		stage.draw();

	}
		 // Update the gemArrow UI with the current player and gem positions
		gemArrowUI.update(player, gems);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		System.out.println("Game Paused");
		GAME_STATE = GAME_PAUSED;
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
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
		assetManager.dispose();

	}

	public int getTileSize() {
		return tileSize;
	}
	
	public void addBike(int x, int y) {
		transport_OBJs.add(new Bicycle_OBJ(this, x, y, true));
	}
	
	public void addCar(int x, int y) {
		transport_OBJs.add(new Car_OBJ(this, x, y, true));
	}

	
}
