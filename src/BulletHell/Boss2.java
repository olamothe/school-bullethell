/**
 *
 * @author Olivier Lamothe
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BulletHell;
import FXGameEngine.FXGameCanvas;

/**
 * @author Olivier Lamothe
 */
public class Boss2 extends Boss
{
    private double nextShotX;
    private double nextShotY;

    public int [] pointTirX = {-41,41};
    public int [] pointTirY = {-62,-27,9};

    public Boss2(FXGameCanvas c,int delais)
    {
        super(delais);
        sprite = c.createSprite("/images/Boss2.png", 89, 150, 0, true);
        explosion = c.createSprite("/images/BlueExplosion.png",120,120,1 ,true);
        explosion.setVisible(false);
        sprite.setVisible(false);
        vie = 150;
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
        if(nextShotY>2)
            nextShotY=2;
        if(nextShotY<.2)
            nextShotY=.2;

        return nextShotY;
    }

}

