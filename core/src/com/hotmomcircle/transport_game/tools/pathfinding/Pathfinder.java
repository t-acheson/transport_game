package com.hotmomcircle.transport_game.tools.pathfinding;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Pathfinder {

    public Pathfinder () {
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("bigMap.tmx");
    
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("yourLayerName");
    }
// Now you have access to the layer data and can proceed with pathfinding implementation.

}
