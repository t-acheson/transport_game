package com.hotmomcircle.transport_game.ui;
// this package will hold all of the ui elements
// MVP: Carbon, freshness and a timer of some description

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Carbon extends Group {
    private ProgressBar carbon;

    public Carbon(Skin skin) {
        // Create health bar
        carbon = new ProgressBar(0, 100, 1, false, skin);
        carbon.setSize(200, 20);
        carbon.setAnimateDuration(0.25f);
        carbon.getStyle().knob.setMinHeight(carbon.getHeight());
        carbon.getStyle().knob.setMinWidth(0);
        carbon.getStyle().knobBefore.setMinHeight(carbon.getHeight());
        carbon.getStyle().background.setMinHeight(carbon.getHeight());
        addActor(carbon);
    }

    public void setCarbon(float value) {
        carbon.setValue(value);
    }

    public float getCarbon() {
        return carbon.getValue();
    }
}
