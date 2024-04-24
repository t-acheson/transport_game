package com.hotmomcircle.transport_game.tools;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.object.Transport_OBJ;

public class WorldMap {
    

    ShapeRenderer shape;
	ShapeRenderer locationPointer;
	public boolean isDrawLocationPointer;
	private float locPointX;
	private float locPointY;
    double panSpeed = 10000;
    int mapWidthInPixels;
    int mapHeightInPixels;
    OrthographicCamera worldMap;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    SpriteBatch batch;
	private Camera camera;
	private GameScreen game;


    public WorldMap(OrthogonalTiledMapRenderer rend, TiledMap map, SpriteBatch bat, Camera camera, GameScreen game){

		this.game = game;
        batch = bat;
        renderer = rend;
		this.camera = camera;
    
        // for world map
        TiledMapTileLayer worldMapLayer = (TiledMapTileLayer) map.getLayers().get(1);
        int mapWidth = worldMapLayer.getWidth();
        int tileWidth = (int) worldMapLayer.getTileWidth();
        mapWidthInPixels = mapWidth * tileWidth*3;
    
        int mapHeightInTiles = worldMapLayer.getHeight();
        int tileHeight = (int) worldMapLayer.getTileHeight();
        mapHeightInPixels = mapHeightInTiles * tileHeight*3;
    
        
        worldMap = new OrthographicCamera();
        worldMap.setToOrtho(false, mapWidthInPixels, mapHeightInPixels);
    	shape = new ShapeRenderer();
		
		isDrawLocationPointer = false;
		locationPointer = new ShapeRenderer();

    }

    public void render(Player player, Array<Gem> gems, ArrayList<Transport_OBJ> bike_OBJs, ArrayList<Transport_OBJ> car_OBJs){
        
			worldMap.zoom = MathUtils.clamp(worldMap.zoom, 0.1f, 1f);
			if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
				worldMap.zoom -= 0.02; // Zoom in
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
				worldMap.zoom += 0.02; // Zoom out
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				worldMap.translate((float) (-panSpeed * Gdx.graphics.getDeltaTime()), 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				worldMap.translate((float)(panSpeed * Gdx.graphics.getDeltaTime()), 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				worldMap.translate(0, (float)(panSpeed * Gdx.graphics.getDeltaTime()));
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				worldMap.translate(0, (float) (-panSpeed * Gdx.graphics.getDeltaTime()));
			}
			// Calculate the edges of the worldMap view in world coordinates
			float halfCamWidth = worldMap.viewportWidth * worldMap.zoom * 0.5f;
			float halfCamHeight = worldMap.viewportHeight * worldMap.zoom * 0.5f;

			// Calculate the minimum and maximum x and y values the worldMap can have
			float minX = halfCamWidth;
			float maxX = mapWidthInPixels - halfCamWidth;
			float minY = halfCamHeight;
			float maxY = 8000 - halfCamHeight;

			// Clamp the worldMap's position
			worldMap.position.x = Math.max(minX, Math.min(maxX, worldMap.position.x));
			worldMap.position.y = Math.max(minY, Math.min(maxY, worldMap.position.y));
			
			worldMap.update();
			renderer.setView(worldMap);
			batch.setProjectionMatrix(worldMap.combined);
			renderer.render();
			batch.begin();
			try{
				batch.draw(player.image, player.getX()-128,player.getY()-64, 512,512);
				for (Gem gem : gems) {
					batch.draw(gem.image, gem.getX()-256, gem.getY()-64, 750,750);
				}
				for (Transport_OBJ bike: bike_OBJs){
					batch.draw(game.assetManager.get("./objects/bicycle.png", Texture.class), bike.getX()-128, bike.getY()-64, 512,512);
				}
				for (Transport_OBJ car: car_OBJs){
					batch.draw(game.assetManager.get("./objects/car_left.png", Texture.class), car.getX()-128, car.getY()-64, 512,512);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batch.end();

			shape.setProjectionMatrix(worldMap.combined);

    }

	public void setLocationPointer(float x, float y) {
		locPointX = x * camera.viewportWidth / mapWidthInPixels;
		locPointY = y * camera.viewportHeight / mapHeightInPixels;
	}

	public ArrayList<Float> getLocationPointer(float x, float y) {
		locPointX = x * camera.viewportWidth / mapWidthInPixels;
		locPointY = y * camera.viewportHeight / mapHeightInPixels;
		ArrayList<Float> locationPoints = new ArrayList<>();
		locationPoints.add(locPointX);
		locationPoints.add(locPointY);
	
		// Return the list of location points
		return locationPoints;	
	}

	public void toggleLocationPointer() {
		if (isDrawLocationPointer) {
			isDrawLocationPointer = false;
		} else {
			isDrawLocationPointer = true;
		}
	}


}
