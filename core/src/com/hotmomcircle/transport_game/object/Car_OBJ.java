package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.hotmomcircle.transport_game.GameScreen;

public class Car_OBJ extends Transport_OBJ {
	
	
	public Car_OBJ(GameScreen game, int x, int y, boolean interactable) {
		this.game = game;
		this.x = x;
		this.y = y;
		
		objImg = new Texture(Gdx.files.internal("./objects/car_left.png"));
		
//		Interaction rectangle
		this.interactable = true;
		interactionRadius = new Rectangle(x, y, game.getTileSize()*2, game.getTileSize()*2);
		
	}
	
	public void interact() {
		game.player.getOnCar();
	}
	
}
