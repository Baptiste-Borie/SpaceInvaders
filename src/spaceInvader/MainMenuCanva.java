package spaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuCanva extends JPanel {

    private Fenetre parent;
    private Image backgroundImage;

    public MainMenuCanva(Fenetre parent) {
        this.parent = parent;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}
