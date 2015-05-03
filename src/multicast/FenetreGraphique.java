/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multicast;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Maxime Blaise
 */
public class FenetreGraphique extends JFrame {
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
     * Panel principal.
     */
    JPanel panPrincipal = new JPanel();
    /**
     * Panel du haut, qui contient le champ de saisie.
     */
    JPanel panHaut = new JPanel();
    
    /**
     * Panel du centre, qui contient les messages.
     */
    JPanel panCenter = new JPanel();
    
    /**
     * Label qui se charge d'afficher les messages
     */
    JLabel labelRes = new JLabel("<html><p>");
    
    /**
     * Constructeur de la fenêtre graphique.
     * @param s
     * @param port
     * @param group
     */
    public FenetreGraphique(final MulticastSocket s, final int port, final InetAddress group) {
        super();
        this.s = s;
        this.port = port;
        this.group = group;
        
        //Panel haut, champ de saisie + ajout KeyListener
        final JTextField saisieMessage = new JTextField(15);
        saisieMessage.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        //Quand on appuie sur entrer
                        String mess = saisieMessage.getText();
                        DatagramPacket hi = new DatagramPacket(mess.getBytes(), mess.length(),
                                group, port);
                        s.send(hi);
                    } catch (IOException ex) {
                        Logger.getLogger(FenetreGraphique.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        //Ajout au panel
        panHaut.add(saisieMessage);
        
        JButton buttonOk = new JButton("Envoyer");
        buttonOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                        //Quand on appuie sur entrer
                        String mess = saisieMessage.getText();
                        DatagramPacket hi = new DatagramPacket(mess.getBytes(), mess.length(),
                                group, port);
                        s.send(hi);
                    } catch (IOException ex) {
                        Logger.getLogger(FenetreGraphique.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
        panHaut.add(buttonOk);
        
        
        panCenter.add(labelRes);
        
        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.add(panHaut, BorderLayout.NORTH);
        panPrincipal.add(panCenter, BorderLayout.CENTER);
        
        //Settings()
        this.setTitle("Multicast");
        
        this.setContentPane(panPrincipal);
        this.setPreferredSize(new Dimension(400,200));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Recepteur thread
           Recepteur re = new Recepteur(s, labelRes);
            re.start();
        
        this.setVisible(true);
    }
}
