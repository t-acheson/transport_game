package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;

public class Bicycle_OBJ extends Transport_OBJ {
	
	
	public Bicycle_OBJ(GameScreen game, int x, int y, boolean interactable) {
		this.game = game;
		this.x = x;
		this.y = y;

		objImg = game.assetManager.get("./objects/bicycle.png", Texture.class);
		
//		Interaction rectangle
		this.interactable = true;
		interactionRadius = new Rectangle(x, y, game.getTileSize()*2, game.getTileSize()*2);
		
	}
	
	public void update(int i)  {
		
		if(game.player.canGetOnTransport(getObjectRectangle()) && Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			interact();
			game.bike_OBJs.remove(i);
		}
	}
	
	public void interact() {
		game.player.getOnBike();
	}
	
}
