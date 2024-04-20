package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.hotmomcircle.transport_game.entity.Gem;

public class gemCounter extends Label {
    public Array<Gem>  gems;
    
    public gemCounter(Array<Gem> gems, Skin skin) {
        super(Integer.toString(gems.size), skin);
        this.gems = gems;
    }

    public void update() {        
        super.setText(Integer.toString(this.gems.size));
    }
}
