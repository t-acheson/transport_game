package com.hotmomcircle.transport_game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.hotmomcircle.transport_game.GameScreen;

// Node class interactable by player
// holds Route[] from which player interacts
// will need its own UI elements to all player
// to choose with WASD, arrow keys, or mouse 
public class Hub extends Entity{
    private Texture image;
    private ArrayList<Hub> connectedHubs = new ArrayList<Hub>();

    public Hub(GameScreen game, float locX, float locY, float width, float height, String imagePath) { 
        super(game, locX, locY, width, height, imagePath);
        image = new Texture(Gdx.files.internal("gem.png"));
    }
    
    public void render(SpriteBatch batch) {
        batch.draw(image, this.getX(), this.getY());
    }

    public void addHub(Hub newHub) {
        connectedHubs.add(newHub);
    }

    public ArrayList<Hub> getConnected() {
        return connectedHubs;
    }

    @Override
    public String toString() {
        return "Hub at " + "X: " + this.getX() + " Y: " + this.getY();
    }
}
