package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// Main menu screen
// Uses gdx stage to render screen and deal with inputs
// When game is started, it will call GameScreen, which holds the game logic.

public class MainMenuScreen implements Screen {
	final TransportGame game;
	
	OrthographicCamera camera;
	
	Skin skin;
	
	Table table;
	TextButton newGame;
	TextButton loadGame;
	TextButton settings;
	TextButton exitGame;
	
	Stage stage;
	
	
	public MainMenuScreen(final TransportGame game) {
		this.game = game;
		
//		Game camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		
//		Create a stage for our buttons. This is a better way of doing UI elements
//		We will use SpriteBatches for the game itself
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

//      Sets the theme of our buttons, this will have to be updated when we decide on our style
		skin = new Skin(Gdx.files.internal("uiskin.json")); // Load skin for buttons
		
//		We will keep the buttons in a table to make handling the layout easier
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(game.SCREEN_WIDTH/3).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH/3);
		table.setDebug(true);
		
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
		
		

        
		
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		camera.update();

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
