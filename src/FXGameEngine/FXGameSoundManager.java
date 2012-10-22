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

import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.io.*;

/**
 * La classe FXGameSoundManager implémente les fonctionnalités d'un gestionnaire
 * de sons / musique pour FXGameEngine. <p>Un objet FXGameManager est
 * automatiquement créé par un objet héritant de FXGameCanvas. <b>EN AUCUN TEMPS
 * </b> vous ne devez appeler les méthodes de cette classe.
 * @see FXGameCanvas
 * @author jfboily
 */
public class FXGameSoundManager implements Runnable
{
   // private Manager soundManager;
    private Player[] sounds;
    private Player music;
    private int nbSounds;
    private final int MAX_SOUNDS = 7;
    private long timeSound;
    boolean quit = false;
    private int soundToPlay = -1;

    /**
     * Constructeur.
     */
    public FXGameSoundManager()
    {
        sounds = new Player[MAX_SOUNDS];
        timeSound = 0;
        quit = false;
    }

    /**
     * Démarre le Thread dédié au son.
     */
    public void start()
    {
        quit = false;
        soundToPlay = -1;
    }

    /**
     * Arrête le thread dédié au son et nettoie.
     */
    public void stop()
    {
        quit = true;
        if(music != null)
        {
            stopMusic();
            music.deallocate();
            music.close();

        }

        for(int i = 0; i<MAX_SOUNDS; i++)
        {
            if(sounds[i] != null)
            {
                try
                {
                    sounds[i].stop();
                    sounds[i].deallocate();
                    sounds[i].close();
                }
                catch(Exception e)
                {}


            }
        }

    }

        // <editor-fold defaultstate="collapsed" desc="Gestion du son/musique">
    /**
     *
     * @param fic
     * @return
     */
    public int addSound(String fic)
    {
        if(nbSounds >= MAX_SOUNDS)
            throw new IndexOutOfBoundsException("Le nombre maximum de sons est atteint : "+fic);

        int ret = nbSounds;

        try
        {
            InputStream is = getClass().getResourceAsStream(fic);
            if(fic.endsWith(".mp3"))
            {
                sounds[nbSounds] = Manager.createPlayer(is, "audio/mpeg");
            }
            else if(fic.endsWith(".wav"))
            {
                sounds[nbSounds] = Manager.createPlayer(is, "audio/x-wav");
            }
            else
            {
                throw new Exception("Le format du fichier est invalide : "+fic);
            }
            sounds[nbSounds].realize();
            sounds[nbSounds].prefetch();
            nbSounds++;
        }
        catch(Exception e)
        {
            System.err.println("impossible de creer le son : "+fic);
            System.err.println("-->"+e.getMessage());
        }
        return ret;
    }

    /**
     *
     * @param s
     */
    public void playSound(int s)
    {
        soundToPlay = s;
////        try
////        {
////            Manager.playTone(69, 30, 100);
////        }catch(Exception e){}
//        if(System.currentTimeMillis() - timeSound < 250)
//            return;
//
//        if(sounds[s] != null)
//        {
////            for(int i = 0; i<nbSounds; i++)
////            {
////                try
////                {
////                    if(sounds[i].getState() == Player.STARTED)
////                    {
////                        sounds[i].stop();
////                    }
////                }
////                catch(Exception e)
////                {
////                    System.err.println("Impossible d'arrêter le son : "+Integer.toString(i));
////                    System.err.println("-->"+e.getMessage());
////                }
////            }
//
//            try
//            {
//                sounds[s].start();
//                timeSound = System.currentTimeMillis();
//            }
//            catch(Exception e)
//            {
//                System.err.println("Impossible de joueur le son : "+Integer.toString(s));
//                System.err.println("-->"+e.getMessage());
//            }
//        }
    }

    /**
     *
     * @param pc
     */
    public void setSFXVolume(int pc)
    {
        for(int i=0; i<nbSounds; i++)
        {
            VolumeControl vc = (VolumeControl)sounds[i].getControl("VolumeControl");
            vc.setLevel(pc);
        }
    }

    /**
     *
     * @param pc
     */
    public void setMusicVolume(int pc)
    {
        VolumeControl vc = (VolumeControl)music.getControl("VolumeControl");
        vc.setLevel(pc);
    }

    /**
     *
     * @param fic
     */
    public void setMusic(String fic)
    {
        try
        {
            InputStream is = getClass().getResourceAsStream(fic);
            //sounds[nbSounds] = Manager.createPlayer(is, "audio/x-wav");
            if(fic.endsWith(".mp3"))
            {
                music = Manager.createPlayer(is, "audio/mpeg");
            }
            else if(fic.endsWith(".mid"))
            {
                music = Manager.createPlayer(is, "audio/midi");
            }
            else
            {
                throw new Exception("Type de fichier incorrect");
            }
            music.realize();
            music.prefetch();
            music.setLoopCount(-1);
        }
        catch(Exception e)
        {
            System.err.println("Impossible de creer la musique.");
            System.err.println("-->"+e.getMessage());
        }
    }

    /**
     *
     */
    public void startMusic()
    {
        try
        {
            music.start();
        }
        catch(Exception e)
        {
            System.err.println("Impossible de jouer la musique.");
            System.err.println("-->"+e.getMessage());
        }
    }

    /**
     *
     */
    public void stopMusic()
    {
        try
        {
            music.stop();
        }
        catch(Exception e)
        {
            System.err.println("Impossible d'arreter la musique.");
            System.err.println("-->"+e.getMessage());
        }
    }

    // </editor-fold>

    /**
     * Boucle principale du moteur de son.
     */
    public void run()
    {
        //quit = true;
        
        while(!quit)
        {
            if(soundToPlay != -1 && System.currentTimeMillis() - timeSound > 250)
            {
                try
                {
                    if(sounds[soundToPlay] != null)
                    {
                        //VolumeControl vc = (VolumeControl)music.getControl("VolumeControl");
                        //vc.setLevel(15);
                        sounds[soundToPlay].start();
                        timeSound = System.currentTimeMillis();
                    }
                }
                catch(Exception e)
                {
                }

                soundToPlay = -1;
            }
            else
            {
//                boolean playing = false;
//                for(int i = 0; i < nbSounds; i++)
//                {
//                    try
//                    {
//                        if(sounds[i] != null)
//                        {
//                            if(sounds[i].getState() == Player.STARTED)
//                            {
//                                playing = true;
//                            }
//                        }
//                    }
//                    catch (Exception e)
//                    {}
//                }
//
//                if(!playing)
//                {
//                    try
//                    {
//                        VolumeControl vc = (VolumeControl)music.getControl("VolumeControl");
//                        vc.setLevel(15);
//                    }
//                    catch(Exception e)
//                    {}
//                }
            }

            try
            {
                Thread.sleep(30);
            }
            catch (Exception e)
            {}
        }
    }
}
