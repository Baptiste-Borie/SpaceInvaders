package spaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe MainMenuCanva représentant le menu principal du jeu Space Invaders.
 * Cette classe affiche un fond d'écran et propose trois boutons : Jouer, Aide
 * et Quitter.
 * Elle utilise un layout GridBagLayout pour organiser les boutons.
 *
 * @author Baptiste Borie
 */
public class MainMenuCanva extends JPanel {

    /** Fenêtre parente contenant ce panneau. */
    private Fenetre parent;

    /** Image de fond du menu principal. */
    private Image backgroundImage;

    /**
     * Constructeur de la classe MainMenuCanva.
     * Initialise le menu principal avec une image de fond et trois boutons : Jouer,
     * Aide et Quitter.
     *
     * @param parent Fenêtre parente contenant ce panneau.
     */
    public MainMenuCanva(Fenetre parent) {
        this.parent = parent;

        // Chargement de l'image de fond
        this.backgroundImage = new ImageIcon("assets/background.jpg").getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Bouton pour lancer le jeu
        JButton playButton = new JButton("Jouer");
        playButton.addActionListener(e -> parent.launchGameScreen());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playButton, gbc);

        // Bouton pour afficher l'aide
        JButton helpButton = new JButton("Aide");
        helpButton.addActionListener(e -> parent.launchHelpScreen());
        gbc.gridy = 1;
        add(helpButton, gbc);

        // Bouton pour quitter
        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 2;
        add(quitButton, gbc);
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
