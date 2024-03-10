package com.hotmomcircle.transport_game.entity;
// This will hold the entity superclass
//player will inherit from this
// can we abstract the Rectangles from 
// Player and Gem etc. to here?
// Entity is anything a player might 
// interact with so likely useful to standardise
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	protected float x;
	protected float y;
	public Rectangle rectangle;
	public String imagePath;
	private Texture image;
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	Entity(float locX, float locY, int width, int height, String imagePath){
		this.x = locX;
		this.y = locY;

		this.image = new Texture(Gdx.files.internal(imagePath));

		this.rectangle = new Rectangle();
		this.rectangle.x = locX;
		this.rectangle.y = locY;
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

	public void render(SpriteBatch batch) throws Exception {
		this.rectangle.x = this.getX();
		this.rectangle.y = this.getY();
		batch.draw(this.image, this.getX(), this.getY());
	}

	public void dispose() {
        image.dispose();
    }

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
