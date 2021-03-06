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


import javax.microedition.rms.*;
import java.io.*;

/**
 * Classe FXGameProfile
 * Cette classe contient toute l'information globale du jeu.
 * On y retrouve, entre autre, les niveaux de son (volumes),
 * le nom du joueur, le niveau (level) actuel.
 * 
 * Utilisez cette classe comme exemple (template) pour la
 * sauvegarde d'informations dans le RecordStore.
 *
 * @author jfboily
 * 
 */
public class FXGameProfile
{
    /**
     * Volume des effets sonores.
     */
    public static int sfxVolume;
    /**
     * Volume de la musique.
     */
    public static int musicVolume;
    /**
     * Nom du joueur.
     */
    public static String playerName;
    /**
     * Niveau actuel de la partie.
     */
    public static int level;
    /**
     * Indique si on est dans une nouvelle partie.
     */
    public static boolean newGame;

    /**
     * Nom du RecordStore.
     */
    private static String rsName = "Exemple12345";

    /**
     * Le RecordStore.
     */
    private static RecordStore rs;

    /**
     * Ouvre le RecordStore. Si celui-ci n'existe pas, il est créé.
     * @return vrai si le RecordStore existait préalablement.
     */
    public static boolean openRS()
    {
        boolean exist = false;
        try
        {
            rs = RecordStore.openRecordStore(rsName, true);
            if(rs.getNextRecordID() == 1)
            {
                exist = false;
                createRS();
            }
            else
            {
                exist = true;
            }
        }
        catch (RecordStoreException ex)
        {
            System.err.println("*** ERREUR OUVERTURE RS ***");
            System.err.println(ex.getMessage());
        }

        return exist;
    }

    /**
     * Ferme le RecordStore.
     */
    public static void closeRS()
    {
        try
        {
            rs.closeRecordStore();
        }
        catch(RecordStoreException ex)
        {
            System.err.println("ERREUR closeRS : " + ex.getMessage());
        }
    }

    /**
     * Lis le data du RecordStore.
     * <p><b>Vous devez personnaliser cette méthode selon vos besoins.</b>
     */
    public static void readData()
    {
        try
        {
            ByteArrayInputStream bin;
            DataInputStream din;

            byte[] data = rs.getRecord(2);

            if(data != null)
            {
                bin = new ByteArrayInputStream( data );
                din = new DataInputStream( bin );
                playerName = din.readUTF();
            }
            else
            {
                playerName = "default";
            }
        }
        catch(RecordStoreException ex)
        {
            System.err.println("**** RS/ERREUR ****");
            System.err.println(ex.getMessage());
        }
        catch(IOException ioex)
        {
            System.err.println("IO Exception (Read): " + ioex.getMessage());
        }

    }

    /**
     * Enregistre le data dans le RecordStore.
     * <p><b>Vous devez personnaliser cette méthode selon vos besoins.</b>
     */
    public static void saveData()
    {
        try
        {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream( bout );

            dout.writeUTF(FXGameProfile.playerName);


            byte[] data = bout.toByteArray();
            rs.setRecord(2, data, 0, data.length);
        }
        catch(RecordStoreException ex)
        {
            System.err.println("**** RS/ERREUR ****");
            System.err.println(ex.getMessage());
        }
        catch(IOException ioex)
        {
            System.err.println("IO Exception (Write): " + ioex.getMessage());
        }
    }

    /**
     * Crée un nouveau RecordStore. Alloue automatiquement les 100 premiers
     * Records.
     */
    private static void createRS()
    {
        try
        {
            // on alloue 100 Records
            for(int i = 0; i < 100; i++)
            {
                rs.addRecord(null, 0, 0);
            }
        }
        catch (RecordStoreException ex)
        {
            System.err.println("ECHEC de createRS.");
            System.err.println(ex.getMessage());
        }
    }


}
