package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Gem extends Entity {
    private Texture gemImage;

    public Gem(int x, int y) {
        super(x, y);
        Rectangle gem = new Rectangle();
        gem.x = this.getX();
        gem.y = this.getY();
        gem.width = 32;
        gem.height = 32;
        gemImage = new Texture(Gdx.files.internal("gem.png"));
    }


    public void render(SpriteBatch batch) {
        batch.draw(gemImage, this.getX(), this.getY());
    }

    public static void main(String[] args) {
        Gem gem = new Gem(150, 150);
        System.out.println(gem.getX());
        System.out.println(gem.getY());
    }
}
