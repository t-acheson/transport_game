package com.hotmomcircle.transport_game.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.entity.Hub;
import com.hotmomcircle.transport_game.entity.Player;

public class Planning {
    private TransportGame game;
	private GameScreen screen; // passing screen for camera control 
    private Table planningTable;
    private Player player;

	public boolean active = false;
    public Skin skin;
    public Stage stage;

    public Planning(TransportGame game, GameScreen screen, Stage stage, Skin skin, Player player) {
        // constructor just needs to know what it can work with
        this.game = game;
        this.stage = stage;
        this.skin = skin;
        this.player = player;
		this.screen = screen;
    }
    
    public void populatePlanning (ArrayList<Hub> hubs) {
		// takes Routes as argument from togglePlanning
		// creates new table and populates with routes
		planningTable = new Table();
		planningTable.setFillParent(true);
		// planningTable.defaults().width(this.game.SCREEN_WIDTH / 6).expandX().fillX();
		// planningTable.setWidth(game.SCREEN_WIDTH / 6);

		for (Hub hub: hubs) {
			planningTable.row().pad(2, 0, 0, 0);
			TextButton routeButton = new TextButton(hub.toString(), skin);
			planningTable.add(routeButton).pad(0,0,0,0).row();
            // event listener allows selection
            // right now it just closes the Planning UI
			routeButton.setColor(1, 1, 1, 0.3f);
			routeButton.addListener(new InputListener() {

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					// fill on hover
					routeButton.setColor(1, 1, 1, 1); 
				}
				
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					// back to opaque on exit
					routeButton.setColor(1, 1, 1, 0.3f);
				}

                @Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // handling of the Guided Transport is actually here
					deactivatePlanning();
                    player.setX(hub.getX());
                    player.setY(hub.getY());
					screen.showWorldMap ^= true;
					return true;
				}
			});
			
		}
		stage.addActor(planningTable);
		planningTable.left();
	}

	public void activatePlanning(ArrayList<Hub> hubs) {
		// planning mode "active" or not
		// for conditional planning UI render
		// calls populatePlanning on False -> True toggle
		// passing routes from Node to populate options
		if (active != true) {
			Gdx.input.setInputProcessor(this.stage);
			active = true;
			populatePlanning(hubs);
			screen.showWorldMap ^= true;
		}
	}

	public void deactivatePlanning() {
		if (active) {
		stage.getRoot().removeActor(planningTable);
		active = false;
		} 
	}
}
