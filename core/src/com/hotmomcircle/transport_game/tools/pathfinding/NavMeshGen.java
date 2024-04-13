package com.hotmomcircle.transport_game.tools.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

public class NavMeshGen {
    // Define a field to store the generated graph
    private HashMap<Node, ArrayList<Node>> graph;
    private TiledMapTileLayer layer;

    // Constructor
    public NavMeshGen() {
        // get every cell in the layer
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("bigMap.tmx");


        for (MapLayer mapLayer : map.getLayers()) {
            // System.out.println(mapLayer.getName());
            if (mapLayer.getName().equals("roadsDrive")) {

                System.out.println(mapLayer.getName());
                // for (MapObject obstacle: layer.getObjects()) {
                //     float x = obstacle.getProperties().get("x", Float.class) * 3;
                //     float y = obstacle.getProperties().get("y", Float.class) * 3;
                //     float width = obstacle.getProperties().get("width", Float.class) * 3;
                //     float height = obstacle.getProperties().get("height", Float.class) * 3;
                //     Obstacle newRoad = new Obstacle(x, y, width, height);
                //     roads.add(newRoad);
                // }
            }
        }


        // for every cell in the layer, create a new node

            // check what nodes are adjacent 
            // add to hashmap
        }

    

    public boolean areNodesConnected(Rectangle rect1, Rectangle rect2) {

        boolean horizontallyAdjacent = (rect1.x + rect1.width == rect2.x || rect2.x + rect2.width == rect1.x) &&
                                        !(rect1.y >= rect2.y + rect2.height || rect2.y >= rect1.y + rect1.height);

        boolean verticallyAdjacent = (rect1.y + rect1.height == rect2.y || rect2.y + rect2.height == rect1.y) &&
                                    !(rect1.x >= rect2.x + rect2.width || rect2.x >= rect1.x + rect1.width);

        return horizontallyAdjacent || verticallyAdjacent;
    }

}


