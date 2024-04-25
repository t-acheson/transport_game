package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Points extends Label {
    
    public Points(CharSequence text, Skin skin) {
        super(text, skin);
    }

    @Override
    public void setText(CharSequence deltaValue) {
        // pass value to change value by (positive or negative)

        // parse original label value as integer
        int original = Integer.parseInt(this.getText().toString());

        // parse deltaValue as integer
        int change = Integer.parseInt(deltaValue.toString());

        // calculate new value
        int newValue = original + change;
        
        // call super setText method to update label
        super.setText(Integer.toString(newValue));
    }
}
