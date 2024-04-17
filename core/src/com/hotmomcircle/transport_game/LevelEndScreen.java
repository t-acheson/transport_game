package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// Main menu screen
// Uses gdx stage to render screen and deal with inputs
// When game is started, it will call GameScreen, which holds the game logic.

public class LevelEndScreen implements Screen {
	final TransportGame game;
	
	AssetManager assetManager;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	OrthographicCamera camera;
	
	Skin skin;
	BitmapFont font;
	
	Table table;
	TextButton newGame;
	TextButton continueGame;
	TextButton settings;
	TextButton exitGame;
	
	static String savePath = "saves/";
	
	Stage stage;

	
	
	public LevelEndScreen(final TransportGame game) {
		this.game = game;
		this.skin = game.skin;
		this.font = game.font;


		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

//      Make the table with the buttons
        makeLevelEndTable();
        
//        Add the table to the stage
		stage.addActor(table);
		
		
		
	}
	
	
	public void makeLevelEndTable() {
		
//		We will keep the buttons in a table to make handling the layout easier
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(game.SCREEN_WIDTH/3).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH/3);
		table.setDebug(true);
		
		Label titleLabel = new Label("Gem Expedition", skin);
		titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(2.5f); // Increase font size
		table.add(titleLabel).padBottom(20); // Colspan to span across all columns

		
//		Create the buttons 
        continueGame = new TextButton("Continue", skin);
		newGame = new TextButton("New Game", skin);
		settings = new TextButton("Settings", skin);
		exitGame = new TextButton("Exit", skin);
		
		
//		Add buttons to the table
		table.row();
		table.add(continueGame).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(newGame).fillX().uniformX();
		table.row().pad(5,0,5,0);
		table.add(settings).fillX().uniformX();
		table.row().pad(5, 0, 15, 0);
		table.add(exitGame).fillX().uniformX();
		
		
//		Create button listeners
		
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				game.newGame();
			}
			
		});
		
		continueGame.addListener( new ChangeListener() {

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
