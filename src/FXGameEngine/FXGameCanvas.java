  /*  
  Moteur de jeu 2D FXGameEngine
  Copyright (C) 2010 JF Boily — Tous droits réservés.
  
  Ce programme est un logiciel libre ; vous pouvez le redistribuer ou le
  modifier suivant les termes de la “GNU General Public License” telle que
  publiée par la Free Software Foundation : soit la version 3 de cette
  licence, soit (à votre gré) toute version ultérieure.
  
  Ce programme est distribué dans l’espoir qu’il vous sera utile, mais SANS
  AUCUNE GARANTIE : sans même la garantie implicite de COMMERCIALISABILITÉ
  ni d’ADÉQUATION À UN OBJECTIF PARTICULIER. Consultez la Licence Générale
  Publique GNU pour plus de détails.
  
  Vous devriez avoir reçu une copie de la Licence Générale Publique GNU avec
  ce programme ; si ce n’est pas le cas, consultez :
  <http://www.gnu.org/licenses/>. 
  */
package FXGameEngine;


import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.*;

//import java.io.*;
/**
 * La classe FXGameCanvas est la classe de base pour un jeu.
 * Cette classe hérite de GameCanvas et implémente Runnable.
 * Elle fournit des méthodes pour :<ul>
 * <li>la gestion du background</li>
 * <li>la gestion des sprites</li>
 * <li>la gestion des sons / musique</li>
 * <li>la gestion des états (state machine)</li>
 * <li>la gestion des inputs (clavier)</li></ul>
 *
 * Pour utiliser efficacement FXGameEngine, vous devriez MINIMALEMENT déclarer
 * 3 instances de classes héritant de FXGameCanvas :
 * <ol>
 * <li>Splash</li>
 * <li>Menu</li>
 * <li>Jeu</li>
 * </ol>
 * <p><b>ATTENTION :</b> La méthode start() doit absolument être appelée dans le
 * construteur d'une classe héritant de FXGameCanvas afin de démarrer la boucle principale.
 * @author jfboily
 */
public abstract class FXGameCanvas extends GameCanvas implements Runnable
{
    /**
     * Nombre maximal de Sprites supportés.
     */
    public static final int MAX_SPRITES = 256;


    /**
     * Implémente une simple liste chaînée de Sprites.
     */
    private class FXGameSpriteList
    {
        FXGameSprite sprite;
        FXGameSpriteList next;
    }

    /**
     * La liste des sprites.
     */
    private FXGameSpriteList spritesList;
    /**
     * Tableau contenant les noms des images pour les sprites.
     */
    private String[] spritesFicList;
    /**
     * Tableau contenant les Image(s) utilisées par les sprites.
     */
    private Image[] spritesImgList;
    /**
     * Nombre de sprites actuellement dans la liste.
     */
    private int nbSprites;

    private final int MAX_FONTS = 255;
    private int nbFonts = 0;
    private FXGameFont[] fonts = new FXGameFont[MAX_FONTS];
    /**
     * Couleur du background.
     */
    private int bgColor = 0;
    /**
     * Image du background.
     */
    private Image bgImage;
    /**
     * Tiled background
     */
    private Image tilset;
    private int[][] tiles;
    private int bgx, bgy;
    private int bgNbTilesX, bgNbTilesY;
    private int tileWidth, tileHeight;
    private int bgWidth, bgHeight;
    public TiledLayer bgLayer = null;
    /**
     * Dimensions de l'écran.
     */
    protected int SCR_WIDTH, SCR_HEIGHT;
    /**
     * Coordonnées du centre de l'écran.
     */
    protected int SCR_MIDWIDTH, SCR_MIDHEIGHT;
    /**
     * Thread de la boucle principale.
     */
    private Thread t;
    /**
     * Thread de la boucle des sons.
     */
    private Thread soundThread;
    /**
     * Indique si on doit quitter ou nom la boucle principale du canvas.
     */
    private boolean quit = false;

    /**
     * État courant.
     */
    private int curState;
    /**
     * Indique le changement d'état.
     */
    private boolean enterState;
    /**
     * Tableau des états.
     */
    private int[] states;
    /**
     * Nombre d'états.
     */
    private int nbStates;
    /**
     * Nombre maximal d'états.
     */
    private static final int MAX_STATES = 64;

