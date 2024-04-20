package com.hotmomcircle.transport_game.tools.pathfinding;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        // if (this == obj) {
        //     return true;
        // }
        // if (obj == null || getClass() != obj.getClass()) {
        //     return false;
        // }
        // Node other = (Node) obj;
        // return Float.compare(other.region.x, region.x) == 0 && Float.compare(other.region.y, region.y) == 0;
        Node other = (Node) obj;
        return Math.floor(this.getX()) == Math.floor(other.getX()) && Math.floor(this.getY()) == Math.floor(other.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(region.x, region.y);
    }

    public float getX() {
        return this.region.x;
    }

    public float getY() {
        return this.region.y;
    }

    public float getWidth() {
        return this.region.width;
    }
}
