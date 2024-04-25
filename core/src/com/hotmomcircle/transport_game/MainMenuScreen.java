package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
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
	TextButton resumeGame;
	TextButton loadGame;
	TextButton settings;
	TextButton exitGame;
	
	static String savePath = "saves/";
	
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
		camera.position.set(game.SCREEN_WIDTH/2, 5850, 0);
		targetPosition = new Vector3(camera.position);
		
//		Create a stage for our buttons. This is a better way of doing UI elements
//		We will use SpriteBatches for the game itself
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        
        
        
//      Make the table with the buttons
        makeMainMenuTable();
        
//        Add the table to the stage
		stage.addActor(table);
		

		
		

		assetManager.finishLoading();
		
		try {
			map = assetManager.get("maps/mainMenuMap.tmx", TiledMap.class);
			System.out.println("Map loaded successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderer = new OrthogonalTiledMapRenderer(map, 2.0f);
		
	}
	
	
	public void makeMainMenuTable() {
		
//		We will keep the buttons in a table to make handling the layout easier
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(game.SCREEN_WIDTH/3).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH/3);
		table.setDebug(false);
		
		Label titleLabel = new Label("Gem Expedition", skin);
		titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(2.5f); // Increase font size
		table.add(titleLabel).padBottom(20); // Colspan to span across all columns

		
//		Create the buttons 
		newGame = new TextButton("New Game", skin);
		resumeGame = new TextButton("Resume", skin);
		loadGame = new TextButton("Load", skin);
		settings = new TextButton("Settings", skin);
		exitGame = new TextButton("Exit", skin);
		
		
//		Add buttons to the table
		table.row();
		table.add(newGame).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(resumeGame).fillX().uniformX();
		table.row().pad(5, 0, 5, 0);
		table.add(loadGame).fillX().uniformX();
		table.row().pad(5,0,5,0);
		table.add(settings).fillX().uniformX();
		table.row().pad(5, 0, 15, 0);
		table.add(exitGame).fillX().uniformX();
		
		
//		Create button listeners
		
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				newGameDialog();
			}
			
		});
		
		resumeGame.addListener( new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Add continue game functionality
				int successful = game.resumeGame();
				if (successful ==1) {
					dispose();
				}
			}
			
		});
		
		loadGame.addListener( new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Add continue game functionality
				loadGameTable();
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
	
	public void loadGameTable() {
		
		
//		Table that holds title, saves, and back button
		Table loadTable = new Table();
		loadTable.setFillParent(true);
		loadTable.defaults().width(game.SCREEN_WIDTH/2).expandX().fillX();
		loadTable.setWidth(game.SCREEN_WIDTH/2);
		// loadTable.setDebug(true);
		
		Label titleLabel = new Label("Load Game", skin);
		titleLabel.setAlignment(Align.center);
		titleLabel.setFontScale(2.0f); // Increase font size
		loadTable.add(titleLabel).padBottom(10).padTop(10); // Colspan to span across all columns		
		
		table.remove();
		
//		table that holds all the save files
		Table pastSavesTable = new Table();
		pastSavesTable.defaults().width(game.SCREEN_WIDTH/2).expandX().fillX();
		pastSavesTable.setWidth(game.SCREEN_WIDTH/2);
		pastSavesTable.pad(0);
		// pastSavesTable.setDebug(true);
		
		
		
//		Get a list of all the files in the saves folder
        FileHandle localDir = Gdx.files.local(savePath);

        // List all files in the local storage directory
        FileHandle[] files = localDir.list();

        // Iterate over the array of FileHandle objects
        for (FileHandle file : files) {
            // Get the name of each file
            String fileName = file.name();
            
            String text = file.readString();
            
            JsonValue json = new JsonReader().parse(text);
            
            String name = json.getString("name");
            String level = json.getString("currLevel");
            
            TextButton button = new TextButton(name + "    Level: " + level, skin);

            
    		button.addListener( new ChangeListener() {
    			@Override
    			public void changed(ChangeEvent event, Actor actor) {
    				dispose();
    				game.loadGame(fileName);
    			}
    		});
    		
    		pastSavesTable.row();
    		pastSavesTable.add(button).fillX().uniformX();
    		    
        }
        
		
		ScrollPane scrollPane = new ScrollPane(pastSavesTable, skin);
		scrollPane.setScrollingDisabled(true, false); // Disable scrolling in both directions
		scrollPane.setFadeScrollBars(false);
		
		TextButton back = new TextButton("Back", skin);
		
		back.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
//				Remove the load table and add the main menu table back
				loadTable.remove();
				stage.addActor(table);
				
			}
		});
		
		loadTable.row();
		loadTable.add(scrollPane).fillX().fillY();
		
		loadTable.row();
		loadTable.add(back).fillX().fillY();
        
        // Set the position of the scroll pane
		stage.addActor(loadTable);
		
	}
	
//	Pops up the "Please enter your name" dialogue
	public void newGameDialog() {
//		Table that holds title, saves, and back button
		Table newGameTable = new Table();
		newGameTable.setFillParent(true);
		newGameTable.defaults().width(game.SCREEN_WIDTH/2).expandX().fillX();
		newGameTable.setWidth(game.SCREEN_WIDTH/2);
		// newGameTable.setDebug(true);
		
		Label titleLabel = new Label("Please enter your name:", skin);
		titleLabel.setAlignment(Align.center);
		titleLabel.setFontScale(2.0f); // Increase font size
		newGameTable.add(titleLabel).colspan(2).padBottom(10).padTop(10); // Colspan to span across all columns		
		
		
		
        // Create text field for entering name
        final TextField textField = new TextField("", skin);
		newGameTable.row();
		newGameTable.add(textField).colspan(2).fillX().uniformX();

        // Add OK button
        TextButton okButton = new TextButton("OK", skin);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
				if (!textField.getText().trim().isEmpty()) {
					game.newGame(textField.getText());
					dispose();
				}

            }
        });
        

        // Add Cancel button
        TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
				newGameTable.remove();
				stage.addActor(table);
            }
        });
        
        newGameTable.row();
        newGameTable.add(okButton);
        newGameTable.add(cancelButton);

        // Show dialog
        table.remove();
        stage.addActor(newGameTable);

		// auto focus on text input
		textField.getStage().setKeyboardFocus(textField);
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
		map.dispose();
	}


}
