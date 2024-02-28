package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.transport.Transport;

//This will hold the player class. 
//Player should be able to move, be drawn, and will own the transport methods
// Player should also have the code to handle movement

public class Player extends Entity {
	
	GameScreen game;
	private Texture bikeImage;
	private Texture carImage;
	private Transport[] transport = new Transport[3];
	private int transIdx = 0; //Index corresponding to which transport the player is currently on
	private int stamina;
	
	private String direction = "down";
	
	
	public Player(GameScreen game) {
		this.game = game;
//		Initialize the textures
//		Initialize player x and y positions
		y = 100;
		x = 100;
		Texture[] playerTextures = new Texture[8];
		playerTextures[0] = new Texture(Gdx.files.internal("player_up1.png"));
		playerTextures[1] = new Texture(Gdx.files.internal("player_up2.png"));
		playerTextures[2] = new Texture(Gdx.files.internal("player_down1.png"));
		playerTextures[3] = new Texture(Gdx.files.internal("player_down2.png"));
		playerTextures[4] = new Texture(Gdx.files.internal("player_left1.png"));
		playerTextures[5] = new Texture(Gdx.files.internal("player_left2.png"));
		playerTextures[6] = new Texture(Gdx.files.internal("player_right1.png"));
		playerTextures[7] = new Texture(Gdx.files.internal("player_right2.png"));
		
		transport[0] = new Transport(game, "Foot", 200, playerTextures);
		
		
	}
	
	
	public void render(SpriteBatch batch) throws Exception {
		
//		Move the player 
		if(Gdx.input.isKeyPressed(Input.Keys.W)) { 
			direction = "up";
			y += getSpeed() * Gdx.graphics.getDeltaTime();
			}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) { 
			direction = "down";
			y -= getSpeed() * Gdx.graphics.getDeltaTime();
			}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) { 
			direction = "left";
			x -= getSpeed() * Gdx.graphics.getDeltaTime();
			}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) { 
			direction = "right";
			x += getSpeed() * Gdx.graphics.getDeltaTime();
			}
		
		
//		Handle 
		
		
		
		
		currTransport().render(batch);
//		batch.draw(transport[transIdx].image, x, y, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), game.scale, game.scale, 0, 0, 0, transport[transIdx].image.getWidth(), transport[transIdx].image.getHeight(), false, false);
	}
	
	public int getSpeed() {
		return transport[transIdx].speed;
	}
	
	public String getDirection() {
		return direction;
	}
	
	
//	Returns the current transport method
	public Transport currTransport() {
		return transport[transIdx];
	}
	
	
	
}
