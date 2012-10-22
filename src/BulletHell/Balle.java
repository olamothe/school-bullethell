/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

import FXGameEngine.FXGameSprite;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Olivier Lamothe
 */
public abstract class Balle
{

    public double x;
    public double y;
    public double vx;
    public double vy;
    public boolean playable;
    public FXGameSprite sprite;

    protected Balle(double px, double py,double pvx, double pvy)
    {
        x = px;
        y  =py;
        vx =pvx;
        vy = pvy;
        playable = true;
    }
    public void update()
    {
        x+=vx;
        y+=vy;
        sprite.setPosition((int)x, (int)y);
    }
    public void paint(Graphics g)
    {
        if(! playable)
            sprite.paint(g);
    }
    public void enterStage(Ennemi ennemi)
    {
        playable = false;
        this.x = (double)ennemi.x;
        this.y = (double)ennemi.y;
        this.vx = ennemi.shootingPatternX();
        this.vy = ennemi.shootingPatternY();
    }
    public void enterStage (Boss boss , int x , int y)
    {
        playable = false;
        this.x = x;
        this.y= y;
        this.vx = boss.shootingPatternX();
        this.vy = boss.shootingPatternY();
    }
    public void exitStage()
    {
        playable=true;
    }

}
