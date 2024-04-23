package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;

public class LevelStart {
	private TransportGame game;
	private GameScreen screen;
	private Stage stage;
	private Skin skin;
	private int level;
	private Array<String> captions;
	
	Table table;
	
	public LevelStart(TransportGame game, GameScreen screen, Stage stage, Skin skin, int level, Array<String> captions) {
		this.game = game;
		this.screen = screen;
		this.stage = stage;
		this.skin = skin;
		this.level = level;
		this.captions = captions;
		showLevelStart();
	}
	
	public void showLevelStart() {
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(this.game.SCREEN_WIDTH).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH);
		
		
		Label title = new Label("Level " + level, skin);
		Label instructions = new Label("Collect all the gems before your time (and carbon) runs out!", skin);
		
		table.row().pad(5, 5, 15, 5);
		table.add(title).fillX().uniformX();
		for(String caption: captions) {
			Label captionLabel = new Label(caption, skin);
			table.row().pad(5,5,5,5);
			table.add(captionLabel).fillX().uniformX();
			
		}

		stage.addActor(table);
		System.out.println("here");
		
		
	}
}
