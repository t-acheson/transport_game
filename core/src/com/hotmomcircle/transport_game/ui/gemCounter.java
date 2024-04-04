package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class gemCounter extends Label{

    private int[] gems; // Field to store the gem array

    public gemCounter(int[] gems, Skin skin) {
        super("Gems Left: " + gems.length, skin); // Initialize with the initial count
        this.gems = gems; // Store the gem array
    }


    public void update(){
        setText("Gems Left: " + gems.length);
    }

}
