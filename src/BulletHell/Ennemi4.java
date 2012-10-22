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
public class Ennemi4 extends Ennemi
{
    public Ennemi4(FXGameCanvas c , int pTypeBalle)
    {
        super(Jeu.DELAIS_BALLE_4);
        super.sprite = c.createSprite("/images/ennemi4.png",13 , 13, 1, true);
        super.explosion = c.createSprite("images/explEnnemi.png" , 10 , 10 , 1,true);
        super.sprite.setVisible(false);
        super.explosion.setVisible(false);
        super.vie=5;
        typeBalle[0] = pTypeBalle;
    }

    double shootingPatternX()
    {
       return .7;
    }

    double shootingPatternY()
    {
       return .7;
    }

}
