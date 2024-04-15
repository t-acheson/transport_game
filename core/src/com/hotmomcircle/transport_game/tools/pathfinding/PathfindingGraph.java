package com.hotmomcircle.transport_game.tools.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class PathfindingGraph {
    // Define a field to store the generated graph
    public HashMap<Node, ArrayList<Node>> graph;
    private MapLayer mapLayer;
    private TiledMapTileLayer layer;

    // Constructor
    public PathfindingGraph(TiledMap map, int originalTileSize) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("road");
        float regionWidth = originalTileSize * 3; // Example width of each region
        float regionHeight = originalTileSize * 3; // Example height of each region
    
        this.graph = new HashMap<>();
    
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle region = new Rectangle(x * regionWidth, y * regionHeight, regionWidth, regionHeight);
                    Node node = new Node(region);
                    graph.put(node, new ArrayList<>());
    
                    // Check and add neighbors
                    addNeighborIfExists(x, y + 1, regionWidth, regionHeight, layer, node);
                    addNeighborIfExists(x, y - 1, regionWidth, regionHeight, layer, node);
                    addNeighborIfExists(x + 1, y, regionWidth, regionHeight, layer, node);
                    addNeighborIfExists(x - 1, y, regionWidth, regionHeight, layer, node);
                }
            }
        }
    }
    
    private void addNeighborIfExists(int x, int y, float regionWidth, float regionHeight, TiledMapTileLayer layer, Node node) {
        if (x >= 0 && x < layer.getWidth() && y >= 0 && y < layer.getHeight()) {
            TiledMapTileLayer.Cell cell = layer.getCell(x, y);
            if (cell != null) {
                Rectangle neighborRegion = new Rectangle(x * regionWidth, y * regionHeight, regionWidth, regionHeight);
                Node neighbor = new Node(neighborRegion);
                if (!graph.get(node).contains(neighbor)) { // Avoid adding duplicate neighbors
                    graph.get(node).add(neighbor);
                }
            }
        }
    }
    
}