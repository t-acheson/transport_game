package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Gem extends Entity {
    private Texture gemImage;
    private Rectangle gem;
    boolean earned;

    public Gem(int locX, int locY) {
        super(locX, locY);
        gem = new Rectangle();
        gem.x = this.getX();
        gem.y = this.getY();
        gem.width = 32;
        gem.height = 32;
        gemImage = new Texture(Gdx.files.internal("gem.png"));
        
    }

    
    public void setImage(String imageName) {
        gemImage = new Texture(Gdx.files.internal(imageName));
    } 


    public Rectangle getGemRectangle() {
        return gem;
    }


    public void render(SpriteBatch batch) {
        batch.draw(gemImage, this.getX(), this.getY());
    }


    public void dispose() {
        gemImage.dispose();
    }
}
