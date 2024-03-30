package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hotmomcircle.transport_game.tools.Camera;

// Main menu screen
// Uses gdx stage to render screen and deal with inputs
// When game is started, it will call GameScreen, which holds the game logic.

public class MainMenuScreen implements Screen {
	final TransportGame game;
	
	AssetManager assetManager;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	OrthographicCamera camera;
	
	Skin skin;
	BitmapFont font;
	
	Table table;
	TextButton newGame;
	TextButton loadGame;
	TextButton settings;
	TextButton exitGame;
	
	Stage stage;
	Vector3 targetPosition;
	float panSpeed = 100; //Sets the speed at which the main menu camera pans 
	
	
	public MainMenuScreen(final TransportGame game) {
		this.game = game;
		
		this.skin = game.skin;
		this.font = game.font;
		
//		Load map
		
		assetManager = new AssetManager();

		//loading map 
		assetManager.setLoader(TiledMap.class,  new TmxMapLoader());
		assetManager.load("maps/mainMenuMap.tmx", TiledMap.class);
		
//		Game camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		camera.position.set(game.SCREEN_WIDTH/2, 2950, 0);
		targetPosition = new Vector3(camera.position);
		
//		Create a stage for our buttons. This is a better way of doing UI elements
//		We will use SpriteBatches for the game itself
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        
        
        
//        skin = new Skin(Gdx.files.internal("uiskin.json")); // Load 
		
//		We will keep the buttons in a table to make handling the layout easier
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(game.SCREEN_WIDTH/3).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH/3);
		table.setDebug(true);
		
		Label titleLabel = new Label("The Burning City", skin);
		titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(3.0f); // Increase font size
		table.add(titleLabel).padBottom(20); // Colspan to span across all columns

		
//		Create the buttons
		newGame = new TextButton("New Game", skin);
		loadGame = new TextButton("Load Game", skin);
		settings = new TextButton("Settings", skin);
		exitGame = new TextButton("Exit", skin);
		
		
//		Add buttons to the table
		table.row();
		table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(loadGame).fillX().uniformX();
		table.row().pad(10,0,10,0);
		table.add(settings).fillX().uniformX();
		table.row().pad(10, 0, 30, 0);
		table.add(exitGame).fillX().uniformX();
		
//        Add the table to the stage
		stage.addActor(table);
		
//		Create button listeners
		
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
			
		});
		
		loadGame.addListener( new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Add continue game functionality
				
			}
			
		});
		
		settings.addListener( new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Add settings functionality
				
			}
			
		});
		
		exitGame.addListener( new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
			
		});
		
		

		assetManager.finishLoading();
		
		try {
			map = assetManager.get("maps/mainMenuMap.tmx", TiledMap.class);
			System.out.println("Map loaded successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderer = new OrthogonalTiledMapRenderer(map);
		
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
        float targetX = camera.position.x + panSpeed * Gdx.graphics.getDeltaTime(); // Adjust the value based on how far you want to slide
        float targetY = camera.position.y; // Keep the y-coordinate the same to slide horizontally
        targetPosition.set(targetX, targetY, 0); // Set the new target position
		
		camera.position.lerp(targetPosition, Gdx.graphics.getDeltaTime() * panSpeed/10);
		camera.update();
		// map render 
		renderer.setView(camera);
		// camera.position.set(player.getX(),player.getY(), 0);

		renderer.render();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// Allows buttons to update when screen is resized.
		stage.getViewport().update(width, height, true);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
		stage.dispose();
		
	}


}
