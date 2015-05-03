/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime Blaise
 */
public class Emetteur extends Thread {

    /**
     * Besoin pour envoyer un message.
     */
    MulticastSocket s;

    /**
     * Port du multicast.
     */
    int port;

    /**
     * L'équivalent de l'adresse IP.
     */
    InetAddress group;

    /**
     * Construction du thread emetteur.
     *
     * @param s
     * @param port
     * @param group
     */
    public Emetteur(MulticastSocket s, int port, InetAddress group) {
        super();
        this.port = port;
        this.s = s;
        this.group = group;
    }

    /**
     * Lancement.
     */
    @Override
    public void run() {
        try {
            while (true) {
                Scanner sc = new Scanner(System.in);
                //Création du message + envoi
                System.out.println("Message : ");
                String mess = sc.nextLine();

                DatagramPacket hi = new DatagramPacket(mess.getBytes(), mess.length(),
                        group, port);
                s.send(hi);

            }
        } catch (IOException ex) {
            Logger.getLogger(Emetteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
