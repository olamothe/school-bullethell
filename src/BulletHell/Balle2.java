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
public class Balle2 extends Balle
{
    public Balle2(FXGameCanvas c)
    {
        super(-10, -10, 0, 0);
        super.sprite=c.createSprite("/images/petiteBalle.png", 5, 5, 1, true);
        super.sprite.setFrame(1);
    }

}
