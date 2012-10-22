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
public class Boss3 extends Boss
{

    public int [] pointTirX = {-44,0,44};
    public int [] pointTirY = {71,75,71};

    public Boss3(FXGameCanvas c,int delais)
    {
        super(delais);
        sprite = c.createSprite("/images/Boss3.png", 125, 150, 0, true);
        explosion = c.createSprite("/images/BlueExplosion.png",120,120,1 ,true);
        explosion.setVisible(false);
        sprite.setVisible(false);
        vie = 200;
    }
   
    public double shootingPatternX()
    {
        return 0;
    }

    public double shootingPatternY()
    {
        return 0;
    }

}

