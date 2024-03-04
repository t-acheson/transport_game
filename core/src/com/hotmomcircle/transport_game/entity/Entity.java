package com.hotmomcircle.transport_game.entity;
// This will hold the entity superclass
//player will inherit from this


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
	
}
