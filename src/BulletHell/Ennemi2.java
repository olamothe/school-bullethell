

package BulletHell;
import FXGameEngine.*;

/**
 * @author Olivier Lamothe
 */
public class Ennemi2 extends Ennemi
{
    //Cet ennemi tire en diagonal aux 4 coins en meme temps. noCycleTir sert
    //à savoir dans quel coin l'ennemi doit tirer son prochain tir.
    private int noCycleTir;

    public Ennemi2(FXGameCanvas canevas , int pTypeBalle)
    {
        super(Jeu.DELAIS_BALLE_2);
        super.sprite=canevas.createSprite("/images/ennemi2.png",15 , 15,1, true);
        super.explosion = canevas.createSprite("images/explEnnemi.png" , 10 , 10 , 1,true);
        super.sprite.setVisible(false);
        super.explosion.setVisible(false);
        noCycleTir =0;
        super.vie=3;
        typeBalle[0] = pTypeBalle;
    }

    protected double shootingPatternX()
    {
        noCycleTir ++;
        if(noCycleTir>3)
            noCycleTir=0;
        if(noCycleTir == 0 || noCycleTir ==3)
            return -.5;
        else
            return .5;
    }

    protected double shootingPatternY()
    {
        if(noCycleTir==0 || noCycleTir ==1)
            return -.5;
        else
            return .5;
    }

}
