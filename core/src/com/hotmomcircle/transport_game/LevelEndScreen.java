package com.hotmomcircle.transport_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelEndScreen implements Screen {
    final TransportGame game;
    
    OrthographicCamera camera;
    Skin skin;
    BitmapFont font;
    
    Stage stage;
    Table table;
    Label levelCompletedLabel;
    Label scoreLabel;
    TextButton continueButton;
    TextButton quitButton;

    public LevelEndScreen(final TransportGame game) {
        this.game = game;
        this.skin = game.skin;
        this.font = game.font;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        makeLevelEndTable();

        stage.addActor(table);
    }
    
    public void makeLevelEndTable() {
        table = new Table();
        table.setFillParent(true);
        // table.setDebug(true); // Turn on all debug lines (table, cell, and widget)

        levelCompletedLabel = new Label("Level Completed", skin);
        levelCompletedLabel.setAlignment(Align.center);

        scoreLabel = new Label("Current Score: 5000", skin);
        scoreLabel.setAlignment(Align.center);

        continueButton = new TextButton("Continue", skin);
        quitButton = new TextButton("Quit", skin);

        table.add(levelCompletedLabel).colspan(2).padBottom(10);
        table.row(); // Move to next row
        table.add(scoreLabel).colspan(2).padBottom(20);
        table.row(); // Move to next row
        table.add(continueButton).width(200).padRight(5);
        table.add(quitButton).width(200).padLeft(5);

        // Add listeners to buttons as needed
        // e.g., continueButton.addListener(new ChangeListener() { ... });
        continueButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
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

    // Implement other required methods: resize, pause, resume, hide, dispose, etc.
}
