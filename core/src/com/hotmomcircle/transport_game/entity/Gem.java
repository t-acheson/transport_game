package com.hotmomcircle.transport_game.entity;

import com.hotmomcircle.transport_game.GameScreen;

public class Gem extends Entity {
    boolean earned;
    static String imagePath = "gem.png";
    public Gem(GameScreen game, int locX, int locY, int width, int height) {
        super(game, locX, locY, width, height, imagePath);
    }
}
