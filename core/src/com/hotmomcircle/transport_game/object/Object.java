package com.hotmomcircle.transport_game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Object {
	protected int x;
	protected int y;
	protected Texture objImg;
	protected Rectangle interactionRadius;
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
	
	
}
