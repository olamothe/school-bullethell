package BulletHell;
import FXGameEngine.*;

/**
 * @author Olivier Lamothe
 */
public class Ennemi1 extends Ennemi
{
    private double  nextShotX , nextShotY;


    public Ennemi1(FXGameCanvas canevas , int pTypeBalle)
    {
        super(Jeu.DELAI_ENNEMI_1);
        super.sprite = canevas.createSprite("/images/ennemi1.png",15 , 12, 1, true);
        super.explosion = canevas.createSprite("images/explEnnemi.png" , 10 , 10 , 1,true);
        super.sprite.setVisible(false);
        super.explosion.setVisible(false);
        super.vie=2;
        nextShotX = -1;
        nextShotY = 0.05;
        typeBalle[0] = pTypeBalle;
    }

    protected double shootingPatternX()
    {
        nextShotX += .2;
        if(nextShotX>1)
            nextShotX = -1;

        return nextShotX;
    }

    protected double shootingPatternY()
    {
        if(nextShotX<0)
            nextShotY +=.2;
        if(nextShotX>0)
            nextShotY -=0.2;
        if(nextShotY>2)
            nextShotY=2;
        if(nextShotY<.2)
            nextShotY=.2;
        return nextShotY;
    }

}
