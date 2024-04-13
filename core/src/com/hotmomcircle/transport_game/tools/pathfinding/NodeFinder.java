package com.hotmomcircle.transport_game.tools.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeFinder {
    public static Node findNode(HashMap<Node, ArrayList<Node>> graph, float x, float y) {
        for (Node node : graph.keySet()) {
            if (node.region.contains(x, y)) {
                return node;
            }
        }
        return null;
    }
}
