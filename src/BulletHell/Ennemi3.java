/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

import FXGameEngine.FXGameCanvas;;

/**
 *
 * @author Olivier Lamothe
 */
public class Ennemi3  extends Ennemi
{
    //Ennemi 3 tire un laser continu pendant quelques seconde. La variable
    //isShooting est prévue pour empêcher le vaisseau de bouger tant qu'il tire.

    public Ennemi3 (FXGameCanvas c , int pTypeBalle)
    {
        super(Jeu.DELAIS_BALLE_3);
        super.sprite=c.createSprite("/images/ennemi3.png", 10, 20, 1, true);
        super.explosion = c.createSprite("images/explEnnemi.png" , 10 , 10 , 1,true);
        super.sprite.setVisible(false);
        super.explosion.setVisible(false);
        super.vie=4;
        typeBalle[0] = pTypeBalle;
    }


    public double shootingPatternX()
    {
        return 0;
    }

    public double shootingPatternY()
    {
        return 3;
    }

}
