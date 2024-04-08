package com.hotmomcircle.transport_game.entity;

import com.badlogic.gdx.math.Rectangle;

public class Obstacle extends Entity{
    
    public Obstacle (float x, float y, float w, float h) {
        this.x = x;
        this.y = y;

        this.rectangle = new Rectangle(x, y, w, h);
    }
}
