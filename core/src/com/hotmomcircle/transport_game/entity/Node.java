package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Node class interactable by player
// holds Route[] from which player interacts
// will need its own UI elements to all player
// to choose with WASD, arrow keys, or mouse 
public class Node extends Entity{
    private Texture image;

    private Route[] routes;

    public Node(int locX, int locY, int width, int height) { // Route[] routes
        super(locX, locY, width, height);
        // this.routes = routes;
        image = new Texture(Gdx.files.internal("gem.png"));
    }

    public Route[] getRoutes() {
        return routes;
    }
    
    public void render(SpriteBatch batch) {
        batch.draw(image, this.getX(), this.getY());
    }
}
