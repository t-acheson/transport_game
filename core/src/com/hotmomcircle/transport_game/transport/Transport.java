package com.hotmomcircle.transport_game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

// Transport superclass.
// Foot, bike and car will all be instances of this class
// It will own the image for each method of transport (player walking, player on bike, car)
//It will also own speed, but not position as this will be handled by the player class

public class Transport {
	private int footprint;
	private int staminaCost;
	public String name;
	public int speed;
	public Texture image;

	public Transport(String name, int speed, Texture image) {
		this.name = name;
		this.speed = speed;
		this.image = image;
		
		
	}
}
