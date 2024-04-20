package com.hotmomcircle.transport_game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.hotmomcircle.transport_game.GameScreen;

// Node class interactable by player
// holds Route[] from which player interacts
// will need its own UI elements to all player
// to choose with WASD, arrow keys, or mouse 
public class Hub extends Entity{
    private Rectangle rect;
    private ArrayList<Hub> connectedHubs = new ArrayList<Hub>();

    public Hub(float locX, float locY, float width, float height) { 
        this.rect = new Rectangle(locX, locY, width, height);
    }

    public void addHub(Hub newHub) {
        connectedHubs.add(newHub);
    }

    public ArrayList<Hub> getConnected() {
        return connectedHubs;
    }

    @Override
    public String toString() {
        return "X: " + Math.round(this.getX()) + " Y: " + Math.round(this.getY());
    }

    public Rectangle getRect () {
        return this.rect;
    }

    public float getX() {
        return this.rect.x;
    }

    public float getY() {
        return this.rect.y;
    }

    public float getWidth() {
        return this.rect.width;
    }

    public float getHeight() {
        return this.rect.height;
    }
}
