package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Object implements Json.Serializable {
	protected int x;
	protected int y;
	protected Texture objImg;
	protected Rectangle interactionRadius;
	protected String popUp;
	boolean interactable = false;
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Rectangle getObjectRectangle() {
		return interactionRadius;
	}
	
//	Serialization function to write ParentGame to JSON
	@Override
	public void write(Json json) {
		json.writeValue("x", x);
		json.writeValue("y", y);
	}

//	Serialization function to read ParentGame from JSON
	@Override
	public void read(Json json, JsonValue jsonData) {
		this.x = jsonData.getInt("x");
		this.y = jsonData.getInt("y");

	}
	
	
}
