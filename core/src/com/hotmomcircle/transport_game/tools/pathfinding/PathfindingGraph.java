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

        this.graph = new HashMap<Node, ArrayList<Node>>();

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    // for every cell in the layer, create a new node
                    Rectangle region = new Rectangle(x * regionWidth, y * regionHeight, regionWidth, regionHeight);
                    Node node = new Node(region);
                    graph.put(node, new ArrayList<>());

                    if (layer.getCell(x, y + 1) != null) {
                        Rectangle neighborRegion = new Rectangle(x * regionWidth, (y + 1) * regionHeight, regionWidth,
                                regionHeight);
                        Node neighbor = new Node(neighborRegion);
                        graph.get(node).add(neighbor);
                    }

                    if (layer.getCell(x, y - 1) != null) {
                        Rectangle neighborRegion = new Rectangle(x * regionWidth, (y - 1) * regionHeight, regionWidth,
                                regionHeight);
                        Node neighbor = new Node(neighborRegion);
                        graph.get(node).add(neighbor);
                    }

                    if (layer.getCell(x + 1, y) != null) {
                        Rectangle neighborRegion = new Rectangle((x + 1) * regionWidth, y * regionHeight, regionWidth,
                                regionHeight);
                        Node neighbor = new Node(neighborRegion);
                        graph.get(node).add(neighbor);
                    }

                    if (layer.getCell(x - 1, y) != null) {
                        Rectangle neighborRegion = new Rectangle((x - 1) * regionWidth, y * regionHeight, regionWidth,
                                regionHeight);
                        Node neighbor = new Node(neighborRegion);
                        graph.get(node).add(neighbor);
                    }

                }
            }
        }
    }
}
