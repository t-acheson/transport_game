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

        if (NodeFinder.findNode(this.graph, player.getX(), player.getY()) == current) {
            if (!path.isEmpty()) {
                current = path.remove(0);
            } else {
                player.getOnFoot();
                return getCurrentImage(dx, dy);
            }
        } 
        else {
            if (player.getX() > current.getX()) {
                // player must move left
                dx -= speed;
            }

            if (player.getX() < current.getX()) {
                // player must move right
                dx += speed;
            }

            if (player.getY() < current.getY()) {
                // player must move up
                dy += speed;
            }

            if (player.getY() > current.getY()) {
                // player must move down
                dy -= speed;
            }
        }

        // finally apply the movement
		this.player.incX(dx);
		this.player.incY(dy);

		this.player.rectangle.x = this.player.getX();
		this.player.rectangle.y = this.player.getY();

        return getCurrentImage(dx, dy);
    }
    
}
