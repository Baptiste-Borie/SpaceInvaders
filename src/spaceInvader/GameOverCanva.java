package spaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverCanva extends JPanel {

    private int score;
    private int waveNumber;
    private Fenetre parent;
    private Image backgroundImage;

    public GameOverCanva(int score, int waveNumber, Fenetre parent) {
        this.score = score;
        this.waveNumber = waveNumber;
        this.parent = parent;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
