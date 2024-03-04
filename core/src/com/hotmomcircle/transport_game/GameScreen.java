package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.ui.Carbon;

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

   // Stage for UI components 
   private Stage stage;
   private Carbon carbon;

   // asset manager to implement uiskin.json
   // TODO best practise to implement all our assets this way? 
   private AssetManager assetManager;

	public GameScreen(TransportGame game) {
		this.game = game;
		
		this.batch = game.batch;
		
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

		// Asset manager instanitation
		assetManager = new AssetManager();
        assetManager.load("uiskin.json", Skin.class);

		// UI component boilerplate
		// TODO explain stages and skins
		Stage stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		// instantiante Carbon, set position, add to Stage
        carbon = new Carbon(skin);
        carbon.setPosition(50, 50);
        stage.addActor(carbon);
		
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

		// UI draw 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

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
		
		// TODO check if right place for this disposal 
		map.dispose();
		renderer.dispose();
		stage.dispose();
		assetManager.dispose();
		
	}
	
	public int getTileSize() {
		return tileSize;
	}

}
