package com.hotmomcircle.transport_game.entity;

public class Route extends Entity {
    private int steps[]; // list of objects from Map.tmx that make up the route
    
    // could argue destX and destY are
    // redundant because steps should translate
    // x, y to destX, destY
    Route(int locX, int locY, int width, int height, String imagePath, int destX, int destY, int steps[]) {
        super(locX, locY, width, height, imagePath);
        this.steps = steps;
    }

    // need to pass to GuidedTransport to move player
    public int[] getSteps() {
        return steps;
    }
}
