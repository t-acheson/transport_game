package com.hotmomcircle.transport_game.entity;
// This will hold the entity superclass
//player will inherit from this
// can we abstract the Rectangles from 
// Player and Gem etc. to here?
// Entity is anything a player might 
// interact with so likely useful to standardise

public class Entity {
	protected int x;
	protected int y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	Entity(int locX, int locY){
		this.x = locX;
		this.y = locY;
	}

	Entity(){
		this.x = 0;
		this.y = 0;
	}	

	// similar to abstracting player rectangles
	// argument to made for abstracting render?
	// overloaded by player for sure but the 
	// other entities would likely have static images
	// and at worst a little blip to animate them
}