    /**
     * Utilisés pour le calcul du DeltaTime.
     */
    private long curTime, oldTime, deltaTime;

    /**
     * Masque des touches du clavier présentement enfoncées.
     */
    protected int keys;
    /**
     * Masque des touches du clavier enfoncées au frame précédent.
     */
    protected int oldKeys;

    /**
     * Référence à la FXGameMidlet.
     */
    protected FXGameMidlet midlet;

    /**
     * Constantes de clavier
     */
    private final int KEYSTATE_UP = 0;
    private final int KEYSTATE_PRESSED = 2;
    private final int KEYSTATE_DOWN = 1;
    private final int KEYSTATE_RELEASED = 4;
    /**
     * Constante pour la touche CMD_LEFT.
     */
    protected final int CMD_LEFT_PRESSED = -6;
    /**
     * Constante pour la touche CMD_RIGHT.
     */
    protected final int CMD_RIGHT_PRESSED = -7;
    /**
     * Constante pour la touche '#'.
     */
    protected final int POUND_PRESSED = 35;
    /**
     * Constante pour la touche '*'.
     */
    protected final int STAR_PRESSED = 42;
    private boolean poundKey = false;
    private boolean starKey = false;
    private boolean cmdKeyLeft = false;
    private boolean cmdKeyRight = false;

    /**
     * Tableau d'état du clavier.
     */
    private int[] keyStates;


    /**
     * Référence du FXGameSoundManager.
     */
    private FXGameSoundManager soundManager;

    /**
     * État du FXGameCanvas : permet de retourner une valeur à la FXGameMidlet.
     */
    public int status;
    
    /**
     * Dessine tous le canvas: Background et Sprites.
     * @param g Graphics à utiliser pour dessiner.
     */
    protected abstract void gamePaint(Graphics g);

    /**
     * gameUpdate Met à jour l'état du canvas.
     * @param state État actuel.
     * @param enterState Indique un changement d'état.
     */
    protected abstract void gameUpdate(int state, boolean enterState);

    /**
     * Constructeur unique.
     * @param m Référence à la FXGameMidlet parente.
     */
    public FXGameCanvas(FXGameMidlet m)
    {
        super(false);
        setFullScreenMode(true);
        SCR_WIDTH = this.getWidth();
        SCR_HEIGHT = this.getHeight();
        SCR_MIDWIDTH = SCR_WIDTH / 2;
        SCR_MIDHEIGHT = SCR_HEIGHT / 2;
        bgColor = 0;
        bgImage = null;
        spritesList = new FXGameSpriteList();//new FXGameSprite[MAX_SPRITES];
        spritesFicList = new String[MAX_SPRITES];
        spritesImgList = new Image[MAX_SPRITES];


        curState = -1;
        enterState = false;

        keyStates = new int[20];

        states = new int[MAX_STATES];

        keys = 0;
        oldKeys = 0;
        midlet = m;

        soundManager = new FXGameSoundManager();

        
    }

