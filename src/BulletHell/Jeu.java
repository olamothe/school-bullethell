package BulletHell;
import FXGameEngine.*;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
/**
 * @author Olivier Lamothe
 */
public class Jeu extends FXGameCanvas
{
    private Vaisseau vaisseau;
    private long timerInvul;
    private final int DELAIS_INVUL = 3000;

    public static final int DELAIS_BALLE_VAISSEAU = 250;
    private int delaisBalle;
    private BalleVaisseau ballesVaisseau[];

    private Ennemi1 ennemis1[];
    public static final int DELAI_ENNEMI_1= 800;

    private Balle balles1[];

    private Ennemi2 ennemis2[];

    private Balle balles2[];
    public static final int DELAIS_BALLE_2 = 1000;

    private Ennemi3 ennemis3[];

    private Balle balles3[];
    public static final int DELAIS_BALLE_3= 3000;

    private Ennemi4 ennemis4[];

    private Balle4 balles4[];
    public static final int DELAIS_BALLE_4= 500;

    private Ennemi5 ennemis5[];

    private Missile missiles[];
    public static final int DELAIS_MISSILE= 5000;

    private Boss1 boss1;
    public static final int DELAIS_BOSS1 = 8000;

    private Boss2 boss2;
    public static final int DELAIS_BOSS2 = 8000;

    private Boss3 boss3;
    public static final int DELAIS_BOSS3 = 12000;

    private Boss4 boss4;
    public static final int DELAIS_BOSS4 = 10000;

    private Boss5 boss5;
    public static final int DELAIS_BOSS5 = 8000;

    private Stack tousEnnemi;
    private Stack tousBoss;
    private Stack tousBalles;

    private level unLevel;
    private int noLevel;

    private boolean debutLevel;
    private long delaisdebutLevel;

    private boolean finLevel;
    private long delaisFinlevel;

    private boolean enPause;

    private boolean retourMenu;

    FXGameSprite win;

    private int sonLaser;
    private int sonExplosion;
    private int sonPause;

    public Jeu(FXGameMidlet m)
    {
        super(m);
        noLevel = getLevel();
        vaisseau = new Vaisseau(this);
        timerInvul = DELAIS_INVUL;
        initializeEnnemi();
        initializeBalles();
        
        unLevel = new level(noLevel,this);
        debutLevel = true;
        delaisdebutLevel = 5000;
        finLevel = false;
        delaisFinlevel = 5000;
        enPause =false;
        retourMenu = false;
        win = createSprite("/images/fuck_yea.png", 240,205,1);
        sonLaser = addSound("/son/laser.wav");
        sonExplosion = addSound("/son/explosion.wav");
        sonPause = addSound("/son/getcoin.wav");
        setMusic(unLevel.fichierMusique);
        startMusic();
        super.start();
        
    }

    private void initializeEnnemi()
    {
        ennemis1 = new Ennemi1[30];
        ennemis2 = new Ennemi2[30];
        ennemis3 = new Ennemi3[30];
        ennemis4 = new Ennemi4[30];
        ennemis5 = new Ennemi5[30];

        for(int i = 0 ; i < 30 ; i++)
        {
            ennemis1[i] = new Ennemi1(this ,1);
            ennemis2[i] = new Ennemi2(this, 2);
            ennemis3[i] = new Ennemi3(this ,3);
            ennemis4[i] = new Ennemi4(this ,4);
            ennemis5[i] = new Ennemi5(this ,5);
        }
        boss1 = new Boss1(this,DELAIS_BOSS1);
        boss2 = new Boss2(this,DELAIS_BOSS2);
        boss3 = new Boss3(this, DELAIS_BOSS3);
        boss4 = new Boss4(this, DELAIS_BOSS4);
        boss5 = new Boss5(this, DELAIS_BOSS5);
        
        tousEnnemi = new Stack();
        tousEnnemi.push(ennemis1);
        tousEnnemi.push(ennemis2);
        tousEnnemi.push(ennemis3);
        tousEnnemi.push(ennemis4);
        tousEnnemi.push(ennemis5);
        tousBoss = new Stack();
        tousBoss.push(boss1);
        tousBoss.push(boss2);
        tousBoss.push(boss3);
        tousBoss.push(boss4);
        tousBoss.push(boss5);
    }

