/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author blaise
 */
public class Recepteur extends Thread {

    /**
     * MulticastSocket, besoin pour reçevoir un message.
     */
    MulticastSocket s;
    
    JLabel label;

    /**
     * Constructeur du thread recepteur.
     *
     * @param s
     * @param label
     */
    public Recepteur(MulticastSocket s, JLabel label) {
        
        super();
        this.label = label;
        this.s = s;
    }

    /**
     * Lancement du recepteur
     */
    @Override
    public void run() {
        try {
            
            while (true) {
                // En attente d'une réponse ...
                byte[] buf = new byte[50];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                s.receive(dp);
                // Affichage de la réponse
                //System.out.println("Recu : " + new String(buf));
				
				
				String ss = label.getText();
                label.setText(ss + "<br/><span style=\"color: red;\">Recu : </span>" + new String(buf));
            }
        } catch (IOException ex) {
            Logger.getLogger(Recepteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
