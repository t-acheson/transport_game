package com.hotmomcircle.transport_game;

import java.util.ArrayList;

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
// This will also be used for things like level selection and end of level screens.
public class ParentGame implements Json.Serializable{
	
	TransportGame game;
	AssetManager assetManager;
	
	GameScreen gameScreen;
	
	int currLevel;
	int maxLevel;
	JsonValue levelData;
	ArrayList<JsonValue> levels = new ArrayList<>();
	
	String name;
	String fileName;

	String totalScore = "0";

	
//	Constructor for new game
	public ParentGame(TransportGame game, String name, String fileName) {
		this.game = game;
		this.name = name;
		this.fileName = fileName;
		init();
		loadLevels();
		currLevel = 0;
		maxLevel = 0;
		startLevel();

	}
	
//	Constructor for loading game
	public ParentGame(TransportGame game, JsonValue jsonData, String fileName) {
		this.game = game;
		this.fileName = fileName;
		init();
		loadLevels();
		read(null, jsonData);
		
	}
	
//	Initialization
//	Separated into a different function to allow reuse between new and load game.
	public void init() {
		this.assetManager = new AssetManager();
		
		//loading map 
		assetManager.setLoader(TiledMap.class,  new TmxMapLoader());
		assetManager.load("finalDraft.tmx", TiledMap.class);
		
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

//	Loads levels from the levels.json file
	public void loadLevels() {
        FileHandle fileHandle = Gdx.files.internal("levels/levels.json"); // w
        String text = fileHandle.readString();
        
        levelData = new JsonReader().parse(text).get("levels");
        
        for (JsonValue level = levelData.child; level != null; level = level.next){
 
                levels.add(level);
        }
	}
	
//	Starts the level given
	public void startLevel() {
		gameScreen = new GameScreen(game, this, levelData.get(currLevel), totalScore);
		game.setScreen(gameScreen);
	}
	
	public void levelUp(String totalScore) {
		currLevel += 1;
		if (currLevel > maxLevel) {
			maxLevel += 1;
		}

		this.totalScore = totalScore;

		startLevel();
	}
	
	public int getCurrLevel() {
		return currLevel + 1; // + 1 as currLevel is 0 index and we want to display this.
	}
	
//	Save the game
	public void saveGame() {
		
		Json json = new Json();
		String text = json.toJson(this);
		
        FileHandle fileHandle = Gdx.files.local("saves/" + fileName); // Adjust the file path as needed
        fileHandle.writeString(json.toJson(this), false);
//        text = fileHandle.readString();
        
//        JsonValue root = new JsonReader().parse(text); 
		
	}

//	Serialization function to write ParentGame to JSON
	@Override
	public void write(Json json) {
		json.writeValue("name", name);
		json.writeValue("currLevel", currLevel);
		json.writeValue("maxLevel", maxLevel);
		json.writeValue("currGame", gameScreen);
		json.writeValue("score", totalScore);
	}

//	Serialization function to read ParentGame from JSON
	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		currLevel = jsonData.getInt("currLevel");
		maxLevel = jsonData.getInt("maxLevel");
		totalScore = jsonData.getString("score");
		
		gameScreen = new GameScreen(game, this, levelData.get(currLevel), jsonData.get("currGame"), totalScore);
		game.setScreen(gameScreen);
	}
	
	
}
