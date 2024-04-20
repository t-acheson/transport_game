package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;

// Interactable transport objects (car, bike etc.)
public abstract class Transport_OBJ extends Object {
	GameScreen game;
	Rectangle object_rect;
	Rectangle player_rect;
	
	public void render(SpriteBatch batch) throws Exception{
		batch.draw(objImg, x, y, 0, 0, game.getTileSize(), game.getTileSize(), game.scale, game.scale, 0, 0, 0, objImg.getWidth(), objImg.getHeight(), false, false);

		object_rect = new Rectangle(x, y, objImg.getWidth(), objImg.getHeight());
		player_rect = game.player.getPlayerRectangle();

		if (player_rect.overlaps(object_rect)){
			System.out.println("Collision");
			game.player.Collision();
		}
	}
	
//	This might be better to bring up to the object class
	public abstract void update(int i);
	
//	Holds the logic for interaction with transport class, will be different for each object.
	public abstract void interact();
	
	public void dispose() {
		objImg.dispose();
	}
	
	public Rectangle getObjectRectangle() {
		return interactionRadius;
	}
	

	
}
