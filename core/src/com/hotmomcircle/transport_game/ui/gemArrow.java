package com.hotmomcircle.transport_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.hotmomcircle.transport_game.entity.Gem;
import com.hotmomcircle.transport_game.entity.Player;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.scenes.scene2d.Actor;;


public class gemArrow extends Actor {
    
    private Image arrowImage;

    private Gem findClosestGem (Array<Gem> gems, Player player) {
        Gem closestGem = null;
        int minDistance = Integer.MAX_VALUE;

        for (Gem gem : gems){
            int distance = Math.round(Math.abs(gem.getX() - player.getX()) + Math.abs(gem.getY() - player.getY()));
            if (distance < minDistance) {
                minDistance = distance;
                closestGem = gem;
            }
        }

        return closestGem;
    }

    private double gemAngle (Player player, Gem gem) {

        if (gem == null) {

        System.err.println("No gem found for player.");
            return 0; 
        }
        
        double deltaX = gem.getX() - player.getX();
        double deltaY = gem.getY() - player.getY();
        double angleInRadians = Math.atan2(deltaY, deltaX);
        double angleInDegrees = Math.toDegrees(angleInRadians);
        return angleInDegrees;
    }


    public gemArrow(Skin skin, Player player, Array<Gem> gems, Table table){
           
        try {
            // Load arrow
            Texture arrowTexture = new Texture(Gdx.files.internal("arrow.png"));
            arrowImage = new Image(arrowTexture);
            arrowImage.setOrigin(arrowImage.getWidth() / 2, arrowImage.getHeight() / 2);
            
            // Calls findClosestGem
            // Returns gem 
            Gem closestGem = findClosestGem(gems, player);
        
            // Calls gemAngle
            // Returns angle between player and gem 
            double angleToGem = gemAngle(player, closestGem);      
    
            // Rotates arrow to angle as above
            arrowImage.setRotation((float) angleToGem);
    
            // Add arrowImage to the table
            table.add(arrowImage).size(arrowTexture.getWidth(), arrowTexture.getHeight()).pad(1);
        } catch (Exception e) {
            // Handle any exceptions that might occur
            e.printStackTrace();
        }
    }
    

    public void update(Player player, Array<Gem> gems) {
        // Update the gemArrow UI with the current player and gem positions
        Gem closestGem = findClosestGem(gems, player);
        double angleToGem = gemAngle(player, closestGem);
        arrowImage.setRotation((float) angleToGem);
    }

}
