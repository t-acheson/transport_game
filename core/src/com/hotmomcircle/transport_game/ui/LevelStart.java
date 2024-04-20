package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;

public class LevelStart {
	private TransportGame game;
	private GameScreen screen;
	private Stage stage;
	private Skin skin;
	private int level;
	
	Table table;
	
	public LevelStart(TransportGame game, GameScreen screen, Stage stage, Skin skin, int level) {
		this.game = game;
		this.screen = screen;
		this.stage = stage;
		this.skin = skin;
		this.level = level;
		showLevelStart();
	}
	
	public void showLevelStart() {
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(this.game.SCREEN_WIDTH / 6).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH / 6);
		
		
		Label title = new Label("Level " + level, skin);
		Label instructions = new Label("Collect all the gems before your time (and carbon) runs out!", skin);
		Label cont = new Label("Press SPACE to continue", skin);
		
		table.row().pad(5, 0, 15, 0);
		table.add(title).fillX().uniformX();
		table.row().pad(5,0,5,0);
		table.add(instructions).fillX().uniformX();
		table.row().pad(5, 0, 15, 0);
		table.add(cont).fillX().uniformX();

		stage.addActor(table);
		System.out.println("here");
		
		
	}
}
