package BulletHell;
import FXGameEngine.*;
import javax.microedition.lcdui.Graphics;

public class Vaisseau
{
    public int x, y ;
    public FXGameSprite sprite;
    public FXGameSprite spriteExplo;
    public int puissance;
    private FXGameCanvas c;
    private final int X_MIN = 20 , X_MAX = 220 ,
            Y_MIN = 10 , Y_MAX = 310, VITESSE = 4 ;
    public boolean estInvulnerable;



    public Vaisseau(FXGameCanvas canevas)
    {
        c = canevas;
        sprite = canevas.createSprite("/images/vaisseau.png", 0,true);
        x = canevas.getWidth()/2;
        y = Y_MAX;
        sprite.setPosition(x, y);
        sprite.defineCollisionRectangle(18, 8, 4, 4);
        spriteExplo = canevas.createSprite("/images/expl.png", 20, 20, 0, true);
        spriteExplo.setVisible(false);
        puissance = 1;
        estInvulnerable = false;
    }

    protected void paint(Graphics g)
    {
       sprite.paint(g);
       spriteExplo.paint(g);
    }
    
    protected void update(int keys)
    {
       if(c.isDown(keys & FXGameCanvas.LEFT_PRESSED))
           x-=VITESSE;
       if(c.isDown(keys & FXGameCanvas.RIGHT_PRESSED))
           x+=VITESSE;
       if(c.isDown(keys & FXGameCanvas.DOWN_PRESSED))
           y+=VITESSE;
       if(c.isDown(keys & FXGameCanvas.UP_PRESSED))
           y-=VITESSE;
       if(x>X_MAX)
           x=X_MAX;
       if(x<X_MIN)
           x=X_MIN;
       if(y>Y_MAX)
           y=Y_MAX;
       if(y<Y_MIN)
           y=Y_MIN;

       sprite.setPosition(x, y);
       spriteExplo.setPosition(x, y);
    }
    public void augmenteFirePower()
    {
        puissance ++;
    }

}