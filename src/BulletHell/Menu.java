
package BulletHell;
import FXGameEngine.*;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Olivier Lamothe
 */
public class Menu extends FXGameCanvas
{
    private int choix ;
    FXGameSprite s1;
    FXGameSprite s2;
    private int menuAffiche;
    private boolean newMenu;

    private int sonChoix;
    private int sonDeplacement;
    public Menu(FXGameMidlet m)
    {
        super(m);
        s1 = createSprite("/images/selecteur.png", 8,16,1,false);
        s2 = createSprite("/images/selecteur.png", 8,16,1,false);
        s1.setTransform(FXGameSprite.TRANS_MIRROR_ROT90);
        s2.setTransform(FXGameSprite.TRANS_MIRROR_ROT270);
        choix = 1;
        menuAffiche = 0;
        newMenu = true;
        sonChoix = addSound("/son/click3.wav");
        sonDeplacement = addSound("/son/click2.wav");
        setMusic("/son/ct_epoch.mid");
        setMusicVolume(100);
        setSFXVolume(40);
        startMusic();
        super.start();
        
    }

    protected void gamePaint(Graphics g) 
    {
        setBackground(-1);
        g.setColor(0,0,0);
        if(menuAffiche == 0)
        {
            g.drawString("New game", 90,50 , Graphics.TOP|Graphics.LEFT);
            g.drawString("Choix Level", 80, 100, Graphics.TOP| Graphics.LEFT);
            g.drawString("Instructions",70 , 150, Graphics.TOP|Graphics.LEFT);
            g.drawString("Quitter", 90, 200, Graphics.TOP|Graphics.LEFT);
            s1.setTransform(FXGameSprite.TRANS_MIRROR_ROT90);
            s2.setTransform(FXGameSprite.TRANS_MIRROR_ROT270);
            s1.paint(g);
            s2.paint(g);
        }
        if(menuAffiche == 1)
        {
            s1.setVisible(false);
            s2.setVisible(false);
            setBackground("/images/instructions.png");
        }
        if(menuAffiche == 2)
        {
            s2.setVisible(false);
            s1.setTransform(FXGameSprite.TRANS_NONE);
            s1.paint(g);
            g.drawString("Choix level", 80, 50, Graphics.TOP| Graphics.LEFT);
            g.drawString("1", 70,100,Graphics.TOP|Graphics.LEFT);
            g.drawString("2", 90,100,Graphics.TOP|Graphics.LEFT);
            g.drawString("3", 110,100,Graphics.TOP|Graphics.LEFT);
            g.drawString("4", 130,100,Graphics.TOP|Graphics.LEFT);
            g.drawString("5", 150,100,Graphics.TOP|Graphics.LEFT);
        }
    }

    protected void gameUpdate(int state, boolean enterState)
    {
        if(menuAffiche == 0)
        {
            s1.setVisible(true);
            s2.setVisible(true);
            newMenu=false;
            if(isPressed(DOWN_PRESSED))
            {
                choix++;
                playSound(sonDeplacement);
            }
            if(isPressed(UP_PRESSED))
            {
                choix--;
                playSound(sonDeplacement);
            }
            if(choix > 4)
                choix =4;
            if (choix <1)
                choix =1;
            s1.setPosition(50, choix*50+5);
            s2.setPosition(180, choix*50+5);
            s2.setVisible(true);
            if(isPressed(FIRE_PRESSED))
            {
                playSound(sonChoix);
                switch(choix)
                {
                    case 1:
                        stop(1);
                        break;
                    case 2:
                        s2.setVisible(false);
                        s1.setPosition(55, 120);
                        s1.setTransform(FXGameSprite.TRANS_NONE);
                        menuAffiche=2;
                        choix =1;
                        newMenu =true;
                        break;
                    case 3:
                        menuAffiche=1;
                        break;
                    case 4:
                        stop(0);
                        break;
                }
            }
        }
        else if(menuAffiche == 1)
        {
            if(isPressed(FIRE_PRESSED))
            {
                playSound(sonChoix);
                menuAffiche=0;
            }
        }
        else if(menuAffiche == 2)
        {
            if(isPressed(RIGHT_PRESSED))
            {
                playSound(sonDeplacement);
                choix ++ ;
                s1.setPosition(s1.X()+ 20, 120);
            }
            if(isPressed(LEFT_PRESSED))
            {
                playSound(sonDeplacement);
                choix --;
                s1.setPosition(s1.X() - 20 , 120);
            }
            if(choix > 5)
            {
                choix =5;
                s1.setPosition(s1.X() -20 , 120);
            }
            if(choix < 1)
            {
                choix =1;
                s1.setPosition(s1.X() + 20 ,120);
            }
            if(isPressed(FIRE_PRESSED) && ! newMenu)
            {
                playSound(sonChoix);
                setLevel(choix);
                menuAffiche = 0;
                choix =1;
            }
            newMenu = false;
        }
    }
}
