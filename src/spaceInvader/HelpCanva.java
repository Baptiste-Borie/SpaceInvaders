package spaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpCanva extends JPanel {

    private Fenetre parent;

    public HelpCanva(Fenetre parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel helpLabel = new JLabel("<html><h1>Aide</h1><p>Instructions pour jouer au jeu...</p></html>");
        add(helpLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> parent.launchMainMenu());
        add(backButton, BorderLayout.SOUTH);
    }
}
