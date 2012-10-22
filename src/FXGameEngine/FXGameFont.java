/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FXGameEngine;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;
import java.io.*;

/**
 *
 * @author JFBoily
 */
public class FXGameFont
{
    private String text;
    private Image img;
    private int charW;
    private int charH;
    private int x, y;
    private boolean isVisible;
    private Sprite[] charSprites = new Sprite[255];
    private int[] charWidth = new int[256];

    public FXGameFont(Image image, int width, int height, String widthData)
    {
        img = image;
        charW = width;
        charH = height;
        isVisible = true;

        for(int i = 0; i < 255; i++)
        {
            charSprites[i] = new Sprite(img, width, height);
        }

        for(int i = 0; i < 256; i++)
        {
            charWidth[i] = charW;
        }

        if(widthData != null)
        {
            try
            {
                InputStream is = this.getClass().getResourceAsStream(widthData);

                for(int i = 0; i < 256; i++)
                {
                    charWidth[i] = is.read();
                    is.read(); // bidon : données écrites sur 2 octets...
                }

                is.close();
            }
            catch(Exception e)
            {
                System.err.println("Impossible de charger les largeurs de chars : "+e.getMessage());
            }
        }
    }

    public void setPosition(int posX, int posY)
    {
        x = posX;
        y = posY;
    }

    public int X()
    {
        return x;
    }

    public int Y()
    {
        return y;
    }

    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }

    public void setText(String t)
    {
        text = t;
        for(int i = 0; i < text.length(); i++)
        {
            charSprites[i].setFrame((int)text.charAt(i));
            //charSprites[i].setPosition(x + (i*charW), y);
        }
    }

    public void paint(Graphics g)
    {
        if(!isVisible)
            return;

        int sx = x;

        for(int i = 0; i < text.length(); i++)
        {
            charSprites[i].setPosition(sx, y);
            charSprites[i].paint(g);
            sx += charWidth[text.charAt(i)];
        }
    }

}
