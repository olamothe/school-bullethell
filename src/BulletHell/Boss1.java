/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

import FXGameEngine.FXGameCanvas;

/**
 * @author Olivier Lamothe
 */
public class Boss1 extends Boss
{
    private double nextShotX;
    private double nextShotY;

    public int [] pointTirX = {-65,0,65};
    public int [] pointTirY = {65,71,65};

    public Boss1(FXGameCanvas c,int delais)
    {
        super(delais);
        sprite = c.createSprite("/images/Boss1.png", 200, 150, 1, true);
        explosion = c.createSprite("/images/BlueExplosion.png",120,120,1 ,true);
        explosion.setVisible(false);
        sprite.setVisible(false);
        vie = 100;
        nextShotX = -1;
        nextShotY = 0.05;
    }
   
    
    public double shootingPatternX()
    {
        nextShotX += .2;
        if(nextShotX>1)
            nextShotX = -1;
        return nextShotX;
    }

    public double shootingPatternY()
    {
        if(nextShotX<0)
            nextShotY +=.09;
        if(nextShotX>0)
            nextShotY -=0.09;
        if(nextShotY>1)
            nextShotY=1;
        if(nextShotY<.2)
            nextShotY=.2;

        return nextShotY;
    }
    
}
