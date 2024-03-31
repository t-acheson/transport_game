package com.hotmomcircle.transport_game.entity;

import com.hotmomcircle.transport_game.GameScreen;

public class Gem extends Entity {
    boolean earned;

    public Gem(GameScreen game, int locX, int locY, int width, int height, String imagePath) {
        super(game, locX, locY, width, height, imagePath);        
    }
}
