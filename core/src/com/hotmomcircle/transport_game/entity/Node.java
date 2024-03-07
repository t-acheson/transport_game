package com.hotmomcircle.transport_game.entity;

// Node class interactable by player
// holds Route[] from which player interacts
// will need its own UI elements to all player
// to choose with WASD, arrow keys, or mouse 
public class Node extends Entity{

    private Route[] routes;

    Node(int x, int y, Route[] routes) {
        super(x ,y);
        this.routes = routes;
    }
    
}
