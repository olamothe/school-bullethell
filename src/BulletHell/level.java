/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BulletHell;

import FXGameEngine.FXGameCanvas;

/**
 *
 * @author Olivier Lamothe
 */
public class level
{
    private final int BGMOVESPEED = 150;
    private int bgmovespeed;
    private FXGameCanvas c;
    private long timeElapsed;
    private int noLevel;
    public String fichierMusique;

    //=========================== LEVEL 1 ======================================
    private int [] tempsSpawnLevel1= {2,10,20,30,40,50,60,75};
    private int [][] typeEnnemiLevel1 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                                        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                                        {3,3,3,3,3,3,3,3,3},
                                        {4,4,4},
                                        {5,5,5,5,5,5,5,5,5,5},
                                        {4,5,5,5,5,4,4,5,5,5,5,4,4,5,5,5,5,4},
                                        {1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1},
                                        {6}};
    private int [][] posXEnnemiLevel1 = {{130,160,190,220,25,55,85,115,145,175,205,40,70,100,130,160,190,220},
                                         {30,50,70,110,130,120,140,160,180,200,30,50,70,110,130},
                                         {30,50,70,110,130,150,170,190,210},
                                         {70,150,200},
                                         {30,50,70,110,130,120,140,160,180,200},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190} , {20}};
    private int [][] posYEnnemiLevel1 = {{-10,-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30,-50,-50,-50,-50,-50},
                                        {-10,-15,-20,-25,-30,-35,-40,-45,-50},
                                        {-10,-10,-10},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30},{-150}};
    private double [][] vitXLevel1 = {{0},{0},{0},{0},{0},{0},{0},{0}};
    private double [][] vitYLevel1 = {{.5},{.5},{.5},{.5},{.3},{.5},{.6},{.6}};


    //=========================== LEVEL 2 ======================================
    private int [] tempsSpawnLevel2= {2,10,20,30,40,50,60,75};
    private int [][] typeEnnemiLevel2 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                                        {2,2,2,2,2,2,2,2,2},
                                        {3,3,3,3,3,3,3,3,3},
                                        {4,4,4,4,4,4,4,4},
                                        {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
                                        {4,5,5,5,5,4,4,5,5,5,5,4,4,5,5,5,5,4},
                                        {1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1},
                                        {7}};
    private int [][] posXEnnemiLevel2 = {{40,70,100,130,160,190,220,25,55,85,115,145,175,205,40,70,100,130,160,190,220},
                                         {80,100,120,140,160,80,100,140,160},
                                         {80,100,120,140,160,80,100,140,160},
                                         {30,90,150,220,20,80,140,210},
                                         {80,100,120,140,160,80,80,80,80,80,160,160,160,160,160,80,100,120,140,160},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190} , {20}};
    private int [][] posYEnnemiLevel2 = {{-10,-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30,-30},
                                        {-70,-60,-50,-40,-30,-30,-40,-60,-70},
                                        {-70,-60,-50,-40,-30,-30,-40,-60,-70},
                                        {-10,-10,-10,-10,-20,-20,-20,-20},
                                        {-80,-80,-80,-80,-80,-30,-40,-50,-60,-70,-30,-40,-50,-60,-70,-20,-20,-20,-20,-20},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30},{-150}};
    private double [][] vitXLevel2 = {{0},{0},{0},{0},{0},{0},{0},{0}};
    private double [][] vitYLevel2 = {{.5},{.5},{.5},{.5},{.3},{.5},{.6},{.6}};

    //=========================== LEVEL 3 ======================================

    private int [] tempsSpawnLevel3= {2,10,20,30,40,50,60,75};
    private int [][] typeEnnemiLevel3 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                                        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                                        {3,3,3,3,3,3,3,3,3},
                                        {4,4,4},
                                        {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1,1,1,1},
                                        {4,5,5,5,5,4,4,5,5,5,5,4,4,5,5,5,5,4},
                                        {1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1},
                                        {8}};
    private int [][] posXEnnemiLevel3 = {{40,70,100,130,160,190,220,25,55,85,115,145,175,205,40,70,100,130,160,190,220},
                                         {30,50,70,110,130,120,140,160,180,200,30,50,70,110,130},
                                         {30,50,70,110,130,150,170,190,210},
                                         {70,100,130},
                                         {80,100,120,140,160,80,80,80,80,80,160,160,160,160,160,80,100,120,140,160,100,120,140,100,140},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190} ,
                                         {20}};
    private int [][] posYEnnemiLevel3 = {{-10,-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30,-50,-50,-50,-50,-50},
                                        {-10,-15,-20,-25,-30,-35,-40,-45,-50},
                                        {-10,-10,-10},
                                        {-80,-80,-80,-80,-80,-30,-40,-50,-60,-70,-30,-40,-50,-60,-70,-20,-20,-20,-20,-20,-60,-50,-40,-40,-60},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30},{-150}};
    private double [][] vitXLevel3 = {{0},{0},{0},{0},{0},{0},{0},{0}};
    private double [][] vitYLevel3 = {{.5},{.6},{.7},{.8},{.3},{.5},{.6},{.6}};

    //=========================== LEVEL 4 ======================================

    private int [] tempsSpawnLevel4= {2,10,20,30,40,50,60,75};

    private int [][] typeEnnemiLevel4 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                                        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                                        {3,3,3,3,3,3,3,3,3,3},
                                        {4,4,4,4},
                                        {5,5,5,5,5,5,5,5,5,5},
                                        {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1,1,1,1},
                                        {1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1},
                                        {9}};
    private int [][] posXEnnemiLevel4 = {{30,50,70,110,130,120,140,160,180,200,30,50,70,110,130},
                                         {30,50,70,110,130,120,140,160,180,200,30,50,70,110,130},
                                         {30,50,70,110,130,120,140,160,180,200},
                                         {30,50,70,110},
                                         {30,50,70,110,130,120,140,160,180,200},
                                         {80,100,120,140,160,80,80,80,80,80,160,160,160,160,160,80,100,120,140,160,100,120,140,100,140},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190}};
    private int [][] posYEnnemiLevel4 = {{-10,-10,-10,-10,-10,-30,-30,-30,-30,-30,-50,-50,-50,-50,-50},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30,-50,-50,-50,-50,-50},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-80,-80,-80,-80,-80,-30,-40,-50,-60,-70,-30,-40,-50,-60,-70,-20,-20,-20,-20,-20,-60,-50,-40,-40,-60},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30}};
    private double [][] vitXLevel4 = {{0},{0},{0},{0},{0},{0},{0}};
    private double [][] vitYLevel4 = {{.5},{.5},{.5},{.5},{.5},{.3},{.5}};

    //=========================== LEVEL 5 ======================================

    private int [] tempsSpawnLevel5= {2,10,20,30,40,50,60,75};

    private int [][] typeEnnemiLevel5 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2},
                                        {2,2,2,2,2,2,2,2,2},
                                        {3,3,3,3,3,3,3,3,3,3},
                                        {2,2,2,2,2,2,2,2,2},
                                        {5,5,5,5,5,5,5,5,5,5},
                                        {1,2,3,4,5,1,2,3,4,5},
                                        {1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1},
                                        {10}};
    private int [][] posXEnnemiLevel5 = {{80,100,120,140,160,80,80,80,80,80,160,160,160,160,160,80,100,120,140,160,100,120,140,100,140},
                                         {80,100,120,140,160,80,100,140,160},
                                         {30,50,70,110,130,120,140,160,180,200},
                                         {80,100,120,140,160,80,100,140,160},
                                         {30,50,70,110,130,120,140,160,180,200},
                                         {30,50,70,110,130,120,140,160,180,200},
                                         {20,230,40,210,60,190,20,230,40,210,60,190,20,230,40,210,60,190}};
    private int [][] posYEnnemiLevel5 = {{-80,-80,-80,-80,-80,-30,-40,-50,-60,-70,-30,-40,-50,-60,-70,-20,-20,-20,-20,-20,-60,-50,-40,-40,-60},
                                        {-70,-60,-50,-40,-30,-30,-40,-60,-70},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-30,-30,-30,-30,-30},
                                        {-10,-10,-10,-10,-10,-10,-20,-20,-20,-20,-20,-20,-30,-30,-30,-30,-30,-30}};
    private double [][] vitXLevel5 = {{0},{0},{0},{0},{0},{0},{0}};
    private double [][] vitYLevel5 = {{.3},{.5},{.5},{.5},{.5},{.5},{.5}};



    public level (int noLevel , FXGameCanvas c)
    {
        this.noLevel = noLevel;
        String imgFile="";
        String txtFile="";
        this.c=c;
        switch(noLevel)
        {
            case 1:
                txtFile="/background/bg1.txt";
                imgFile ="/background/bg1-4-5.png";
                fichierMusique="/son/ct_lavos_battle1.mid";
                break;
            case 2:
                txtFile="/background/bg2.txt";
                imgFile="/background/bg2-3.png";
                fichierMusique="/son/ct_bike_chase1.mid";
                break;
            case 3:

                txtFile="/background/bg3.txt";
                imgFile="/background/bg2-3.png";
                fichierMusique="/son/ct_battle3.mid";
                break;
            case 4:
                txtFile="/background/bg4.txt";
                imgFile="/background/bg1-4-5.png";
                fichierMusique="/son/ct_ayla.mid";
                break;
            case 5:
                txtFile="/background/bg5.txt";
                imgFile="/background/bg1-4-5.png";
                fichierMusique="/son/ct_final_battle2.mid";
                break;    
        }
        
        c.setBackground(imgFile,txtFile, 15, 50, 16, 16);
        c.moveBG(0, -480);
        bgmovespeed= BGMOVESPEED;
        timeElapsed = 0;
    }
    public void update(long deltaTime)
    {
        bgmovespeed-=deltaTime;
        if(bgmovespeed<0 &&c.getBGY() < 0)
        {
            bgmovespeed = BGMOVESPEED;
            c.moveBG(0, 1);
        }
        timeElapsed+= deltaTime;
    }
    public int getSpawn()
    {
        if(noLevel == 1)
        {
            for(int i = 0 ; i <tempsSpawnLevel1.length; i++ )
            {
                if(tempsSpawnLevel1[i]*1000<=timeElapsed)
                {
                    tempsSpawnLevel1[i] = 9999;
                    return i;
                }
            }
        }
        else if (noLevel ==2)
        {
            for(int i = 0 ; i <tempsSpawnLevel2.length; i++ )
            {
                if(tempsSpawnLevel2[i]*1000<=timeElapsed)
                {
                    tempsSpawnLevel2[i] = 9999;
                    return i;
                }
            }
        }
        else if (noLevel ==3)
        {
            for(int i = 0 ; i <tempsSpawnLevel3.length; i++ )
            {
                if(tempsSpawnLevel3[i]*1000<=timeElapsed)
                {
                    tempsSpawnLevel3[i] = 9999;
                    return i;
                }
            }
        }
        else if (noLevel ==4)
        {
            for(int i = 0 ; i <tempsSpawnLevel4.length; i++ )
            {
                if(tempsSpawnLevel4[i]*1000<=timeElapsed)
                {
                    tempsSpawnLevel4[i] = 9999;
                    return i;
                }
            }
        }
        else if (noLevel ==5)
        {
            for(int i = 0 ; i <tempsSpawnLevel5.length; i++ )
            {
                if(tempsSpawnLevel5[i]*1000<=timeElapsed)
                {
                    tempsSpawnLevel5[i] = 9999;
                    return i;
                }
            }
        }

        return -1;
    }
    public int[][] getType()
    {
        if(noLevel == 1)
        {
            return typeEnnemiLevel1;
        }
        if(noLevel == 2)
        {
            return typeEnnemiLevel2;
        }
        if(noLevel == 3)
        {
            return typeEnnemiLevel3;
        }
        if(noLevel == 4)
        {
            return typeEnnemiLevel4;
        }
        if(noLevel == 5)
        {
            return typeEnnemiLevel5;
        }
        return null;
    }
    public int [][] getPosX()
    {
        if(noLevel == 1)
            return posXEnnemiLevel1;
         if(noLevel == 2)
            return posXEnnemiLevel2;
         if(noLevel == 3)
            return posXEnnemiLevel3;
         if(noLevel == 4)
            return posXEnnemiLevel4;
         if(noLevel == 5)
            return posXEnnemiLevel5;
        return null;
    }
    public int [][] getPosY()
    {
        if (noLevel ==1)
            return posYEnnemiLevel1;
        if (noLevel ==2)
            return posYEnnemiLevel2;
        if (noLevel ==3)
            return posYEnnemiLevel3;
        if (noLevel ==4)
            return posYEnnemiLevel4;
        if (noLevel ==5)
            return posYEnnemiLevel5;
        return null;
    }
    public double [][] getVX()
    {
        if (noLevel == 1)
            return vitXLevel1;
         if (noLevel == 2)
            return vitXLevel2;
         if (noLevel == 3)
            return vitXLevel3;
         if (noLevel == 4)
            return vitXLevel4;
         if (noLevel == 5)
            return vitXLevel5;
        return null;
    }
    public double [][] getVY()
    {
        if(noLevel ==1)
            return vitYLevel1;
        if(noLevel ==2)
            return vitYLevel2;
        if(noLevel ==3)
            return vitYLevel3;
        if(noLevel ==4)
            return vitYLevel4;
        if(noLevel ==5)
            return vitYLevel5;
        return null;
    }

}