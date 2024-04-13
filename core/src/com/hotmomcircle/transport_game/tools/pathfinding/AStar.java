package com.hotmomcircle.transport_game.tools.pathfinding;

import java.util.*;

public class AStar {
    // Method to perform A* pathfinding
    public static ArrayList<Node> findPath(HashMap<Node, ArrayList<Node>> graph, Node start, Node goal) {
        // Initialize open and closed sets
        Queue<Node> openSet = new LinkedList<>();
        HashSet<Node> closedSet = new HashSet<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();

        // Add start node to open set
        openSet.add(start);

        // A* search loop
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Goal check
            if (current.equals(goal)) {
                // Reconstruct path
                ArrayList<Node> path = reconstructPath(cameFrom, current);
                System.out.println("Path found:");
                for (Node node : path) {
                    System.out.println(node.toString());
                }
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            // Expand current node
            for (Node neighbor : graph.get(current)) {
                if (closedSet.contains(neighbor)) {
                    continue; // Skip neighbors already evaluated
                }

                // Check if neighbor is not already in the open set
                if (!openSet.contains(neighbor)) {
                    cameFrom.put(neighbor, current);
                    openSet.add(neighbor);
                }
            }
        }

        // No path found
        return null;
    }

    // Method to reconstruct path from start to goal
    private static ArrayList<Node> reconstructPath(HashMap<Node, Node> cameFrom, Node current) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current); // Add to front of list
        }

        return path;
    }
}


