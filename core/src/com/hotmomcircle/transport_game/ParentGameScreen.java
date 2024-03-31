package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

// Holds an instance of the game
// Decides which level the player is playing and loads in the logic 
// Will also load in screens for prologue, level start, end, etc.
// Allows for multiple levels to be played.
public class ParentGameScreen implements Json.Serializable{
	
	TransportGame game;
	AssetManager assetManager;
	
	GameScreen gameScreen;
	
	int currLevel;
	int maxLevel;
	
	String name = "test";
	
//	Constructor for new game
	public ParentGameScreen(TransportGame game) {
		this.game = game;
		init();
		
		currLevel = 0;
		maxLevel = 0;
		gameScreen = new GameScreen(game, this);
		game.setScreen(gameScreen);
		
	}
	
//	Constructor for loading game
	public ParentGameScreen(TransportGame game, JsonValue jsonData) {
		this.game = game;
		init();
		read(null, jsonData);
		
	}
	
	public void init() {
		this.assetManager = new AssetManager();
		
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
		
	}
	
	public void saveGame() {
		
		Json json = new Json();
		String text = json.toJson(this);
		
        FileHandle fileHandle = Gdx.files.local("saves/output.json"); // Adjust the file path as needed
        fileHandle.writeString(json.toJson(this), false);
//        text = fileHandle.readString();
        
//        JsonValue root = new JsonReader().parse(text);
		
	}

	@Override
	public void write(Json json) {
		json.writeValue("name", name);
		json.writeValue("currLevel", currLevel);
		json.writeValue("maxLevel", maxLevel);
		json.writeValue("currGame", gameScreen);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		currLevel = jsonData.getInt("currLevel");
		maxLevel = jsonData.getInt("maxLevel");
		
		gameScreen = new GameScreen(game, this, jsonData.get("currGame"));
		game.setScreen(gameScreen);
	}
	
	
}
