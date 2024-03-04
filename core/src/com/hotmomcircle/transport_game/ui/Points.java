package com.hotmomcircle.transport_game.ui;
// this package will hold all of the ui elements
// MVP: points, freshness and a timer of some description

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

// Group is redundant right now buy may not be when we add more components
// i'm thinking about a minimap etc
// so when we extend group all the components
// SHOULD play nicely 

public class Points extends Group {
    private Label points;

    public Points(Skin skin) {
        // Create health bar
        points = new Label("100", skin);
        addActor(points);
    }
}
