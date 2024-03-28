package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.entity.Route;

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
    
    public void populatePlanning (Array<Route> routes) {
		// takes Routes as argument from togglePlanning
		// creates new table and populates with routes
		planningTable = new Table();
		planningTable.setFillParent(true);
		planningTable.defaults().width(this.game.SCREEN_WIDTH / 6).expandX().fillX();
		planningTable.setWidth(game.SCREEN_WIDTH / 6);

		for (Route route: routes) {
			planningTable.row().pad(10, 0, 10, 0);
			TextButton routeButton = new TextButton(route.toString(), skin);
			planningTable.add(routeButton);
            // event listener allows selection
            // right now it just closes the Planning UI
			routeButton.addListener(new InputListener() {

                @Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // handling of the Guided Transport is actually here
					deactivatePlanning();
                    player.setX(route.getDestX());
                    player.setY(route.getDestY());
					screen.camera.zoomIn();
					return true;
				}
			});
			
		}
		stage.addActor(planningTable);
	}

	public void activatePlanning(Array<Route> routes) {
		// planning mode "active" or not
		// for conditional planning UI render
		// calls populatePlanning on False -> True toggle
		// passing routes from Node to populate options
		if (active != true) {
			active = true;
			populatePlanning(routes);
		}
	}

	public void deactivatePlanning() {
		if (active) {
		stage.getRoot().removeActor(planningTable);
		active = false;
		} 
	}
}
