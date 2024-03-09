package com.hotmomcircle.transport_game.ui;


public class gemArrow {
    
    public gem findClosestGem (List<gem> gems, Player player) {
        gem closestGem = null;
        int minDistance = Integer.MAX_VALUE;

        for (gem gem : gems){
            int distance = Math.abs(gem.x - player.x) + Math.abs(gem.y - player.y);
            if (distance < minDistance) {
                
            }
        }
    }


}
