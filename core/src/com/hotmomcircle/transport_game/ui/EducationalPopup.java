package com.hotmomcircle.transport_game.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.entity.Player;

public class EducationalPopup {
    private boolean bike = false;
    private boolean bus = false;
    private boolean car = false;
    private boolean walking = false;
    private boolean luas = false;
    private boolean gem = false;
    private Player player;
    private TransportGame game;
    private Stage stage;
    private Skin skin;
    private Table uiTable;
    private boolean uiShow = false;

    public EducationalPopup(TransportGame game, GameScreen screen, Stage stage, Skin skin, Player player) {
        // constructor just needs to know what it can work with
        this.game = game;
        this.stage = stage;
        this.skin = skin;
        this.player = player;
    }

    public void showUI () {
        if (uiShow == false){
            uiShow = true;
            String outString = getMessage();

            uiTable = new Table();
            uiTable.setFillParent(true);
            uiTable.defaults().width(this.game.SCREEN_WIDTH).expandX().fillX();
            uiTable.setWidth(game.SCREEN_WIDTH);
            uiTable.row().pad(10, 0, 10, 0);
            uiTable.bottom();

            Label panLabel = new Label(outString, skin);
            panLabel.setWrap(true);

            uiTable.add(panLabel);


            stage.addActor(uiTable);

            uiTable.addAction(Actions.sequence(
                Actions.delay(7), // Delay for 7 seconds
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        uiShow = false;
                        uiTable.remove(); // Remove the table from the stage
                    }
                })
            ));
            System.out.println(player.currTransport().name);
	}}


    public String getMessage(){
        FileHandle path = Gdx.files.internal("");
        int line = -1;
        ArrayList<String> lines = new ArrayList<>();
        Random random = new Random();

        switch(player.currTransport().name) {
            case "Foot":
                path = Gdx.files.internal("environmentFacts/generic.txt");
                if (!walking){
                    line = 1;
                    walking = true;
                } else {
                    return "Remember to be careful of your Freshness!";
                }
                break;
            case "Bicycle": 
                path =Gdx.files.internal("environmentFacts/bike.txt");
                if (!bike){
                    line = 0;
                    bike = true;
                } else {
                    line = random.nextInt(20);
                }
                break;
            case "Car":
                path = Gdx.files.internal("environmentFacts/car.txt");
                if (!car){
                    line = 0;
                    car = true;
                } else {
                    line = random.nextInt(20);
                }
                break;
            case "Luas":
                path = Gdx.files.internal("environmentFacts/luas.txt");
                if (!luas){
                    line = 0;
                    luas = true;
                } else {
                    line = random.nextInt(20);
                }
                break;
            case "Bus":
                path = Gdx.files.internal("environmentFacts/bus.txt");
                if (!bus){
                    line = 0;
                    bus = true;
                } else {
                    line = random.nextInt(20);
                }
                break;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(path.read()))) {
                String lineString;
                while ((lineString = reader.readLine()) != null) {
                    lines.add(lineString); 
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
            return lines.get(line);


    }
    


}
