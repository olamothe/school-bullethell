/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;
import FXGameEngine.*;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Olivier Lamothe
 */
public abstract class Ennemi
{
    public double x,y;
    public int delaisTir;
    public int frequenceTir;
    public int vie;
    public double vx,vy;
    public boolean playable;
    public int [] typeBalle;
    public FXGameSprite sprite;
    public FXGameSprite explosion;

    protected Ennemi(int delais)
    {
        x = -10;
        y = -10;
        vx=0;
        vy=0;
        playable = true;
        delaisTir = delais;
        frequenceTir = delais;
        typeBalle = new int [5];
    }
    protected void update()
    {
        if(!playable)
        {
            x+=vx;
            y+=vy;
            if(x>230|| x < 10)
                vx =0;
            if(y > 200)
                vy = 0;
            sprite.setPosition((int)x, (int)y);
            explosion.setPosition((int)x, (int)y);
        }
                
    }
    public void paint(Graphics g)
    {
        if(!playable)
            sprite.paint(g);
        explosion.paint(g);
    }

    public void estTouché(int puissance)
    {
        vie-= puissance;
        if(vie <= 0)
        {
            sprite.setVisible(false);
            explosion.setVisible(true);
            explosion.playAnim(false);
            exitStage();
        }
    }
    public void exitStage()
    {
        playable = true;
        sprite.setVisible(false);
    }
    public void enterStage(int x ,int y , double vx, double vy)
    {
        sprite.setVisible(true);
        explosion.setVisible(false);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        playable = false;

    }
    abstract double shootingPatternX();
    abstract double shootingPatternY();

}
