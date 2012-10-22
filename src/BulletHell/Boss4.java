/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

import FXGameEngine.FXGameCanvas;

/**
 * @author Olivier Lamothe
 */
public class Boss4 extends Boss
{

    public int [] pointTirX = {-15,15};
    public int [] pointTirY = {-28,0,65};

    public Boss4(FXGameCanvas c,int delais)
    {
        super(delais);
        sprite = c.createSprite("/images/Boss4.png", 140, 150, 1, true);
        explosion = c.createSprite("/images/BlueExplosion.png",120,120,1 ,true);
        explosion.setVisible(false);
        sprite.setVisible(false);
        vie = 250;
    }

    public double shootingPatternX()
    {
        return 0;
    }

    public double shootingPatternY()
    {
        return 1;
    }

}
