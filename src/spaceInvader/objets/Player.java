/**
 * Classe Player représentant le joueur dans le jeu Space Invaders.
 * Cette classe hérite de GraphicalObject et définit les caractéristiques graphiques
 * du vaisseau du joueur, ainsi que sa méthode d'affichage.
 *
 * @author Baptiste Borie
 */
package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Player extends GraphicalObject {

    /**
     * Constructeur de la classe Player.
     * Initialise la position, la rotation, l'échelle, la hauteur et la largeur du
     * vaisseau.
     *
     * @param pX     Position initiale en X.
     * @param pY     Position initiale en Y.
     * @param pZ     Position initiale en Z.
     * @param angX   Angle initial de rotation selon l'axe X.
     * @param angY   Angle initial de rotation selon l'axe Y.
     * @param angZ   Angle initial de rotation selon l'axe Z.
     * @param scale  Facteur d'échelle initial du vaisseau.
     * @param height Hauteur du vaisseau.
     * @param width  Largeur du vaisseau.
     */
    public Player(float pX, float pY, float pZ,
            float angX, float angY, float angZ,
            float scale, float height, float width) {
        super(pX, pY, pZ, angX, angY, angZ, scale, height, width);
    }

    /**
     * Affiche le vaisseau du joueur sous forme normalisée en utilisant OpenGL.
     * Le vaisseau est composé d'un corps central gris, de deux ailes rouges et d'un
     * cockpit bleu clair.
     *
     * @param gl Contexte OpenGL utilisé pour le rendu graphique.
     */
    @Override
    public void displayNormalized(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        // Corps central du vaisseau
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Gris
        gl.glVertex3f(-0.8f, -0.4f, 0.0f);
        gl.glVertex3f(0.8f, -0.4f, 0.0f);
        gl.glVertex3f(0.8f, 0.8f, 0.0f);
        gl.glVertex3f(-0.8f, 0.8f, 0.0f);

        // Aile gauche
        gl.glColor3f(0.7f, 0.0f, 0.0f); // Rouge
        gl.glVertex3f(-1.2f, -0.4f, 0.0f);
        gl.glVertex3f(-0.8f, -0.4f, 0.0f);
        gl.glVertex3f(-0.8f, 0.4f, 0.0f);
        gl.glVertex3f(-1.2f, 0.4f, 0.0f);

        // Aile droite
        gl.glColor3f(0.7f, 0.0f, 0.0f); // Rouge
        gl.glVertex3f(0.8f, -0.4f, 0.0f);
        gl.glVertex3f(1.2f, -0.4f, 0.0f);
        gl.glVertex3f(1.2f, 0.4f, 0.0f);
        gl.glVertex3f(0.8f, 0.4f, 0.0f);

        // Cockpit
        gl.glColor3f(0.0f, 0.5f, 0.7f); // Bleu clair
        gl.glVertex3f(-0.4f, 0.6f, 0.0f); // Réduire la hauteur de départ
        gl.glVertex3f(0.4f, 0.6f, 0.0f);
        gl.glVertex3f(0.4f, 1.0f, 0.0f); // Augmenter légèrement la hauteur finale
        gl.glVertex3f(-0.4f, 1.0f, 0.0f);

        gl.glEnd();
    }
}
