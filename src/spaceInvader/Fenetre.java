package spaceInvader;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.glu.GLU;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;

public class Fenetre {

    public static void main(String[] args) {
        new Fenetre();
    }

    public Fenetre() {
        JFrame frame = new JFrame("Space Invader");

        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width;
        int hauteur = tailleMoniteur.height;

        frame.setSize(longueur, hauteur);

        Canva canva = new Canva();

        frame.getContentPane().add(canva);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
