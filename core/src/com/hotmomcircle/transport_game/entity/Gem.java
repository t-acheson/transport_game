package com.hotmomcircle.transport_game.entity;

import com.hotmomcircle.transport_game.GameScreen;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Gem extends Entity implements Json.Serializable{
    boolean earned;
    static String imagePath = "gem.png";
    public Gem(GameScreen game, int locX, int locY, int width, int height) {
        super(game, locX, locY, width, height, imagePath);
    }
    
//	Serialization function to write ParentGame to JSON
	@Override
	public void write(Json json) {
		json.writeValue("x", x);
		json.writeValue("y", y);
	}

//	Serialization function to read ParentGame from JSON
	@Override
	public void read(Json json, JsonValue jsonData) {
		this.x = jsonData.getInt("x");
		this.y = jsonData.getInt("y");

	}
	
}
