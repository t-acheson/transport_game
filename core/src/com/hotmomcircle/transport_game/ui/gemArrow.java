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

public class gemArrow {
    
    private Image arrowImage;

    private Gem findClosestGem (Array<Gem> gems, Player player) {
        Gem closestGem = null;
        int minDistance = Integer.MAX_VALUE;

        for (Gem gem : gems){
            int distance = Math.abs(gem.getX() - player.getX()) + Math.abs(gem.getY() - player.getY());
            if (distance < minDistance) {
                minDistance = distance;
                closestGem = gem;
            }
        }

        return closestGem;
    }

    private double gemAngle (Player player, Gem gem) {
        double deltaX = gem.getX() - player.getX();
        double deltaY = gem.getY() - player.getY();
        double angleInRadians = Math.atan2(deltaY, deltaX);
        double angleInDegrees = Math.toDegrees(angleInRadians);
        return angleInDegrees;
    }


    public gemArrow(Skin skin, Player player, Array<Gem> gems, Table table){
        
        //load arrow 
        Texture arrowTexture = new Texture(Gdx.files.internal("arrow.png"));
        TextureRegion arrowRegion = new TextureRegion(arrowTexture);
        arrowImage = new Image(arrowRegion);
        
        //calls findClosestGem
        //returns gem 
        Gem closestGem = findClosestGem(gems, player);
    
        //calls gemAngle
        //returns angle between player and gem 
        double angleToGem = gemAngle(player, closestGem);      

        //rotates arrow to angle as above
        arrowImage.setRotation((float) angleToGem);

        //TODO @Eoin I need your help here, padding right doesn't seem to affect position of arrow in the way I want so I can display on screen properly?
        table.add(arrowImage).size(arrowTexture.getWidth(), arrowTexture.getHeight()).padRight(10);
}
       
    

    public void update(Player player, Array<Gem> gems) {
        // Update the gemArrow UI with the current player and gem positions
        Gem closestGem = findClosestGem(gems, player);
        double angleToGem = gemAngle(player, closestGem);
        arrowImage.setRotation((float) angleToGem);
    }

}
