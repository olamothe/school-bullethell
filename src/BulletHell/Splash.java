
package BulletHell;

import FXGameEngine.*;
import javax.microedition.lcdui.Graphics;
import javax.microedition.m3g.Background;

/**
 * @author Olivier Lamothe
 */
public class Splash extends FXGameCanvas
{
    boolean fin;

    public Splash(FXGameMidlet m)
    {
        super(m);

        setBackground("/images/splash1.png");
        fin = false;
        super.start();

        
    }
    protected void gamePaint(Graphics g)
    {
    }

    protected void gameUpdate(int state, boolean enterState)
    {
        if(isPressed(FIRE_PRESSED & keys))
        {
            if(fin)
                stop(1);
            else
            {
                setBackground("/images/splash2.png");
                fin =true;
            }
        }
    }

}