    public int getLevel ()
    {
        return midlet.noLevel;
    }
    public void setLevel(int lvl)
    {
        midlet.noLevel = lvl;
    }
    public int getNbMort()
    {
        return midlet.nbMort;
    }
    public void setNbMort(int nb)
    {
        midlet.nbMort = nb;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestion du Background">
    /**
     * Définit le background avec une couleur unie.
     * @param color couleur du background.
     */
    public void setBackground(int color)
    {
        bgImage = null;
        bgColor = color;
    }

    /**
     * Définit le background avec une image.
     * @param fic nom du fichier de l'image.
     */
    public void setBackground(String fic)
    {
        try
        {
            bgImage = Image.createImage(fic);
        }
        catch(Exception e)
        {
            System.err.println("Impossible de charger le fichier : "+fic);
            System.err.println("-->"+e.getMessage());
        }
    }

    /**
     * Définit le background comme étant un tiledbg.
     * @param ficts nom du fichier du tileset
     * @param fictd nom du fichier des tiles
     */
    public void setBackground(String ficts, String fictd, int nbTilesX, int nbTilesY, int tileWidth, int tileHeight)
    {
        try
        {
            bgImage = Image.createImage(ficts);
        }
        catch(Exception e)
        {
            System.err.println("TILED BACKGROUND ERROR : cannot load tileset "+ficts);
        }
        bgLayer = new TiledLayer(nbTilesX, nbTilesY, bgImage, tileWidth, tileHeight);

        try
        {
            tiles = new int[nbTilesY][nbTilesX];
            InputStream is = this.getClass().getResourceAsStream(fictd);
            String s = "";
            int c;
            int x = 0;
            int y = 0;
            int val = 0;
            int nbread = 0;
            while((c = is.read()) != -1)
            {
                if(c >= '0' && c <= '9')
                {
                    val = (val * 10) + (c - 48);
                    nbread++;
                }
                else
                {
                    if(nbread > 0)
                    {
                        //int v = Integer.parseInt(s);
                        tiles[y][x] = val;
                        x++;
                        if(x >= nbTilesX)
                        {
                            x = 0;
                            y++;
                        }
                        s="";
                        val = 0;
                        nbread = 0;
                    }
                }
            }

            if(nbread > 0)
            {
                tiles[y][x] = val;
            }

            for(int j = 0; j < nbTilesY; j++)
            {
                for(int i = 0; i < nbTilesX; i++)
                {
                    bgLayer.setCell(i, j, tiles[j][i]);
                }
            }
            is.close();
        }
        catch(Exception e)
        {
            System.err.println("TILED BACKGROUND ERROR : cannot load tile data "+fictd);
            System.err.println(e.getMessage());
        }
    }

    public void moveBG(int dx, int dy)
    {
        if(bgLayer != null)
        {
            bgLayer.move(dx, dy);
        }
    }

    public int getBGX()
    {
        return bgLayer.getX();
    }

    public int getBGY()
    {
        return bgLayer.getY();
    }

    public void setBGPos(int x, int y)
    {
        bgLayer.setPosition(x, y);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestion des Sprites">
    /**
     * Méthode pour la création d'un FXGameSprite. Enregistre le sprite dans la
     * liste et gère automatique sont affichage.
     * @param fic le fichier .png du sprite
     * @param priority priorité du Sprite. 0 = devant, 1 = défaut, > = derrière
     * @return FXGameSprite créé.
     */
    public FXGameSprite createSprite(String fic, int priority)
    {
        return createSprite(fic, -1, -1, priority, false);
    }
    /**
     * Méthode pour la création d'un FXGameSprite. Enregistre le sprite dans la
     * liste et gère automatique sont affichage.
     * @param fic le fichier .png du sprite
     * @param priority priorité du Sprite. 0 = devant, 1 = défaut, > = derrière
     * @param defineRefCenter indiqie si on doit utiliser le centre du Sprite
     * comme point de référence (positionnement) du Sprite ou non.
     * defaut = false = coin supérieur gauche.
     * @return FXGameSprite créé.
     */
    public FXGameSprite createSprite(String fic, int priority, boolean defineRefCenter)
    {
        return createSprite(fic, -1, -1, priority, defineRefCenter);
    }
    /**
     * Méthode pour la création d'un FXGameSprite. Enregistre le sprite dans la
     * liste et gère automatique sont affichage.
     * @param fic le fichier .png du sprite
     * @param width largeur d'un frame d'animation. -1 pour utiliser la
     * largeur de l'image.
     * @param height hauteur d'un frame d'animation. -1 pour utiliser la
     * hauteur de l'image.
     * @param priority : priorité du Sprite. 0 = devant, 1 = défaut, > = derrière
     * @return FXGameSprite créé.
     */
    public FXGameSprite createSprite(String fic, int width, int height, int priority)
    {
        return createSprite(fic, width, height, priority, false);
    }
    /**
     * Méthode pour la création d'un FXGameSprite. Enregistre le sprite dans la
     * liste et gère automatique sont affichage.
     * @param fic le fichier .png du sprite
     * @param width largeur d'un frame d'animation. -1 pour utiliser la
     * largeur de l'image.
     * @param height hauteur d'un frame d'animation. -1 pour utiliser la
     * hauteur de l'image.
     * @param priority priorité du Sprite. 0 = devant, 1 = défaut, > = derrière
     * @param defineRefCenter : indiqie si on doit utiliser le centre du Sprite
     * comme point de référence (positionnement) du Sprite ou non.
     * defaut = false = coin supérieur gauche.
     * @return FXGameSprite créé.
     */
    public FXGameSprite createSprite(String fic, int width, int height, int priority, boolean defineRefCenter)
    {
        FXGameSprite sprite = null;
        if(nbSprites == MAX_SPRITES)
        {
            throw new IndexOutOfBoundsException("Le nombre maximum de Sprites est atteint : "+fic);
        }

        Image img = loadImage(fic);
        // cree le sprite
        if(width == -1 || height == -1)
        {
            sprite = new FXGameSprite(img, defineRefCenter);
        }
        else
        {
            try
            {
                sprite = new FXGameSprite(img, width, height, defineRefCenter);
            }
            catch(Exception ex)
            {
                System.err.println(ex.getMessage());
                System.err.println("Impossible de creer le sprite : "+fic);
            }
        }
        sprite.setPriority(priority);
        // ajoute le sprite au tableau
        addFXGameSprite(sprite);
        return sprite;
    }


    private void addFXGameSprite(FXGameSprite sprite)
    {
        int prio = sprite.getPriority();
        //1er sprite?
        if(spritesList == null)
        {
            spritesList = new FXGameSpriteList();
        }
        if(spritesList.sprite == null)
        {
            spritesList.sprite = sprite;
            spritesList.next = null;
        }
        else
        {
            FXGameSpriteList l = spritesList;
            FXGameSpriteList prev = null;
            // avance dans la liste jusqu'au sprite plus prioritaire
			while(l != null && l.sprite.getPriority() >= prio)
            {
                prev = l;
                l = l.next;
            }
            if(l == null) // ajout en fin de liste
            {
                prev.next = new FXGameSpriteList();
                prev.next.sprite = sprite;
                prev.next.next = null;
            }
            else // ajout au milieu ou au debut
            {
				if(prev == null) // debut de liste
				{
					spritesList = new FXGameSpriteList();
					spritesList.sprite = sprite;
					spritesList.next = l;
				}
				else // milieu de liste
				{
					prev.next = new FXGameSpriteList();
					prev.next.sprite = sprite;
					prev.next.next = l;
				}
            }
        }
    }


    private void addFont(FXGameFont f)
    {
        if(nbFonts < MAX_FONTS)
        {
            fonts[nbFonts] = f;
            nbFonts++;
        }
        else
        {
            System.err.println("Impossible d'ajouter la font. MAX_FONTS atteint.");
        }
    }
    
    public FXGameFont createFont(String fic, int width, int height, String widthData)
    {
        FXGameFont font = null;

        Image img = loadImage(fic);
        font = new FXGameFont(img, width, height, widthData);

        addFont(font);
        return font;
    }

    private Image loadImage(String fic)
    {
        Image img = null;
        int i =0;
        
        // cherche le nom du fichier dans la liste
        while(i<MAX_SPRITES && spritesFicList[i]!=null && img==null)
        {
            if(spritesFicList[i].compareTo(fic)==0)
            {
                img = spritesImgList[i];
            }
            else
            {
                i++;
            }
        }


        // si pas trouvé, on le charge et on l'ajoute.
        // si la liste est plein, il y aura une exception de lancée et catchée.
        if(img == null)
        {
            try
            {
                img = Image.createImage(fic);
                spritesImgList[i] = img;
                spritesFicList[i] = fic;
            }
            catch (Exception e)
            {
                System.err.println("Impossible de charger l'image : "+fic);
                System.err.println(e.getMessage());
            }
        }

        return img;
    }


    /**
     * Supprime un Sprite de la liste.
     * @param s
     */
    public void deleteSprite(Sprite s)
    {
        FXGameSpriteList l = spritesList;

        // si c'est le premier
        if(l.sprite == s)
        {
            spritesList = l.next;
            l.sprite = null;
            l.next = null;
            return;
        }
        else
        {
            FXGameSpriteList prev = spritesList;
            l = l.next;
            while(l != null)
            {
                if(l.sprite == s)
                {
                    prev.next = l.next;
                    l.sprite = null;
                    return;
                }
                prev = l;
                l = l.next;
            }
        }

    }

    /**
     * Supprime TOUS les Sprites de la liste.
     */
    public void deleteAllSprites()
    {
        FXGameSpriteList l = spritesList;
        while(l != null)
        {
            l.sprite = null;
            l = l.next;
        }
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Paint">
    /**
     * Dessine tous le canvas dans cet ordre
     * <ol>
     * <li>Le background</li>
     * <li>La liste de Sprites, en ordre de priorité.</li>
     * </ol>
     * @param g Graphics à utiliser pour dessiner.
     */
    public void paint(Graphics g)
    {
        g.setColor(bgColor);
        g.fillRect(0, 0, SCR_WIDTH, SCR_HEIGHT);

        if(bgLayer != null)
        {
            bgLayer.paint(g);
        }
        else if(bgImage != null)
        {
            g.drawImage(bgImage, 0, 0, 0);
        }
        else
        {
            
        }
//        for(int i = 0; i < nbSprites; i++)
//        {
//            if(spritesList[i] != null && spritesList[i].isVisible())
//            {
//                spritesList[i].updateAnim(deltaTime);
//                spritesList[i].paint(g);
//            }
//        }
        FXGameSpriteList l = spritesList;
        while(l != null)
        {
            if(l.sprite != null && l.sprite.isVisible())
            {
                l.sprite.updateAnim(deltaTime);
                l.sprite.paint(g);
            }
            l = l.next;
        }

        for(int i = 0; i < nbFonts; i++)
        {
            fonts[i].paint(g);
        }

        gamePaint(g);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Update">
    /**
     * Met à jour l'état du canvas :
     * 1. Calcule le deltatime
     * 2. Appelle le gameUpdate de la classe fille
     * 3. Reinitialise l'état du clavier.
     */
    private void update()
    {
        boolean enter = enterState;
        enterState = false;
        gameUpdate(curState, enter);
        if(cmdKeyLeft)
            cmdKeyLeft = false;
        if(cmdKeyRight)
            cmdKeyRight = false;
        if(starKey)
            starKey = false;
        if(poundKey)
            poundKey = false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestion du thread / deltaTime">

    /**
     * Démarre la boucle principale du canvas.
     */
    public void start()
    {
        t = new Thread(this);
        t.start();

        soundThread = new Thread(soundManager);
        soundThread.start();
    }

    /**
     * Provoque la fin de la boucle principale du canvas et retour à la MIDLet.
     */
    public void stop()
    {
        quit = true;
        status = 0;

        soundManager.stop();
    }

    /**
     * Provoque la fin de la boucle principale du canvas et retour à la MIDlet.
     *
     * @param status : valeur à retourner à la MIDlet.
     */
    public void stop(int status)
    {
        quit = true;
        this.status = status;

        soundManager.stop();
    }

    /**
     * Boucle principale du canvas.
     */
    public void run()
    {
        oldTime = System.currentTimeMillis();
        curTime = System.currentTimeMillis();
    
        while(!quit)
        {
            curTime = System.currentTimeMillis();
            deltaTime = curTime - oldTime;
            oldKeys = keys;
            keys = getKeyStates();
            update();
            repaint();
            long pauseTime = System.currentTimeMillis() - curTime;
            pause(15 - pauseTime);
            oldTime = curTime;
        }

        midlet.done(this);
    }

    /**
     * Suspend l'exécution de la boucle principale.
     * @param t durée (en milisecondes) de la pause.
     */
    public void pause(long p)
    {
        if(p <= 0)
        {
            System.err.println("pause : "+p);
            return;
        }
        
        try
        {
            t.sleep(p);
        }
        catch(Exception e)
        {
            System.err.println("SLLEP FAIL : "+e.getMessage());
        }
    }

    /**
     * Indique la durée du frame courant en milisecondes.
     * @return deltaTime
     */
    public long getDeltaTime()
    {
        /*
        if(deltaTime > 60 || deltaTime < 0)
            deltaTime = 31;*/
        
        return deltaTime;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestion des States">
    /**
     * Ajoute un état au tableau d'états.
     * @param s
     */
    public void addState(int s)
    {
        states[nbStates] = s;
        nbStates++;
    }

    /**
     * Provoque un changement d'état du canvas.
     * @param s
     */
    public void switchState(int s)
    {
        if(s != curState)
        {
            enterState = true;
            curState = s;
        }
    }

    /**
     * Indique s'il y a eu une demande de changement d'état dans le frame courant.
     * @return vrai ou faux.
     * frame courant.
     */
    public boolean exitState()
    {
        return enterState;
    }

    /**
     * Indique l'état courant du canvas.
     * @return identificateur de l'état courant.
     */
    public int getCurState()
    {
        return curState;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestion du clavier">

    /**
     * Gestion de l'évènement KEY_PRESS. Utilisé seulement pour les touches
     * "spéciales" : CMD_LEFT, CMD_RIGHT, KEY_STAR et KEY_POUND.
     * @param key code de la touche.
     */
    public void keyPressed(int key)
    {
        if(key == CMD_LEFT_PRESSED)
        {
            cmdKeyLeft = true;
        }

        if(key == CMD_RIGHT_PRESSED)
        {
            cmdKeyRight = true;
        }

        if(key == POUND_PRESSED)
        {
            poundKey = true;
        }

        if(key == STAR_PRESSED)
        {
            starKey = true;
        }
    }
    /**
     * Indique si une touche a été appuyée depuis le frame précédent.
     * @param key code de la touche.
     * @return vrai ou faux.
     */
    public boolean isPressed(int key)
    {
        if(key == CMD_LEFT_PRESSED)
        {
            return cmdKeyLeft;
        }

        if(key == CMD_RIGHT_PRESSED)
        {
            return cmdKeyRight;
        }

        if(key == POUND_PRESSED)
        {
            return poundKey;
        }

        if(key == STAR_PRESSED)
        {
            return starKey;
        }

        if((keys & key) != 0 && (oldKeys & key) == 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Indique si une touche a été relâchée depuis le frame précédent.
     * @param key code de la touche.
     * @return vrai ou faux.
     */
    public boolean isReleased(int key)
    {
        if((keys & key) == 0 && (oldKeys & key) != 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Indique si une touche est présentement enfoncée.
     * @param key code de la touche.
     * @return vrai ou faux.
     */
    public boolean isDown(int key)
    {
        return ((keys & key) != 0);
    }

    /**
     * Indique si une touche N'EST PAS présentement enfoncée.
     * @param key code de la touche.
     * @return vrai ou faux.
     */
    public boolean isUp(int key)
    {
        return ((keys & key) == 0);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestion du son/musique">
    /**
     * Ajoute un son au SoundManager.
     * @param fic nom du fichier sonore à charger.
     * @return Identificateur du son.
     */
    public int addSound(String fic)
    {
        return soundManager.addSound(fic);
    }

    /**
     * Joue un son.
     * @param s Identificateur du son.
     */
    public void playSound(int s)
    {
        soundManager.playSound(s);
    }

    /**
     * Modifie le volume des effets sonores.
     * @param pc Volume des effets sonores (0 = muet, 100 = 100%).
     */
    public void setSFXVolume(int pc)
    {
        soundManager.setSFXVolume(pc);
    }

    /**
     * Modifie le volume de la musique.
     * @param pc Volume de la musique (0 = muet, 100 = 100%).
     */
    public void setMusicVolume(int pc)
    {
        soundManager.setMusicVolume(pc);
    }

    /**
     * Charge la musique. Celle-ci ne sera jouée que lors de l'appel de startMusic().
     * @param fic nom du fichier sonore de la musique.
     */
    public void setMusic(String fic)
    {
        soundManager.setMusic(fic);
    }

    /**
     * Démarre la lecture de la musique.
     */
    public void startMusic()
    {
        soundManager.startMusic();
    }

    /**
     * Arrête la lecture de la musique.
     */
    public void stopMusic()
    {
        soundManager.stopMusic();
    }

    // </editor-fold>


}
