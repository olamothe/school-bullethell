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


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import BulletHell.*;

/**
 * @author jfboily
 */

/**
 * 
 * La classe FXGameMidlet est la Master-Chief classe d'un jeu. Celle-ci est
 * responsable de géré le déroulement global du jeu (Splash->Menu<->Jeu) ainsi
 * que l'état global de l'application : démarrage, pause / unpause, arrêt.
 */
public class FXGameMidlet extends MIDlet
{
    /**
     * La classe DefaultCanvas sert de canevas "d'urgence" si le midlet n'a
     * plus de Displayable à afficher. Ne devrait pas être utilisée...
     * normalement.
     */
    private class DefaultCanvas extends Canvas implements CommandListener
    {
        Command c;
        FXGameMidlet midlet;
        public DefaultCanvas(FXGameMidlet m)
        {
            midlet = m;
            c = new Command("Quitter", Command.EXIT, 1);
            addCommand(c);
            setCommandListener(this);
        }
        public void commandAction(Command c, Displayable d)
        {
            midlet.destroyApp(true);
            midlet.notifyDestroyed();
        }

        protected void paint(Graphics g)
        {
            g.setColor(0);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(0xffffffff);
            g.drawString("Fin.", this.getWidth()/2, this.getHeight() /2, Graphics.HCENTER | Graphics.BASELINE);
        }
    }


    /**
     * Référence à l'objet Displayable présentement affiché.
     */
    private Displayable curDisplay = null;

    /**
     * Un objet FXGameProfile qui contient les données globales du jeu.
     * Par exemple : volume des sons, de la musique, le prochain niveau à jouer,
     * le nom du joueur, etc.
     */
    //public static FXGameProfile profile = new FXGameProfile();

    /**
     * Un objet Jeu qui réprésente la classe implémentant le jeu proprement dit.
     */
    Jeu jeu;
    int noLevel;
    int nbMort;
    /**
     * Un objet Splash qui affiche pendant x secondes les splashs / logos / etc.
     */
    Splash splash;

    Menu menu;

    FXGameCanvas resumeCanvas = null;
    /**
     * Le constructeur initialise le profile.
     */
    public FXGameMidlet()
    {
        // initialise le profile
        noLevel = 1;
        nbMort = 0;
        if(FXGameProfile.openRS())
        {
            FXGameProfile.readData();
            FXGameProfile.musicVolume = 20;
            FXGameProfile.sfxVolume = 100;
        }
        else
        {
            // pas de profile enregistré.

            FXGameProfile.level = 0;
            FXGameProfile.musicVolume = 20;
            FXGameProfile.sfxVolume = 100;
            FXGameProfile.playerName = "JoeBine";
        }

        FXGameProfile.closeRS();
        
    }

    /**
     * normalement, on commence toujours par les splashs.
     */
    public void startApp()
    {
        if(resumeCanvas != null)
        {
            switchGameCanvas(resumeCanvas);
        }
        else
        {
            splash = new Splash(this);
            switchGameCanvas(splash);
        }
    }

    /**
     * Appelée lorsque l'application est en pause (appel, etc.)
     */
    public void pauseApp()
    {
        resumeCanvas = jeu;
    }

    /**
     * Pour quitter l'application.
     * @param unconditional Indique si on quitter de façon non-conditionnelle.
     * Normalement true.
     */
    public void destroyApp(boolean unconditional)
    {

    }

    /**
     * Permet de changer l'objet Canvas à afficher.
     * @param c Le nouveau Canvas à afficher.
     */
    public void switchGameCanvas(Canvas c)
    {
        if(curDisplay != c)
        {
            if(c != null)
            {
                Display.getDisplay(this).setCurrent(c);
                curDisplay = c;
            }
            else
            {
                Display.getDisplay(this).setCurrent(new DefaultCanvas(this));
                curDisplay = null;
            }
        }
    }

    /**
     * La méthode Done permet à une classe de type FXGameCanvas d'indiquer à
     * l'objet FXGameMidlet qu'elle a terminé. On s'occupe donc ici de
     * libérer le FXGameCanvas terminé, d'en allouer un autre et de l'afficher.
     * par exemple:
     * lorsque Splash est terminé, on alloue un Menu et on affiche celui-ci.
     *
     * @param c Le FXGameCanvas appelant.
     */
    public void done(FXGameCanvas c)
    {
        // si le Splash vient de terminer
        if(c == splash)
        {
            // on le libère
            splash = null;
            // on alloue un Jeu
            menu = new Menu(this);
            // et on l'affiche!
            switchGameCanvas(menu);
        }
        else if(c == menu)
        {
            if(menu.status == 0)
            {
                // on save...
                FXGameProfile.openRS();
                FXGameProfile.saveData();
                FXGameProfile.closeRS();

                //... et on quitte
                destroyApp(true);
                notifyDestroyed();
            }
            else
            {
                // new game!
                // on detruit le menu
                menu = null;

                // on alloue un jeu
                jeu = new Jeu(this);

                // on l'affiche
                switchGameCanvas(jeu);
            }
        }
        else if(c == jeu)
        {
            // si un Jeu vient de terminer

            // on le libère
            jeu = null;
            if(c.status == 0)
            {
                // on cree un menu
                menu = new Menu(this);
                switchGameCanvas(menu);
            }
            else
            {
                jeu = new Jeu(this);
                switchGameCanvas(jeu);
            }
        }
    }

}
