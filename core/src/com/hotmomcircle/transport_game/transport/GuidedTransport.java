package com.hotmomcircle.transport_game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.hotmomcircle.transport_game.GameScreen;
import com.hotmomcircle.transport_game.entity.Player;

public class GuidedTransport extends Transport {
    public Player player;

    public GuidedTransport(GameScreen game, Player player, String name, int speed, Texture[] images, String footprint, String staminaCost) {
        super(game, player, name, speed, images, footprint, staminaCost);

    }

    // method that moves the player from
    // one fixed point to another;
    // player movement (keyboard interrupts)
    // are disabled while they are moving 
    public void move(Player player, int steps[]) {
        
        // take player X, Y (probably the Node X, Y to keep it clean)
        // translate the player X, Y to each step on the route (SMOOTHLY TM)

            // mimic player key-press interrupts?
            // means we could have bus and luas as transport options in player class
            // but the player can't access them directly
            // it would handle the carbon and freshness increments

        // e.g. routeSteps = [[20, 0], [20, 0], [0, 20], [20, 0], [0, -20]....]
        // finish
        // player regains movement control
    }
    
}
