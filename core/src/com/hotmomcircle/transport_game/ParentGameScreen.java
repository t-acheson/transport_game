package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

// Holds an instance of the game
// Decides which level the player is playing and loads in the logic 
// Will also load in screens for prologue, level start, end, etc.
// Allows for multiple levels to be played.
public class ParentGameScreen implements Json.Serializable{
	
	TransportGame game;
	AssetManager assetManager;
	
	int currLevel;
	int maxLevel;
	
//	Constructor for new game
	public ParentGameScreen(TransportGame game) {
		this.game = game;
		this.assetManager = new AssetManager();
		currLevel = 0;
		maxLevel = 0;
		saveGame();
		
		//loading map 
		assetManager.setLoader(TiledMap.class,  new TmxMapLoader());
		assetManager.load("trialMapwithObjects.tmx", TiledMap.class);
		
//		Load in the player transport
		String[] transportPaths = {
			    "./foot/player_up1.png", "./foot/player_up2.png",
			    "./foot/player_down1.png", "./foot/player_down2.png",
			    "./foot/player_left1.png", "./foot/player_left2.png",
			    "./foot/player_right1.png", "./foot/player_right2.png",
			    "./bicycle/bike_up1.png", "./bicycle/bike_up2.png",
			    "./bicycle/bike_down1.png", "./bicycle/bike_down2.png",
			    "./bicycle/bike_left1.png", "./bicycle/bike_left2.png",
			    "./bicycle/bike_right1.png", "./bicycle/bike_right2.png",
			    "./car/car_up.png", "./car/car_up.png",
			    "./car/car_down.png", "./car/car_down.png",
			    "./car/car_left.png", "./car/car_left.png",
			    "./car/car_right.png", "./car/car_right.png"
			};
		
		for(String path: transportPaths) {
			assetManager.load(path, Texture.class);
			
		}
		
//		Load in the objects (gem, bike_OBJ, car_OBJ
		String[] objectPaths = {
				"gem.png",
				"./objects/bicycle.png",
				"./objects/car_left.png"
		};
		
		for(String path: objectPaths) {
			assetManager.load(path, Texture.class);
			
		}
		
		System.out.println("Here");
		
		game.setScreen(new GameScreen(game, this));
		
	}
	
	public void saveGame() {
		
		Json json = new Json();
		String text = json.prettyPrint(this);
		System.out.println(text);
		
        FileHandle fileHandle = Gdx.files.local("saves/output.json"); // Adjust the file path as needed
        fileHandle.writeString(json.prettyPrint(this), false);
		
	}

	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		json.writeValue("currlevel", currLevel);
		json.writeValue("maxLevel", maxLevel);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		
	}
	
	
}
