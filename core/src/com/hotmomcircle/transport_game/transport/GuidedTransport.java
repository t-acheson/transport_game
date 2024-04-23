package com.hotmomcircle.transport_game.transport;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.tools.pathfinding.Node;
import com.hotmomcircle.transport_game.tools.pathfinding.NodeFinder;

public class GuidedTransport extends Transport {
    public ArrayList<Node> path;
    public Node current;
    private HashMap<Node, ArrayList<Node>> graph;

    public GuidedTransport(GameScreen game, Player player, String name, int speed, Texture[] images, String footprint, String staminaCost) {
        super(game, player, name, speed, images, footprint, staminaCost);
        this.graph = game.pathfindingGraph.graph;

    }

    public void setPath(ArrayList<Node> path) {
        this.path = path;
        this.current = path.remove(0);
    }

    @Override
    public Texture update() {
        float speed = getSpeed() * Gdx.graphics.getDeltaTime();
        float dx = 0;
        float dy = 0;
    
        Node playerNode = NodeFinder.findNode(this.graph, player.getX(), player.getY());
    
        if (playerNode == this.current) {
            if (!path.isEmpty()) {
                this.current = path.remove(0);
                game.showPopup = true;
            } else {
                player.getOnFoot();
                return getCurrentImage(dx, dy);
            }
        } else {
            // Calculate the distance to the current node
            float deltaX = current.getX() - player.getX();
            float deltaY = current.getY() - player.getY();
            float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    
            // If the distance is less than a threshold, consider the player has reached the node
            if (distance <= 5) {
                // Move the player directly to the node
                this.player.setX(current.getX());
                this.player.setY(current.getY());
                if (!path.isEmpty()) {
                    this.current = path.remove(0);
                }else {
                    player.getOnFoot();
                    return getCurrentImage(dx, dy);
                }

            } 
                // Calculate the normalized movement vector
                float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                if (length > 0) {
                    deltaX /= length;
                    deltaY /= length;
                }
                
                // Scale the vector by the speed
                dx = deltaX * speed;
                dy = deltaY * speed;
            
        }
    
        // finally apply the movement
        this.player.incX(dx);
        this.player.incY(dy);
    
        this.player.rectangle.x = this.player.getX();
        this.player.rectangle.y = this.player.getY();
    
        return getCurrentImage(dx, dy);
    }
    
}
