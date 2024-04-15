package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.entity.Player;

public class EducationalPopup {
    private boolean bike = false;
    private boolean bus = false;
    private boolean car = false;
    private boolean walking = false;
    private boolean luas = false;
    private boolean gem = false;
    private Player player;
    private TransportGame game;
    private Stage stage;
    private Skin skin;
    private Table uiTable;

    public EducationalPopup(TransportGame game, GameScreen screen, Stage stage, Skin skin, Player player) {
        // constructor just needs to know what it can work with
        this.game = game;
        this.stage = stage;
        this.skin = skin;
        this.player = player;
    }

    public void showUI () {
		// takes Routes as argument from togglePlanning
		// creates new table with screen options
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.defaults().width(this.game.SCREEN_WIDTH / 6).expandX().fillX();
		uiTable.setWidth(game.SCREEN_WIDTH /3);
        uiTable.row().pad(10, 0, 10, 0);
        uiTable.bottom();

        Label zoomLabel = new Label("Zoom: +/-", skin);
        Label panLabel = new Label("Pan: WASD", skin);
        Label closeMapLabel = new Label("Close Map: M", skin);

        uiTable.add(zoomLabel);
        uiTable.add(panLabel);
        uiTable.add(closeMapLabel);


		stage.addActor(uiTable);

        uiTable.addAction(Actions.sequence(
            Actions.delay(7), // Delay for 7 seconds
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    uiTable.remove(); // Remove the table from the stage
                }
            })
        ));
	}

    public String getMessage(String inputType){
        String message;


        return message;
    }
    


}
