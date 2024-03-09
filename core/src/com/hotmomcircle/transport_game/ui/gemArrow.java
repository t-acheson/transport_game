package com.hotmomcircle.transport_game.ui;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;

public class gemArrow {
    
    private Gem findClosestGem (List<Gem> gems, Player player) {
        Gem closestGem = null;
        int minDistance = Integer.MAX_VALUE;

        for (Gem gem : gems){
            int distance = Math.abs(gem.x - player.x) + Math.abs(gem.y - player.y);
            if (distance < minDistance) {
                minDistance = distance;
                closestGem = gem;
            }
        }

        return closestGem;
    }

    private double gemAngle (Player player, Gem gem) {
        double deltaX = gem.x - player.x;
        double deltaY = gem.y - player.y;
        double angleInRadians = Math.atan2(deltaY, deltaX);
        double angleInDegrees = Math.toDegrees(angleInRadians);
        return angleInDegrees;
    }


    public gemArrow(Skin skin){
        //calls findClosestGem
        //returns gem 

        //calls gemAngle
        //returns angle between player and gem 

        //loads arrow image at 0 degrees
        //rotates arrow to angle as above

        //puts arrow on ui 
    }

}
