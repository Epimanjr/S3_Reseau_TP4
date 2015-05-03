/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast;

//Tous les imports nécessaires.
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime Blaise
 */
public class Multicast {

    /**
     * Représente le port.
     */
    private final int port;

    /**
     * Ip.
     */
    private final String ip;

    //Scanner sc = new Scanner(System.in);
    /**
     * Créer le multicast en fonction du port et de l'IP.
     *
     * @param ip
     * @param port
     */
    public Multicast(String ip, String port) {
        this.ip = ip;
        this.port = new Integer(port);

        ecouter();
    }

    /**
     * Met le "multicast" en écoute sur le port donné en paramètre du
     * constructeur.
     */
    public void ecouter() {
        try {
            //Settings + rejoindre le groupe
            InetAddress group = InetAddress.getByName(ip);
            MulticastSocket s = new MulticastSocket(port);
            s.joinGroup(group);
            
            //Création de l'interface Graphique (une seule frame)
            FenetreGraphique fen = new FenetreGraphique(s, port, group);

            //Emetteur thread
            Emetteur em = new Emetteur(s, port, group);
            em.start();

            //Recepteur thread
           /* Recepteur re = new Recepteur(s);
            re.start();*/

            // On quitte le groupe
            //s.leaveGroup(group);
            //System.out.println(getColor(91) + "\nMulticast lancé ! " + getColor(0));
        } catch (IOException ex) {
            Logger.getLogger(Multicast.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Permet de récupérer le numéro du port sur lequel écoute le serveur.
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * Méthode principale, créer et lance le serveur en écoute.
     *
     * @param args
     */
    public static void main(String[] args) {
        //Test sur le nombre d'arguments.
      /*  if (args.length < 2) {
            System.out.println("Il manque des arguments !");
        } else {
            //On considère que l'IP et le port sont bien rentrés par l'utilisateur.
            Multicast multi = new Multicast(args[0], args[1]);
        }*/
        
        
        Multicast multi = new Multicast("225.0.0.1", "2000");
    }

    /**
     * Permet de colorer le terminal Linux, non utilisé dans ce projet.
     *
     * @param i
     * @return
     */
    public static String getColor(int i) {
        return "\033[" + i + "m";
    }
}