    private void initializeBalles()
    {
        ballesVaisseau = new BalleVaisseau[100];
        delaisBalle = DELAIS_BALLE_VAISSEAU;

        balles1 = new Balle1[100];
        balles2 = new Balle2[100];
        balles3 = new Balle3[100];
        balles4 = new Balle4[100];
        missiles = new Missile[100];

        //On change la couleur des balles du vaisseau dépendamment du level
        //sinon on les voit mal a cause du background
        int couleurBalleVaisseau=0;
        switch(noLevel)
        {
            case 2:
                couleurBalleVaisseau = 2;
                break;
            case 3:
                couleurBalleVaisseau = 1;
                break;
            case 4:
                couleurBalleVaisseau = 2;
                break;
            case 5:
                couleurBalleVaisseau = 1;
        }
        for(int i = 0 ; i < 100; i ++)
        {
            ballesVaisseau[i] = new BalleVaisseau(this,couleurBalleVaisseau);
            balles1[i] = new Balle1(this);
            balles2[i] = new Balle2(this);
            balles3[i] = new Balle3(this);
            balles4[i] = new Balle4(this);
            missiles[i] = new Missile(this);
        }
        tousBalles = new Stack();
        tousBalles.push(ballesVaisseau);
        tousBalles.push(balles1);
        tousBalles.push(balles2);
        tousBalles.push(balles3);
        tousBalles.push(balles4);
        tousBalles.push(missiles);
    }

