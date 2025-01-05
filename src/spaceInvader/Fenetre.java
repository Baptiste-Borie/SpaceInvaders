package spaceInvader;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.glu.GLU;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Classe Fenetre représentant la fenêtre principale du jeu Space Invaders.
 * Cette classe gère l'affichage et la navigation entre les différents écrans du
 * jeu :
 * menu principal, aide, écran de jeu et écran de fin de jeu.
 *
 * @author Baptiste Borie
 */
public class Fenetre {

    /** Instance de Canva représentant l'écran de jeu avec OpenGL. */
    private Canva canva;

    /** Fenêtre principale de l'application. */
    private JFrame frame;

    /** Instance de Canva pour l'écran de jeu. */
    private Canva gameCanva;

    /** Instance de MainMenuCanva pour l'écran du menu principal. */
    private MainMenuCanva mainMenuCanva;

    /** Instance de HelpCanva pour l'écran d'aide. */
    private HelpCanva helpCanva;

    /**
     * Point d'entrée principal de l'application.
     *
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        new Fenetre();
    }

    /**
     * Constructeur de la classe Fenetre.
     * Initialise la fenêtre principale et affiche le menu principal par défaut.
     */
    public Fenetre() {
        this.frame = new JFrame("Space Invader");

        // Définir la taille de la fenêtre à celle de l'écran
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width;
        int hauteur = tailleMoniteur.height;

        frame.setSize(longueur, hauteur);

        // Lancer le menu principal
        launchMainMenu();

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    /**
     * Affiche l'écran de fin de jeu avec le score et le nombre de vagues atteintes.
     *
     * @param score      Score final du joueur.
     * @param waveNumber Nombre de vagues atteintes par le joueur.
     */
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

    /**
     * Affiche le menu principal.
     * Si l'écran de jeu est actif, il est arrêté avant d'afficher le menu
     * principal.
     */
    public void launchMainMenu() {
        if (this.gameCanva != null)
            this.gameCanva.stop();
        frame.getContentPane().removeAll();

        this.mainMenuCanva = new MainMenuCanva(this);
        frame.getContentPane().add(this.mainMenuCanva);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Affiche l'écran de jeu.
     * L'écran de jeu utilise la classe Canva pour le rendu OpenGL.
     */
    public void launchGameScreen() {
        frame.getContentPane().removeAll();

        this.gameCanva = new Canva(this); // Canva correspond à l'écran de jeu avec OpenGL
        frame.getContentPane().add(this.gameCanva);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Affiche l'écran d'aide.
     */
    public void launchHelpScreen() {
        frame.getContentPane().removeAll();

        this.helpCanva = new HelpCanva(this);
        frame.getContentPane().add(this.helpCanva);
        frame.revalidate();
        frame.repaint();
    }

}
