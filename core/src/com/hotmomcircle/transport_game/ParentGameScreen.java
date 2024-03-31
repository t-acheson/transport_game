package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Screen;

// Holds an instance of the game
// Decides which level the player is playing and loads in the logic 
public class ParentGameScreen implements Screen {
	
	TransportGame game;
	
//	Constructor for new game
	public ParentGameScreen(TransportGame game) {
		this.game = game;
		game.setScreen(new GameScreen(game, this));
	}
	
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
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
	
}
