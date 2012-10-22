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
public class BalleVaisseau extends Balle
{
    public BalleVaisseau(FXGameCanvas canevas , int frame)
    {
       super(-10,-10,0,0);
       super.sprite = canevas.createSprite("/images/ballevaisseau.png", 5, 16, 1, true);
       super.sprite.setFrame(frame);
    }
}
