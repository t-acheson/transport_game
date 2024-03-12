package com.hotmomcircle.transport_game.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.hotmomcircle.transport_game.TransportGame;

public class Camera extends OrthographicCamera {
    
    public Camera(TransportGame game) {
        super();
        this.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
    }
}
