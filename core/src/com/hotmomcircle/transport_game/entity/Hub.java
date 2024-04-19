package com.hotmomcircle.transport_game.entity;

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
    private Array<Route> routes;

    public Hub(GameScreen game, float locX, float locY, int width, int height, String imagePath, Array<Route> routes) { 
        super(game, locX, locY, width, height, imagePath);
        this.routes = routes;
        image = new Texture(Gdx.files.internal("gem.png"));
    }

    public Array<Route> getRoutes() {
        return routes;
    }
    
    public void render(SpriteBatch batch) {
        batch.draw(image, this.getX(), this.getY());
    }

    @Override
    public String toString() {
        return "Node at " + "X: " + this.getX() + " Y: " + this.getY();
    }
}