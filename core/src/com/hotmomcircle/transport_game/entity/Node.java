package com.hotmomcircle.transport_game.entity;

// Node class interactable by player
// holds Route[] from which player interacts
// will need its own UI elements to all player
// to choose with WASD, arrow keys, or mouse 
public class Node extends Entity{

    private Route[] routes;

    Node(int locX, int locY, int width, int height, Route[] routes) {
        super(locX, locY, width, height);
        this.routes = routes;
    }

    public Route[] getRoutes() {
        return routes;
    }
    
}
