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
public class Balle4 extends Balle
{
    public int posDepartX;
    public Balle4(FXGameCanvas canevas)
    {
        super(-10,-10,0,0);
        super.sprite = canevas.createSprite("/images/petiteBalle.png", 5, 5, 1, true);
        super.sprite.setFrame(1);
    }
    public void update()
    {
        x+=vx;
        y+=vy;
        if(x > posDepartX+30 || x < posDepartX-30)
            vx = -vx;

        sprite.setPosition((int)x, (int)y);
    }
    public void enterStage(Ennemi ennemi)
    {
        playable = false;
        this.x = (double)ennemi.x;
        this.y = (double)ennemi.y;
        this.posDepartX = (int)ennemi.x;
        this.vx = ennemi.shootingPatternX();
        this.vy = ennemi.shootingPatternY();
    }
}
