/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

import FXGameEngine.FXGameCanvas;

/**
 *
 * @author Olivier Lamothe
 */
public class Missile extends Balle
{
     public Missile(FXGameCanvas c)
    {
        super(-10, -10, 0, 0);
        super.sprite=c.createSprite("/images/missile.png", 8, 8, 0, true);
        sprite.playAnim(true);
    }

    public void update(int vaisseauX , int vaisseauY )
    {
        if(x>vaisseauX)
            vx -= .02;
        else
            vx += .02;
        if(y>vaisseauY)
            vy -=.02;
        else
            vy += 0.02;
        if(vx>1)
            vx=1;
        if(vx<-1)
            vx=-1;
        if(vy>1)
            vy=1;
        if(vy<-1)
            vy=-1;
        x +=vx;
        y+=vy;
        sprite.setPosition((int)x, (int)y);
    }


}
