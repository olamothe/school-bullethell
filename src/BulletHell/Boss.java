/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

/**
 *
 * @author Olivier Lamothe
 */
public abstract class Boss extends Ennemi
{
    public Boss(int delais)
    {
        super(delais);
        playable = true;
        delaisTir=delais;

    }
    public void estTouché(int puissance)
    {
        vie -= puissance;
        sprite.setFrame(1);
        if(vie < 0)
        {
            sprite.setVisible(false);
            explosion.setVisible(true);
            explosion.playAnim(false);
            exitStage();

        }
    }
    public abstract double shootingPatternX();
    public abstract double shootingPatternY();
    
    public void update()
    {
        y += vy;
        if(y>65)
            vy = 0;
        sprite.setPosition((int)x, (int)y);
        sprite.setFrame(0);
        explosion.setPosition((int)x, (int)y);
    }
}