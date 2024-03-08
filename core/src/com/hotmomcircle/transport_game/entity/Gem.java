package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gem extends Entity {
    private Texture gemImage;
    boolean earned;

    public Gem(int locX, int locY, int width, int height) {
        super(locX, locY, width, height);
        gemImage = new Texture(Gdx.files.internal("gem.png"));
        
    }

    public void setImage(String imageName) {
        gemImage = new Texture(Gdx.files.internal(imageName));
    } 

    public void render(SpriteBatch batch) {
        batch.draw(gemImage, this.getX(), this.getY());
    }


    public void dispose() {
        gemImage.dispose();
    }
}