    protected void gamePaint(Graphics g)
    {
        g.setColor(255,255,255);
        g.fillRect(0, 0, getWidth(), getHeight());
        bgLayer.paint(g);  
        vaisseau.paint(g);
        paintEnnemis(g);
        paintBalle(g);
        paintPause(g);
        paintDebutLevel(g);
        paintFinLevel(g);
        paintRetourMenu(g);
    }
    private void paintRetourMenu(Graphics g)
    {
        if(retourMenu)
        {
            g.setColor(255,255,255);
            g.fillRect(10, 10,230,170);
            g.setColor(0,0,0);
            g.drawString("Retour au menu principal", 20, 50,Graphics.LEFT|Graphics.TOP );
            g.drawString("# pour quitter", 50, 100,Graphics.LEFT|Graphics.TOP);
            g.drawString("Feu pour continuer jeu", 50, 150, Graphics.LEFT|Graphics.TOP);
        }
    }
    private void paintPause(Graphics g)
    {
        if(enPause)
        {
            g.setColor(255,255,255);
            g.fillRect(60, 130, 110, 30);
            g.setColor(255, 0, 0);
            g.drawString("***PAUSE***", 70, 140, Graphics.LEFT|Graphics.TOP);
        }
    }
    private void paintDebutLevel(Graphics g)
    {
        if(debutLevel)
        {
            g.setColor(255, 0,0);
            String msg = "";
            switch(noLevel)
            {
                case 1:
                    msg="LEVEL 1";
                    break;
                case 2:
                    msg= "LEVEL 2";
                    break;
                case 3:
                    msg="LEVEL 3";
                    break;
                case 4:
                    msg="LEVEL 4";
                    break;
                case 5:
                    msg="LEVEL 5";
                    break;
            }
            g.drawString(msg, 100, 50, Graphics.LEFT|Graphics.TOP);
            g.drawString("GET READY ! ",100,100,Graphics.LEFT|Graphics.TOP);
            g.drawString(String.valueOf((int)delaisdebutLevel/1000+1)+" ...",100,150,Graphics.LEFT|Graphics.TOP);
        }
    }
    private void paintFinLevel(Graphics g)
    {
        if(finLevel)
        {
            g.fillRect(0, 0, 240, 320);
            win.setPosition(0, 0);
            win.paint(g);
            g.setColor(255,0,0);
            if(noLevel < 5)
                g.drawString("Next Level !", 70, 220, Graphics.TOP|Graphics.LEFT);
            else
            {
                g.drawString("Bravo, le jeu est terminé !",20, 220, Graphics.TOP|Graphics.LEFT);
                g.drawString("Vous etes mort " + String.valueOf(getNbMort()) +" fois" , 20, 240 ,Graphics.TOP|Graphics.LEFT);
            }

        }
    }
    private void paintBalle(Graphics g)
    {
        //100 balles dans chaque vecteur
        for(int i=0; i < 100;i++)
        {
            //tousBalles est un stack de toutes les balles
            for(int k = 0 ; k < tousBalles.size() ; k++)
            {
                Balle [] tempo = (Balle [])tousBalles.elementAt(k);
                if(!tempo[i].playable)
                    tempo[i].paint(g);
            }
        }
    }
    private void paintEnnemis(Graphics g)
    {

        for(int i = 0 ; i < tousEnnemi.size() ; i++)
        {
            Ennemi [] e = (Ennemi[])tousEnnemi.elementAt(i);
            for(int k = 0 ; k < 30 ; k++)
            {
                
                e[k].paint(g);
                if(e[k].explosion.animDone())
                {
                    e[k].explosion.setVisible((false));
                }
            }
            Boss b = (Boss)tousBoss.elementAt(i);
            b.paint(g);
            if(b.explosion.animDone())
            {
                b.explosion.setVisible(false);
                finLevel=true;
            }
        }

    }
    protected void gameUpdate(int state, boolean enterState)
    {
        //Etat countdown de depart
        if(debutLevel)
        {
            delaisdebutLevel-=getDeltaTime();
            if(delaisdebutLevel< 0)
                debutLevel = false;
        }
        //Etat fin du level
        else if (finLevel)
        {
            //delais pour l'affichage de l'image/texte à la fin du level
            delaisFinlevel-=getDeltaTime();
            if(delaisFinlevel < 0)
            {
                finLevel = false;
                //On passe au level suivant
                setLevel(getLevel() + 1);
                //Si on vient de passer le level 5, le jeu est terminé
                //Retour au menu
                if(getLevel()>5)
                {
                    setLevel(1);
                    setNbMort(0);
                    stop(0);
                }
                //Le jeu recommence au niveau suivant
                else
                    stop(1);
            }
        }
        //Etat pause
        else if (enPause)
        {
            if(isPressed(FIRE_PRESSED))
                enPause = false;
        }
        //Etat de demande retour au menu
        else if (retourMenu)
        {
            if(isPressed(FIRE_PRESSED))
                retourMenu= false;
            if(isPressed(POUND_PRESSED ))
                stop(0);

        }
        //Etat de jeu
        else
        {
            //Demande de pause
            if(isPressed(FIRE_PRESSED))
            {
                enPause = true;
                playSound(sonPause);
            }
            //Demande de retour au menu
            if(isPressed(POUND_PRESSED))
            {
                retourMenu =true;
                playSound(sonPause);
            }
            
            //Le vaisseau à été touché et l'explosion est terminé
            //On réaffiche le vaisseau normal
            if(vaisseau.spriteExplo.isVisible() && vaisseau.spriteExplo.animDone())
            {
                vaisseau.sprite.setVisible(true);
                vaisseau.spriteExplo.setVisible(false);
            }
            //Si le vaisseau n'est pas en train d'exploser
            //le joueur peut le faire bouger et tirer
            //et les ennemis peuvent continuer a tirer/bouger
            if(! vaisseau.spriteExplo.isVisible())
            {
                vaisseau.update(keys);
                if(vaisseau.estInvulnerable)
                {
                    timerInvul-=getDeltaTime();
                    if(timerInvul<0)
                    {
                        timerInvul=DELAIS_INVUL;
                        vaisseau.estInvulnerable=false;
                    }
                }
                vaisseauShoot(3);
                tirEnnemi();
                unLevel.update(getDeltaTime());
                ennemisUpdate(tousEnnemi);
                bossUpdate(tousBoss);
                balleUpdate();
                checkCollisions();
                spawnEnnemi();
            }
           
           
            
        }
    }
    private void ennemisUpdate(Stack s)
    {
        //Max 30 ennemi dans le vecteur
        for(int i = 0 ; i < 30 ; i++)
        {
            for(int k = 0 ; k < s.size() ; k++)
            {
                Ennemi [] e = (Ennemi[])s.elementAt(k);
                e[i].update();
            }
        }
    }
    private void bossUpdate(Stack s)
    {
        for(int i = 0 ; i < s.size() ; i++)
        {
            Boss b = (Boss)s.elementAt(i);
            b.update();
        }
    }
    private void spawnEnnemi()
    {
        //Spawn représente la position dans le vecteur qui décrit les temps de spawn des ennemis
        //dans la classe level. Autrement dit le temps (en seconde) de ce spawn
        int spawn = unLevel.getSpawn();
        //-1 signifie qu'aucun ennemi ne spawn durant ce frame
        if(spawn != -1)
        {
            //On parcourt tout le vecteur qui décrit le type à spawner au temps "spawn"
            for(int i =0 ; i < unLevel.getType()[spawn].length ; i++)
            {
                int type = unLevel.getType()[spawn][i];
                Ennemi [] e = null;
                Boss b = null;
                //On determine le type d'ennemi a spawner
                switch(type)
                {
                    case 1:
                        e = ennemis1;
                        break;
                    case 2:
                        e= ennemis2;
                        break;
                    case 3:
                        e = ennemis3;
                        break;
                    case 4:
                        e= ennemis4;
                        break;
                    case 5:
                        e= ennemis5;
                        break;
                    case 6:
                        b = boss1;
                    case 7:
                        b = boss2;
                        break;
                    case 8:
                        b = boss3;
                        break;
                    case 9:
                        b = boss4;
                        break;
                    case 10:
                        b = boss5;
                        break;
                }
                //Si on veut spawner un ennemi normal
                if(e!=null)
                {
                    e[findPlayable(e)].enterStage
                            (unLevel.getPosX()[spawn][i],
                            unLevel.getPosY()[spawn][i],
                            unLevel.getVX()[spawn][0],
                            unLevel.getVY()[spawn][0]);
                }
                //sinon le boss arrive
                else
                {
                    b.enterStage(120, -100, 0, 1);
                    stopMusic();
                    setMusic("/son/ct_boss2.mid");
                    startMusic();
                }
            }
        }
    }
    private void tirEnnemi()
    {
        //Tir pour les ennemis normaux(30 dans le vecteur)
        for(int i = 0 ; i< 30 ; i++)
        {
            //Parcours du stack de tous les ennemis
            for(int k = 0 ; k < tousEnnemi.size() ; k ++)
            {
                Ennemi e []  = (Ennemi[])tousEnnemi.elementAt(k);
                if(! e[i].playable)
                {
                    //Delais de tir
                    e[i].delaisTir -= getDeltaTime();
                    if(e[i].delaisTir<0)
                    {
                        e[i].delaisTir = e[i].frequenceTir;
                        //Regarde quelle type de balle cet ennemi tire
                        int typeBalle = e[i].typeBalle[0];
                        Balle b [] = null;
                        switch(typeBalle)
                        {
                            case 1:
                                b=balles1;
                                break;
                            case 2:
                                b=balles2;
                                break;
                            case 3:
                                b=balles3;
                                break;
                            case 4:
                                b=balles4;
                                break;
                            case 5:
                                b= missiles;
                                break;
                        }
                        //Balle entre en jeu
                        b[findPlayable(b)].enterStage(e[i]);
                    }
                }
            }
            //Tir pour les boss ; chaque boss a différent point d'ancrage de tir
            //(i.e) de ou part la balle
            if(!boss1.playable)
            {
                boss1.delaisTir -= getDeltaTime();
                if(boss1.delaisTir<0)
                {
                    boss1.delaisTir = DELAIS_BOSS1;
                    for(int j = 0 ; j < boss1.pointTirX.length ; j++)
                    {
                        int posVecteur = findPlayable(balles1);
                        //pour entrer en jeu une balle à besoin d'un ennemi qui la tire (pour savoir le "pattern" de tir)
                        //une position en X et en Y (qui correspondent au point de tir sur le sprite du boss)
                        balles1[posVecteur].enterStage(boss1, boss1.sprite.X()+boss1.pointTirX[j],boss1.sprite.Y()+ boss1.pointTirY[j]);
                    }
                }
            }
            if(!boss2.playable)
            {
                boss2.delaisTir -= getDeltaTime();
                if(boss2.delaisTir<0)
                {
                    boss2.delaisTir = DELAIS_BOSS2;
                    for(int j = 0 ; j < boss2.pointTirY.length ; j++)
                    {
                        for(int k = 0 ; k < boss2.pointTirX.length ; k++)
                        {
                            int posVecteur = findPlayable(balles1);
                            balles1[posVecteur].enterStage(boss2, boss2.sprite.X()+boss2.pointTirX[k],boss2.sprite.Y()+ boss2.pointTirY[j]);
                        }
                    }
                }
            }
            if(!boss3.playable)
            {
                boss3.delaisTir -=getDeltaTime();
                if(boss3.delaisTir<0)
                {
                    boss3.delaisTir = DELAIS_BOSS3;
                    for(int j = 0 ; j < boss3.pointTirX.length ; j++)
                    {
                        int posVecteur = findPlayable(missiles);
                        missiles[posVecteur].enterStage(boss3, boss3.sprite.X()+boss3.pointTirX[j],boss3.sprite.Y()+ boss3.pointTirY[j]);
                    }
                }
            }
            if(!boss4.playable)
            {
                boss4.delaisTir -= getDeltaTime();
                if(boss4.delaisTir<0)
                {
                    boss4.delaisTir = DELAIS_BOSS4;
                    for(int j = 0 ; j < boss4.pointTirY.length ; j++)
                    {
                        for(int k = 0 ; k < boss4.pointTirX.length ; k++)
                        {
                            if(j == boss4.pointTirY.length -1)
                            {
                                int posVecteur = findPlayable(balles3);
                                balles3[posVecteur].enterStage(boss4,boss4.sprite.X(),boss4.sprite.Y()+boss4.pointTirY[j]);
                                k = boss4.pointTirX.length;
                            }
                            else
                            {
                                int posVecteur = findPlayable(missiles);
                                missiles[posVecteur].enterStage(boss4, boss4.sprite.X()+boss4.pointTirX[k],boss4.sprite.Y()+ boss4.pointTirY[j]);
                            }
                        }
                    }
                }
            }
            if(!boss5.playable)
            {
                boss5.delaisTir-=getDeltaTime();
                if(boss5.delaisTir<0)
                {
                    boss5.delaisTir = DELAIS_BOSS5;
                    for(int j = 0 ; j < boss5.pointTirY.length ; j++)
                    {
                        for(int k = 0 ; k < boss5.pointTirX.length ; k++)
                        {
                            if(j == boss5.pointTirY.length -1)
                            {
                                balles1[findPlayable(balles1)].enterStage(boss5, boss5.sprite.X()+boss5.pointTirX[1],boss5.sprite.Y()+ boss5.pointTirY[j]);
                                balles1[findPlayable(balles1)].enterStage(boss5, boss5.sprite.X()+boss5.pointTirX[2],boss5.sprite.Y()+ boss5.pointTirY[j]);
                            }
                            else
                            {
                                balles2[findPlayable(balles2)].enterStage(boss5, boss5.sprite.X()+boss5.pointTirX[k],boss5.sprite.Y()+ boss5.pointTirY[j]);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void balleUpdate()
    {
        //Update toutes les balles en jeu
        for(int i =0; i< 100 ; i++)
        {
            for(int k = 0 ; k < tousBalles.size()-1 ; k++)
            {
                Balle b [] = (Balle [])tousBalles.elementAt(k);
                if(!b[i].playable)
                {
                    b[i].update();
                    if(balleEstSortieEcran(b[i]))
                        b[i].exitStage();
                }
            }
            if(! missiles[i].playable)
            {
                missiles[i].update(vaisseau.x, vaisseau.y);
            }
        }
    }


    /**Crée les tirs du vaisseau. puissanceTirs est la puissance relative du tir
    Préférablement un nombre impair plus grand que 0*/
    private void vaisseauShoot(int puissanceTirs)
    {
        delaisBalle -= getDeltaTime();
        if(delaisBalle<0)
        {
            delaisBalle=DELAIS_BALLE_VAISSEAU;
            for(int i = 0 ; i < puissanceTirs ; i++)
            {
                int posVecteur = 0;
                while(! ballesVaisseau[posVecteur].playable)
                {
                    posVecteur++;
                }
                ballesVaisseau[posVecteur].playable = false;
                ballesVaisseau[posVecteur].x = vaisseau.x+((puissanceTirs/2)*10) - (10*i);
                ballesVaisseau[posVecteur].y = vaisseau.y;
                ballesVaisseau[posVecteur].vy = -10;
            }
            playSound(sonLaser);
        }
    }
    private void checkCollisionEnnemis(Balle balle, Ennemi ennemi)
    {
        if(balle.sprite.collidesWith(ennemi.sprite, false))
        {
            balle.exitStage();
            ennemi.estTouché(vaisseau.puissance);
        }
    }
    private boolean checkCollisionVaisseau(Balle balle)
    {
        if(balle.sprite.collidesWith(vaisseau.sprite, false) && ! vaisseau.estInvulnerable)
        {
            vaisseau.estInvulnerable=true;
            setSFXVolume(100);
            playSound(sonExplosion);
            setSFXVolume(40);
            balle.exitStage();
            return true;
        }
        return false;
    }
    private void vaisseauExplode()
    {
        setNbMort(getNbMort() + 1);
        vaisseau.sprite.setVisible(false);
        vaisseau.spriteExplo.setVisible(true);
        vaisseau.spriteExplo.playAnim(false);
    }
    private boolean balleEstSortieEcran(Balle b)
    {
        //Un missille peut sortir un peu de l'écran afin de revenir
        //chasser le joueur
        if(b.getClass() == missiles[0].getClass())
        {
            if (b.x < -50 || b.x >290 ||b.y< -50 ||b.y>370)
                return true;
            return false;
        }
        else
        {
            if (b.x < 0 || b.x >240 ||b.y<-100 ||b.y>320)
                return true;
            return false;
        }
    }
    private void checkCollisions()
    {
        for(int i = 0 ; i < 100 ; i++)
        {
            //Les balles du vaisseau en collision avec ...
            if(! ballesVaisseau[i].playable)
            {
                for(int j = 0 ; j < 30 ;j++)
                {
                    //ennemis normaux ...
                    for(int k = 0 ; k < tousEnnemi.size() ; k++)
                    {
                        Ennemi [] e = (Ennemi [])tousEnnemi.elementAt(k);
                        if(! e[j].playable)
                            checkCollisionEnnemis(ballesVaisseau[i], e[j]);
                    }
                }
                //boss ....
                for(int j = 0 ; j < tousBoss.size() ; j++)
                {
                    Boss b = (Boss)tousBoss.elementAt(j);
                    if(!b.playable)
                        checkCollisionBoss(ballesVaisseau[i], b);
                }
                //missiles ...
                for(int j =0 ; j<100;j++)
                {
                    if(! missiles[j].playable)
                    {
                        if(missiles[j].sprite.collidesWith(ballesVaisseau[i].sprite, false))
                        {
                            missiles[j].exitStage();
                        }
                    }
                }              
            }
            //Collision des balles ennemis avec le vaisseau
            for(int j = 1 ; j < tousBalles.size() ; j++ )
            {
                Balle b [] = (Balle [])tousBalles.elementAt(j);
                if(!b[i].playable)
                {
                    if(checkCollisionVaisseau(b[i]))
                        vaisseauExplode();
                }
            }
        }
    }
    /*Retourne le premier ennemi disponible a etre jouer*/
    public int findPlayable(Ennemi [] e)
    {
        int i = 0 ;
        while(! e[i].playable)
            i++;
        return i;
    }
    /*Retour la premiere balle disponible a etre jouée*/
    public int findPlayable(Balle [] b)
    {
        int i = 0 ;
        while(! b[i].playable)
        {
            i++;
            if(i == 99)
                return i;
        }
        return i;
    }
    public void checkCollisionBoss(Balle ba , Boss bo)
    {
        if(ba.sprite.collidesWith(bo.sprite, true))
        {
            bo.estTouché(vaisseau.puissance);
            ba.exitStage();
        }
    }
}