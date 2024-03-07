package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;

public class Bicycle_OBJ extends Object {
	
	GameScreen game;
	
	public Bicycle_OBJ(GameScreen game, int x, int y, boolean interactable) {
		this.game = game;
		this.x = x;
		this.y = y;
		
		objImg = new Texture(Gdx.files.internal("./objects/bicycle.png"));
		
//		Interaction rectangle
		this.interactable = true;
		interactionRadius = new Rectangle(x, y, game.getTileSize()*2, game.getTileSize()*2);
		
	}
	
	public void render(SpriteBatch batch) throws Exception{
		batch.draw(objImg, x, y, 0, 0, game.getTileSize(), game.getTileSize(), game.scale, game.scale, 0, 0, 0, objImg.getWidth(), objImg.getHeight(), false, false);
	}
	
	public void update(int i) {
		if(game.player.getPlayerRectangle().overlaps(getObjectRectangle()) && Gdx.input.isKeyPressed(Keys.SPACE) && game.player.currTransport() == game.player.FOOT) {
			interact();
			this.dispose();
			game.bikes[i] = null;
		}
	}
	
	public void interact() {
		game.player.getOnBike();
	}
	
	public void dispose() {
		objImg.dispose();
	}
	
}
