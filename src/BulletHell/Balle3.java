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
public class Balle3 extends Balle
{
   public Balle3(FXGameCanvas canevas)
    {
        super(-10,-10,0,0);
        super.sprite = canevas.createSprite("/images/laser2.png", 7, 30, 1, true);
    }
}
