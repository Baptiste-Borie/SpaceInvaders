package spaceInvader;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.glu.GLU;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Fenetre {

    private Canva canva;
    private JFrame frame;
    private Canva gameCanva; // L’écran de jeu
    private MainMenuCanva mainMenuCanva; // L’écran de menu principal
    private HelpCanva helpCanva;

    public static void main(String[] args) {
        new Fenetre();
    }

    public Fenetre() {
        this.frame = new JFrame("Space Invader");

        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width;
        int hauteur = tailleMoniteur.height;

        frame.setSize(longueur, hauteur);

        launchMainMenu();

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);

    }

    public void launchGameOverScreen(int score, int waveNumber) {
        SwingUtilities.invokeLater(() -> {
            this.gameCanva.stop();
            this.frame.getContentPane().remove(this.gameCanva);

            GameOverCanva gameOverCanva = new GameOverCanva(score, waveNumber, this);
            this.frame.getContentPane().add(gameOverCanva);
            this.frame.revalidate();
            this.frame.repaint();
        });
    }

    public void launchMainMenu() {
        if (this.gameCanva != null)
            this.gameCanva.stop();
        frame.getContentPane().removeAll();

        this.mainMenuCanva = new MainMenuCanva(this);
        frame.getContentPane().add(this.mainMenuCanva);
        frame.revalidate();
        frame.repaint();
    }

    public void launchGameScreen() {
        frame.getContentPane().removeAll();

        this.gameCanva = new Canva(this); // Canva correspond à l’écran de jeu avec OpenGL
        frame.getContentPane().add(this.gameCanva);
        frame.revalidate();
        frame.repaint();
    }

    public void launchHelpScreen() {
        frame.getContentPane().removeAll();

        this.helpCanva = new HelpCanva(this);
        frame.getContentPane().add(this.helpCanva);
        frame.revalidate();
        frame.repaint();
    }

}
