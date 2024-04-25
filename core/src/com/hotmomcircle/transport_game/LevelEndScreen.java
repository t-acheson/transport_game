package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hotmomcircle.transport_game.tools.Camera;

public class LevelEndScreen implements Screen {
    final TransportGame game;
    final ParentGame parentGame;
    final GameScreen gameScreen;
    
	public OrthogonalTiledMapRenderer renderer;
	public Camera camera;
	
    Skin skin;
    BitmapFont font;
    
    Stage stage;
    Table table;
    Label levelCompletedLabel;
    Label scoreLabel;
    TextButton continueButton;
    TextButton quitButton;
    Screen screen;

    String completed;
    String scoreText;

    public LevelEndScreen(final TransportGame game, ParentGame parentGame, GameScreen gameScreen, Camera camera, OrthogonalTiledMapRenderer renderer) {
        this.game = game;
        this.skin = game.skin;
        this.font = game.font;
        this.parentGame = parentGame;
        this.gameScreen = gameScreen;
        this.camera = camera;
        this.renderer = renderer;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

    }
    
    public void makeLevelEndTable(boolean levelSuccessful, int level, String score) {
        table = new Table();
        table.setFillParent(true);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

       
        if (levelSuccessful){
            completed = "Completed";
            scoreText = "Current";
            
            continueButton = new TextButton("Continue", skin);
             continueButton.addListener(new ChangeListener(){
     			@Override
    			public void changed(ChangeEvent event, Actor actor) {
    				// TODO Add continue game functionality
     				gameScreen.music.stop();
    				parentGame.levelUp(score);
    				}
             });
        } else {
            completed = "Failed";
            scoreText = "Final";

            continueButton = new TextButton("Restart", skin);
            continueButton.addListener(new ChangeListener(){
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                	gameScreen.music.stop();
                    parentGame.startLevel();
                }
            });

        }
        levelCompletedLabel = new Label("Level "+ level + " " + completed, skin);
        levelCompletedLabel.setAlignment(Align.center);

        scoreLabel = new Label(scoreText +" Points: "+ score, skin);
        scoreLabel.setAlignment(Align.center);

        quitButton = new TextButton("Quit", skin);

        table.add(levelCompletedLabel).colspan(2).padBottom(10);
        table.row(); // Move to next row
        table.add(scoreLabel).colspan(2).padBottom(20);
        table.row(); // Move to next row
        table.add(continueButton).width(200).padRight(5);
        table.add(quitButton).width(200).padLeft(5);

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    public void updateLevelEndScreen(boolean levelCompleted, int level, String score){

        makeLevelEndTable(levelCompleted, level, score);

        stage.addActor(table);
    }

    public void gameOverScreen(int level, String score){
        updateLevelEndScreen(false, level, score);
    }

    @Override
    public void render(float delta) {
    	renderer.render();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }



    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
    }

    // Implement other required methods: resize, pause, resume, hide, dispose, etc.
}
