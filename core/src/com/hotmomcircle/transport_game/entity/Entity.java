package com.hotmomcircle.transport_game.entity;
// This will hold the entity superclass
//player will inherit from this
// can we abstract the Rectangles from 
// Player and Gem etc. to here?
// Entity is anything a player might 
// interact with so likely useful to standardise
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	protected int x;
	protected int y;
	public Rectangle rectangle;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	Entity(int locX, int locY, int width, int height){
		this.x = locX;
		this.y = locY;

		this.rectangle = new Rectangle();
		this.rectangle.x = this.getX();
		this.rectangle.y = this.getY();
		// can somebody explain to me if i need to be thisDOTTING these
		rectangle.width = width;
		rectangle.height = height; 
	}

	Entity(){
		this.x = 0;
		this.y = 0;
	}	

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void render() {
		this.rectangle.x = this.getX();
		this.rectangle.y = this.getY();
	}
	
}
