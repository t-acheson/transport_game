package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.MainMenuScreen;

public class WorldMapUI {

    private TransportGame game;
    private Table uiTable;

	public boolean active = false;
    public Skin skin;
    public Stage stage;

    public WorldMapUI(TransportGame game, GameScreen screen, Stage stage, Skin skin) {
        // constructor just needs to know what it can work with
        this.game = game;
        this.stage = stage;
        this.skin = skin;
    }
    
    public void showUI () {
		// takes Routes as argument from togglePlanning
		// creates new table with screen options
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.defaults().width(this.game.SCREEN_WIDTH / 6).expandX().fillX();
		uiTable.setWidth(game.SCREEN_WIDTH / 3);
        uiTable.row().pad(10, 0, 10, 0);
        uiTable.bottom();

        Label zoomLabel = new Label("Zoom: ↑↓", skin);
        Label panLabel = new Label("Pan: WASD", skin);
        Label closeMapLabel = new Label("Close Map: M", skin);

        uiTable.add(zoomLabel);
        uiTable.add(panLabel);
        uiTable.add(closeMapLabel);


		stage.addActor(uiTable);
	}

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}



