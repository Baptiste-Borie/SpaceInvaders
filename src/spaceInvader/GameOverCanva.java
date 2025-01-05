
package spaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe GameOverCanva représentant l'écran de fin de jeu (Game Over) dans
 * Space Invaders.
 * Cette classe affiche le score final, le nombre de vagues atteintes et propose
 * un bouton pour revenir au menu principal.
 *
 * @author Baptiste Borie
 */
public class GameOverCanva extends JPanel {

    /** Score final du joueur. */
    private int score;

    /** Nombre de vagues atteintes par le joueur. */
    private int waveNumber;

    /** Fenêtre parente contenant ce panneau. */
    private Fenetre parent;

    /** Image de fond de l'écran Game Over. */
    private Image backgroundImage;

    /**
     * Constructeur de la classe GameOverCanva.
     * Initialise l'écran de fin de jeu avec le score, le nombre de vagues
     * atteintes,
     * une image de fond et un bouton de retour au menu principal.
     *
     * @param score      Score final du joueur.
     * @param waveNumber Nombre de vagues atteintes.
     * @param parent     Fenêtre parente contenant ce panneau.
     */
    public GameOverCanva(int score, int waveNumber, Fenetre parent) {
        this.score = score;
        this.waveNumber = waveNumber;
        this.parent = parent;

        // Chargement de l'image de fond
        this.backgroundImage = new ImageIcon("assets/background.jpg").getImage();

        setLayout(new BorderLayout());

        // Affichage du message de fin
        JLabel gameOverLabel = new JLabel(
                "<html><h1>Game Over</h1><p>Score : " + score + "</p><p>Wave : " + waveNumber + "</p></html>",
                SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverLabel.setForeground(Color.WHITE); // Texte en blanc pour être visible sur l'image de fond
        add(gameOverLabel, BorderLayout.CENTER);

        // Bouton pour retourner au menu principal
        JButton mainMenuButton = new JButton("Retour au menu principal");
        mainMenuButton.addActionListener(e -> parent.launchMainMenu());
        add(mainMenuButton, BorderLayout.SOUTH);
    }

    /**
     * Surcharge de la méthode paintComponent pour dessiner l'image de fond.
     *
     * @param g Objet Graphics utilisé pour dessiner l'image de fond.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
