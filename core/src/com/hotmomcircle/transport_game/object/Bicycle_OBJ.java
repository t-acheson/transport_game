package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hotmomcircle.transport_game.GameScreen;

public class Bicycle_OBJ extends Object {
	
	GameScreen game;
	
	public Bicycle_OBJ(GameScreen game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
		
		objImg = new Texture(Gdx.files.internal("./objects/bicycle.png"));
		
	}
	
	public void render(SpriteBatch batch) throws Exception{
		batch.draw(objImg, x, y, 0, 0, game.getTileSize(), game.getTileSize(), game.scale, game.scale, 0, 0, 0, objImg.getWidth(), objImg.getHeight(), false, false);
	}
}
