package com.hotmomcircle.transport_game.entity;

public class Route extends Entity {
    private int steps[]; // list of objects from Map.tmx that make up the route
    private int destX;
    private int destY;
    
    // could argue destX and destY are
    // redundant because steps should translate
    // x, y to destX, destY
    public Route(int locX, int locY, int width, int height, String imagePath, int destX, int destY) { // int[] steps
        super(locX, locY, width, height, imagePath);
        // this.steps = steps;
        this.destX = destX;
        this.destY = destY;
    }

    // need to pass to GuidedTransport to move player
    public int[] getSteps() {
        return steps;
    }

    @Override
    public String toString () {
        return "X: " + destX + " Y: " + destY; 
    }

    public int getDestX() {
        return destX;
    }

    public int getDestY() {
        return destY;
    }
}
