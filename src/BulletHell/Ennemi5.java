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
public class Ennemi5 extends Ennemi
{
    public Ennemi5(FXGameCanvas c , int pTypeBalle)
    {
        super(Jeu.DELAIS_MISSILE);
        super.sprite = c.createSprite("/images/ennemi5.png",15 , 13, 1, true);
        super.explosion = c.createSprite("images/explEnnemi.png" , 10 , 10 , 1,true);
        super.sprite.setVisible(false);
        super.explosion.setVisible(false);
        super.vie=10;
        typeBalle[0] = pTypeBalle;
    }

    double shootingPatternX()
    {
       return 0;
    }

    double shootingPatternY()
    {
       return 0;
    }
}
