package com.hotmomcircle.transport_game.tools.pathfinding;

import com.badlogic.gdx.math.Rectangle;

public class Node {
    Rectangle region;

    public Node(Rectangle region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return (
            region.getX() + " " + region.getY() + " " + 
            region.getWidth() + " " + region.getHeight()
        );
    }
}
