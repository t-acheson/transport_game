package com.hotmomcircle.transport_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;

public class Game extends ApplicationAdapter {
	
	private int originalTileSize = 16;
	public int scale = 3;
	private int tileSize = originalTileSize * scale;
	
	SpriteBatch batch;
	Texture img;
	public Player player;

	public Array<Gem> gems;
	   
   	private OrthographicCamera camera;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		player = new Player(this);

		gems = new Array<Gem>();
		gems.add(new Gem(100, 100));
		gems.add(new Gem(200, 200));
		gems.add(new Gem(300, 300));
		
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
	}

	@Override
	public void render () {
		// // checking if the Player hits a gem
		for (Gem gem : gems) {
			if (player.getPlayerRectangle().overlaps(gem.getGemRectangle())) {
				gem.setImage("empty.png");
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
//		batch.draw(img, 0, 0);
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
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
}
