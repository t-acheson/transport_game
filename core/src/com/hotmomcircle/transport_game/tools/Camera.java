package com.hotmomcircle.transport_game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.hotmomcircle.transport_game.TransportGame;
import com.hotmomcircle.transport_game.entity.Player;
import com.hotmomcircle.transport_game.transport.Transport;

public class Camera extends OrthographicCamera {
    private Player player;
    
    public Camera(TransportGame game, Player player) {
        super();
        this.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
        this.player = player;
    }

    public void setPosition() {
        // this.position.set(player.getX(), player.getY(), 0);

        float deltaTime = Gdx.graphics.getDeltaTime();
        // Define the dead zone size
        float deadZoneWidth = this.viewportWidth * 0.15f; // Adjust as needed
        float deadZoneHeight = this.viewportHeight * 0.15f; // Adjust as needed

        Transport[] playerTranport = this.player.getTransport();
        int currentPlayerTransport = this.player.getTransIdx();

        // Define the lag factor
        float lagFactor = (playerTranport[currentPlayerTransport].speed / 200); // Adjust as needed

        // Update camera position
        float targetX = player.getX();
        float targetY = player.getY();

        float dx = targetX - this.position.x;
        float dy = targetY - this.position.y;

        float scaledDx = dx  * lagFactor * deltaTime;
        float scaledDy = dy  * lagFactor * deltaTime;

        float minMovementThreshold = 0.1f; // Adjust as needed
        
        // Check if the player's movement exceeds the minimum threshold
        boolean exceedsThresholdX = Math.abs(scaledDx) > minMovementThreshold;
        boolean exceedsThresholdY = Math.abs(scaledDy) > minMovementThreshold;
        
        // Check if the player is outside the dead zone
        boolean outsideDeadZoneX = Math.abs(dx) > deadZoneWidth / 2;
        boolean outsideDeadZoneY = Math.abs(dy) > deadZoneHeight / 2;
        
        // Apply lag effect only if movement exceeds the minimum threshold and the player is outside the dead zone
        if ((exceedsThresholdX || exceedsThresholdY) && (outsideDeadZoneX || outsideDeadZoneY)) {
            // Apply lag effect
            this.position.x += scaledDx;
            this.position.y += scaledDy;
        }

    }
}
