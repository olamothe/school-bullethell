/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

import FXGameEngine.FXGameCanvas;

/**
 * @author Olivier Lamothe
 */
public class Boss5 extends Boss
{

    private double nextShotX;
    private double nextShotY;

    public int [] pointTirX = {-72,-18,18,73};
    public int [] pointTirY = {0,65};

    public Boss5(FXGameCanvas c,int delais)
    {
        super(delais);
        sprite = c.createSprite("/images/Boss5.png", 166, 150, 1, true);
        explosion = c.createSprite("/images/BlueExplosion.png",120,120,1 ,true);
        explosion.setVisible(false);
        sprite.setVisible(false);
        vie = 300;

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