package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.object.Bicycle_OBJ;
 
// This will be the screen 
public class GameScreen implements Screen {
	
	TransportGame game;
	
	
	SpriteBatch batch;
	
	private int originalTileSize = 16;
	public int scale = 2;
	private int tileSize = originalTileSize * scale;
	
	
	Texture img;
	public Player player;

	public Array<Gem> gems;
	public Bicycle_OBJ bike;
	   
   private OrthographicCamera camera;

	
	public GameScreen(TransportGame game) {
		this.game = game;
		
		this.batch = game.batch;
		

		
		player = new Player(this);

		gems = new Array<Gem>();
		gems.add(new Gem(100, 100));
		gems.add(new Gem(200, 200));
		gems.add(new Gem(300, 300));
		
		bike = new Bicycle_OBJ(this, 300, 100);
		
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
		
		for (Gem gem : gems) {
			if (player.getPlayerRectangle().overlaps(gem.getGemRectangle())) {
				gem.dispose();
				gems.removeValue(gem, true);
			}
		}
		
	
		
	
      // clear the screen with a dark blue color. The
      // arguments to clear are the red, green
      // blue and alpha component in the range [0,1]
      // of the color to be used to clear the screen.
      ScreenUtils.clear(0, 0, 0.2f, 1);

      // tell the camera to update its matrices.
      camera.update();

      // tell the SpriteBatch to render in the
      // coordinate system specified by the camera.
      batch.setProjectionMatrix(camera.combined);

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		try {
			bike.render(batch);
			
			for (Gem gem : gems) {
				gem.render(batch);
			}
//			Render the player last so they appear on top of everything
			player.render(batch);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public int getTileSize() {
		return tileSize;
	}

}
