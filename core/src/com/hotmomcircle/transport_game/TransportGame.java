package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransportGame extends Game {
	

	public int SCREEN_WIDTH = 800;
	public int SCREEN_HEIGHT = 480;
	public BitmapFont font;
	
	SpriteBatch batch;

   

	@Override
	public void create () {

		batch = new SpriteBatch();
		
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this)); // I'm not sure what the set screen function does
		
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public int getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}

	public int getSCREEN_HEIGHT(){
		return SCREEN_HEIGHT;
	}
	
}
