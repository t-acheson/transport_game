package com.hotmomcircle.transport_game;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class TransportGame extends Game {
	

	public int SCREEN_WIDTH = 1600;
	public int SCREEN_HEIGHT = 960;
	
	SpriteBatch batch;
	
	public Skin skin; 
	public BitmapFont font;
	public String fileName; // File name of the current game being playe
   

	@Override
	public void create () {

		batch = new SpriteBatch();
		
//      Sets the theme of our buttons, this will have to be updated when we decide on our style
		
//		Generate a bitmap from a ttf file
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/game_font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		
		
//		Use default UISKIN for now
		skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));
        
//		Change the font for buttons
        TextButtonStyle style = skin.get(TextButtonStyle.class);
        style.font = font;
        
//        Change the font for labels
        LabelStyle labelStyle = skin.get(LabelStyle.class);
        labelStyle.font = font;
        
        
		this.setScreen(new MainMenuScreen(this)); 
		
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public int getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}

	public int getSCREEN_HEIGHT(){
		return SCREEN_HEIGHT;
	}
	
	public void newGame(String name) {
//		Add time to fileName to make it unique for every save
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentTimeString = currentTime.format(formatter);
        fileName = name + currentTimeString + ".json";
		new ParentGame(this, name, fileName);
	}
	
//	Returns 1 if successful, 0 if not
	public int resumeGame() {
//		If no current game return
		if (fileName == null) {
			return 0;
		}
		
//		TODO rename most recent save to something else like current.json
        FileHandle fileHandle = Gdx.files.local("saves/" + fileName); // w
        String text = fileHandle.readString();
        
        JsonValue json = new JsonReader().parse(text);
		new ParentGame(this, json, fileName);
		return 1;
	}
	
//	Load the game from a given filename
	public void loadGame(String fileName) {
		this.fileName = fileName;
        FileHandle fileHandle = Gdx.files.local("saves/" + fileName);
        String text = fileHandle.readString();
        
        JsonValue json = new JsonReader().parse(text);
		new ParentGame(this, json, fileName);
	}
	
}
