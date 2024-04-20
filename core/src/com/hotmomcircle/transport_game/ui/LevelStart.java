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
	
	Table table;
	
	public LevelStart(TransportGame game, GameScreen screen, Stage stage, Skin skin) {
		this.game = game;
		this.screen = screen;
		this.stage = stage;
		this.skin = skin;
		showLevelStart();
	}
	
	public void showLevelStart() {
		table = new Table();
		table.setFillParent(true);
		table.defaults().width(this.game.SCREEN_WIDTH / 6).expandX().fillX();
		table.setWidth(game.SCREEN_WIDTH / 6);
		
		
		TextButton text = new TextButton("PLS", skin);
		
		table.row();
		table.add(text).fillX().uniformX();

		stage.addActor(table);
		System.out.println("here");
		
		
	}
}
