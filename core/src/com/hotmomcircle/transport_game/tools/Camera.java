package com.hotmomcircle.transport_game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.transport.Transport;

public class Camera extends OrthographicCamera {
    private Player player;
    private TransportGame game;

    // easiest thing to do was extend the Camera
    public Camera(TransportGame game, Player player) {
        super();
        this.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
        this.game = game;
        this.player = player;
//      Center the camera around the player on initialization, needed for loadgame as player can end up anywhere on map
        this.position.x = player.getX();
        this.position.y = player.getY();
    }

    public void setPosition() {

        // the deltaTime is the time since the last frame
        // was rendered: multiplying this by the lag
        // helps keep the camera movement smooth
        float deltaTime = Gdx.graphics.getDeltaTime();

        // define the region of the screen (the centre)
        // that the camera won't move if the player moves within it
        float deadZoneWidth = this.viewportWidth * 0.15f; // Adjust as needed
        float deadZoneHeight = this.viewportHeight * 0.15f; // Adjust as needed

        // we need to know what transport the player
        // is currently using to adjust for the speed
        Transport[] playerTranport = this.player.getTransport();
        int currentPlayerTransport = this.player.getTransIdx();

        // define lag factor base on speed
        float lagFactor = (playerTranport[currentPlayerTransport].speed / 200); // Adjust as needed

        float targetX = player.getX();
        float targetY = player.getY();

        float dx = targetX - this.position.x;
        float dy = targetY - this.position.y;

        // scaling the dx, and dy by the lag and the delta
        // to keep things smooth
        float scaledDx = dx  * lagFactor * deltaTime;
        float scaledDy = dy  * lagFactor * deltaTime;

        // this stops the camera updating for every
        // tiny movement outside the deadzone
        // because it was jittering at the 
        // extremes without it
        float minMovementThreshold = 0.1f; // Adjust as needed
        
        // check if the player's movement exceeds the minimum threshold
        boolean exceedsThresholdX = Math.abs(scaledDx) > minMovementThreshold;
        boolean exceedsThresholdY = Math.abs(scaledDy) > minMovementThreshold;
        
        // check if the player is outside the dead zone
        boolean outsideDeadZoneX = Math.abs(dx) > deadZoneWidth / 2;
        boolean outsideDeadZoneY = Math.abs(dy) > deadZoneHeight / 2;
        
        // apply the scaled position values only if they 
        // are outside the deadZone and the
        // threshhold is exceeded
        if ((exceedsThresholdX || exceedsThresholdY) && (outsideDeadZoneX || outsideDeadZoneY)) {
            // Apply lag effect
            this.position.x += scaledDx;
            this.position.y += scaledDy;
        }

    }

    public void zoomOut() {
        this.viewportHeight = this.game.getSCREEN_HEIGHT() * 2;
        this.viewportWidth = this.game.getSCREEN_WIDTH() * 2;
    }

    public void zoomIn() {
        this.viewportHeight = this.game.getSCREEN_HEIGHT();
        this.viewportWidth = this.game.getSCREEN_WIDTH();
    }
}
