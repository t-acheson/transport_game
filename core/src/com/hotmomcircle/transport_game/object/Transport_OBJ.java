package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

// Interactable transport objects (car, bike etc.)
public abstract class Transport_OBJ extends Object {
	
	GameScreen game;
	Rectangle object_rect;
	Rectangle player_rect;
	BitmapFont font = create();
	//BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/game_font.ttf"));
	
	public BitmapFont create () {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/game_font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		return generator.generateFont(parameter);
	}
	
	public void render(SpriteBatch batch) throws Exception{
		batch.draw(objImg, x, y, 0, 0, game.getTileSize(), game.getTileSize(), game.scale, game.scale, 0, 0, 0, objImg.getWidth(), objImg.getHeight(), false, false);

		object_rect = new Rectangle(x, y, objImg.getWidth(), objImg.getHeight());
		player_rect = game.player.getPlayerRectangle();
		

		if (player_rect.overlaps(object_rect)){
			System.out.println("Collision");
			game.player.Collision(game.player.getRectangle());

			font.draw(batch, popUp, this.object_rect.x, this.object_rect.y + 100);
		}
	}
	
//	This might be better to bring up to the object class
	public void update(int i) {
		
		if(game.player.canGetOnTransport(getObjectRectangle()) && Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			interact();
			game.transport_OBJs.remove(i);
		}
	}
	
//	Holds the logic for interaction with transport class, will be different for each object.
	public abstract void interact();
	
	public void dispose() {
		objImg.dispose();
	}
	
	public Rectangle getObjectRectangle() {
		return interactionRadius;
	}
	
}
