package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hotmomcircle.transport_game.Game;
import com.hotmomcircle.transport_game.transport.Transport;

//This will hold the player class. 
//Player should be able to move, be drawn, and will own the transport methods
// Player should also have the code to handle movement

public class Player extends Entity {
	
	Game game;
	private Texture playerImage;
	private Texture bikeImage;
	private Texture carImage;
	private Transport[] transport = new Transport[3];
	private int transIdx = 0;
	private int stamina;
	
	
	public Player(Game game) {
		this.game = game;
//		Initialize the textures
//		Initialize player x and y positions
		y = 100;
		x = 100;
		
		playerImage = new Texture(Gdx.files.internal("player_down1.png"));
		transport[0] = new Transport("Foot", 4, playerImage);
		
		
	}
	
	
	public void render(SpriteBatch batch) {
		batch.draw(transport[transIdx].image, x, y, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), game.scale, game.scale, 0, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), false, false);
	}
	
	
	
}
