/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;
import FXGameEngine.*;
/**
 *
 * @author Olivier Lamothe
 */
public class Balle1 extends Balle
{

    public Balle1(FXGameCanvas canevas)
    {
        super(-10,-10,0,0);
        super.sprite = canevas.createSprite("/images/petiteBalle.png", 5, 5, 1, true);

    }
    
}
