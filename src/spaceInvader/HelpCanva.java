package spaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe HelpCanva représentant l'écran d'aide du jeu Space Invaders.
 * Cette classe affiche des instructions pour le joueur ainsi qu'un bouton pour
 * revenir au menu principal.
 * Elle utilise un layout BorderLayout pour organiser les composants.
 *
 * @author Baptiste Borie
 */
public class HelpCanva extends JPanel {

    /** Fenêtre parente contenant ce panneau. */
    private Fenetre parent;

    /** Image de fond de l'écran d'aide. */
    private Image backgroundImage;

    /**
     * Constructeur de la classe HelpCanva.
     * Initialise l'écran d'aide avec un titre, des instructions et un bouton de
     * retour.
     *
     * @param parent Fenêtre parente contenant ce panneau.
     */
    public HelpCanva(Fenetre parent) {
        this.parent = parent;
        this.backgroundImage = new ImageIcon("assets/background.jpg").getImage();
        setLayout(new BorderLayout());

        // Label affichant les instructions d'aide
        JLabel helpLabel = new JLabel(
                "<html><h1>Aide</h1><p>Instructions pour jouer au jeu </p> <br /> <p> Vous pouvez déplacer le vaisseau avec ZQSD ou les fléches du clavier. Appuyer sur espace pour tirer.</p> <p>Si un ennemi vous touches ou si un enemi atteint le bas de l'écran alors c'est perdu ! </p></html>");
        add(helpLabel, BorderLayout.CENTER);

        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helpLabel.setVerticalAlignment(SwingConstants.CENTER);
        helpLabel.setFont(new Font("Arial", Font.BOLD, 20));
        helpLabel.setForeground(Color.WHITE);

        // Bouton permettant de revenir au menu principal
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> parent.launchMainMenu());
        add(backButton, BorderLayout.SOUTH);
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
