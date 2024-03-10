package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.entity.Route;
import com.hotmomcircle.transport_game.entity.Node;
import com.hotmomcircle.transport_game.ui.Points;
//map imports below 
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
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
	
	public Array<Gem> gems;

	// list of Nodes for interaction
	public Array<Node> nodes;

	// list of Routes for planning UI
	public Array<Route> routes;
	   
   private OrthographicCamera camera;
   // Variables associated with the pause / game state
	private int GAME_STATE;
	private final int GAME_RUNNING = 0;
	private final int GAME_PAUSED = 1;
	private BitmapFont font = new BitmapFont();

	// Route planing UI conditional
	private boolean planning = false;
	private Table planningTable;

	//UI Skin
	public Skin skin;

	// Stage for UI components
	private Stage stage;
	private Table table;

	// scores need to be public so Player can modify
	public Points points;
	public Points carbon;
	public Points freshness;

	// asset manager to implement uiskin.json
	// TODO best practise to implement all our assets this way?
	private AssetManager assetManager;
	
	public GameScreen(TransportGame game) {
		this.game = game;

		this.batch = game.batch;
		
		// for the pause / play feature
		GAME_STATE = GAME_RUNNING;
		
		//loading map 
		TmxMapLoader loader = new TmxMapLoader();
		try {
			map = loader.load("trialMapwithObjects.tmx");
			System.out.println("Map loaded successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// routes for node testing
		routes = new Array<Route>();
		for (int i = 1; i < 4; i++) {
			routes.add(new Route(0, 0, 32, 32, "gem.png", i * 100 + 500, i * 100 + 100));
		}

		// initialise Node array
		nodes = new Array<Node>();

		for (MapLayer layer : map.getLayers()) {
            // Check if the layer contains objects
			// AND create Node(s) for the object layer
            if (layer.getObjects() != null && layer.getName().equals("rec_layer")) {
                // Retrieve objects from the layer
                for (MapObject object : layer.getObjects()) {
					// get X and Y for each object
                    float locX = object.getProperties().get("x", Float.class);
                    float locY = object.getProperties().get("y", Float.class);
					// pass to Node constructor
					nodes.add(new Node(locX, locY, 16, 16, "gem.png", routes));
                }
            }
		}


		renderer = new OrthogonalTiledMapRenderer(map);
		//

		player = new Player(this, 700, 300, 32, 32, "foot/player_down1.png");
		
		gems = new Array<Gem>();
		gems.add(new Gem(400, 400, 16, 16, "gem.png"));
		gems.add(new Gem(200, 200, 16, 16, "gem.png"));
		gems.add(new Gem(300, 300, 16, 16, "gem.png"));

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

		// Stage is the layer on which we draw the UI
		// Likely keeps things cleaner as we make
		// the map more complicated and add objects
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		// Asset manager instansiation
		assetManager = new AssetManager();
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

		// fill table with UI scores
		table.add(new TextField("Points: ", skin));
		table.add(points).fillX().uniformX();
		table.add(new TextField("Carbon: ", skin));
		table.add(carbon).fillX().uniformX();
		table.add(new TextField("Freshness: ", skin));
		table.add(freshness).fillX().uniformX();

		// add table to stage
		stage.addActor(table);

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
			camera.position.set(player.getX(),player.getY(),0);

			renderer.render();
			//

			for (Gem gem : gems) {
				if (player.getRectangle().overlaps(gem.getRectangle())) {
					gem.dispose();
					gems.removeValue(gem, true);
				points.setText("50");
				}
			}

		// TODO Auto-generated method stub
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.

		// tell the camera to update its matrices.
		camera.update();



		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		try {
			player.render(batch);
			for (Gem gem : gems) {
				gem.render(batch);
			}
			for (Node node: nodes) {
				node.render(batch);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		batch.end();

		// conditionally draw Route planning
		if (this.planning) {

		}

		// UI draw
		stage.act(delta);
		stage.draw();

		}
		
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

	public void populatePlanning (Array<Route> routes) {
		planningTable = new Table();
		for (Route route: routes) {
			table.add(new TextField(route.getDest(), skin));
		}
	}

	public void togglePlanning(Array<Route> routes) {
		if (planning) {
			planning = false;
		} else {
			planning = true;
			populatePlanning(routes);
		}
	}
}
