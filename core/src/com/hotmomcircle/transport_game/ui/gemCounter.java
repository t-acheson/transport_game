package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.hotmomcircle.transport_game.entity.Gem;

public class gemCounter extends Label{

    private Array<Gem> gems;; // Field to store the gem array

    public gemCounter(Array<Gem> gems, Skin skin) {
        super("Gems Left: " + gems.size, skin); // Initialize with the initial count
        this.gems = gems; // Store the gem array
    }


    public void update(){
        setText("Gems Left: " + gems.size);
    }

}
