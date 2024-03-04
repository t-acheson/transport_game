package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
//map imports below 
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


 
// This will be the screen 
public class GameScreen implements Screen {
	
	TransportGame game;
	
	
	SpriteBatch batch;
	
	private int originalTileSize = 16;
	public int scale = 2;
	private int tileSize = originalTileSize * scale;
	
	//initalising map 
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	Texture img;
	public Player player;
	
	public Array<Gem> gems;
	
    private OrthographicCamera camera;
	
	private int GAME_STATE;
	
	private final int GAME_RUNNING = 0;
	private final int GAME_PAUSED = 1;
	private BitmapFont font = new BitmapFont();
	
	
	public GameScreen(TransportGame game) {
		this.game = game;
		
		this.batch = game.batch;
		
		// for the pause / play feature
		GAME_STATE = GAME_RUNNING;
		
		//loading map 
		TmxMapLoader loader = new TmxMapLoader();
		try {
			map = loader.load("trialMap.tmx");
			System.out.println("Map loaded successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderer = new OrthogonalTiledMapRenderer(map);	
		//
		
		
		player = new Player(this);
		
		gems = new Array<Gem>();
		gems.add(new Gem(100, 100));
		gems.add(new Gem(200, 200));
		gems.add(new Gem(300, 300));
		
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
		
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {


		if(Gdx.input.isKeyPressed(Input.Keys.P) && GAME_STATE != GAME_PAUSED) {
			pause();
		} 
		if(Gdx.input.isKeyPressed(Input.Keys.R) && GAME_STATE != GAME_RUNNING) {
			resume();
		} 
		if (GAME_STATE == GAME_PAUSED){
			// draw the pause screen
			// Clear the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			batch.begin();
			font.draw(batch, "Game Paused", game.getSCREEN_WIDTH() / 2 - 60, game.getSCREEN_HEIGHT() / 2 + 50);
			font.draw(batch, "Press 'R' to Resume", game.getSCREEN_WIDTH() / 2 - 90, game.getSCREEN_HEIGHT() / 2);
			batch.end();

		} else {

		
			ScreenUtils.clear(0, 0, 0.2f, 1);
			
			// map render 
			renderer.setView(camera);
			renderer.render();
			//

			for (Gem gem : gems) {
				if (player.getPlayerRectangle().overlaps(gem.getGemRectangle())) {
					gem.dispose();
					gems.removeValue(gem, true);
				}
			}

			// TODO Auto-generated method stub
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

			// ScreenUtils.clear(1, 0, 0, 1); 
			batch.begin();
			try {
				player.render(batch);
				for (Gem gem : gems) {
					gem.render(batch);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batch.end();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		System.out.println("Game Paused");
		GAME_STATE = GAME_PAUSED;
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		GAME_STATE = GAME_RUNNING;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		// TODO check if right place for this disposal 
		map.dispose();
		renderer.dispose();
		
	}
	
	public int getTileSize() {
		return tileSize;
	}

}
