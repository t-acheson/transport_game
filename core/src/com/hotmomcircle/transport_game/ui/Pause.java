package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.MainMenuScreen;

public class Pause {
    private TransportGame game;
    private Table pauseTable;

	public boolean active = false;
    public Skin skin;
    public Stage stage;

    public Pause(TransportGame game, GameScreen screen, Stage stage, Skin skin) {
        // constructor just needs to know what it can work with
        this.game = game;
        this.stage = stage;
        this.skin = skin;
    }
    
    public void showPause () {
		// takes Routes as argument from togglePlanning
		// creates new table with screen options
        Gdx.input.setInputProcessor(stage);
		pauseTable = new Table();
		pauseTable.setFillParent(true);
		pauseTable.defaults().width(this.game.SCREEN_WIDTH / 6).expandX().fillX();
		pauseTable.setWidth(game.SCREEN_WIDTH / 6);
        pauseTable.row().pad(10, 0, 10, 0);
        TextButton resumeButton = new TextButton("Resume", skin);

        resumeButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                deactivatePause();
                game.resume();
                return true;
            }
        });


        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        mainMenuButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                deactivatePause();
				game.setScreen(new MainMenuScreen(game));

                return true;
            }
        });


        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                deactivatePause();
				Gdx.app.exit();
                return true;
                }
        });

        pauseTable.add(resumeButton);
        pauseTable.add(mainMenuButton);
        pauseTable.add(quitButton);

		stage.addActor(pauseTable);
	}


	public void deactivatePause() {
		stage.getRoot().removeActor(pauseTable);
    }
}



