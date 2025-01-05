/**
 * Classe Enemy représentant un ennemi dans le jeu Space Invaders.
 * Cette classe hérite de GraphicalObject et définit les caractéristiques
 * graphiques d'un ennemi, ainsi que ses comportements de déplacement et d'état.
 * L'ennemi est dessiné en utilisant OpenGL.
 *
 * @author Baptiste Borie
 */
package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Enemy extends GraphicalObject {

    /**
     * Indique si l'ennemi est vivant.
     */
    private boolean isAlive;

    /**
     * Constructeur de la classe Enemy.
     * Initialise la position, l'échelle, la hauteur et la largeur de l'ennemi,
     * et le marque comme vivant par défaut.
     *
     * @param pX     Position initiale en X.
     * @param pY     Position initiale en Y.
     * @param pZ     Position initiale en Z.
     * @param scale  Facteur d'échelle de l'ennemi.
     * @param height Hauteur de l'ennemi.
     * @param width  Largeur de l'ennemi.
     */
    public Enemy(float pX, float pY, float pZ, float scale, float height, float width) {
        super(pX, pY, pZ, 0.0f, 1.0f, 0.0f, scale, height, width); // Vert par défaut
        this.isAlive = true;
    }

    /**
     * Affiche l'ennemi sous forme normalisée en utilisant OpenGL.
     * Si l'ennemi est vivant, il dessine le corps principal, les yeux, les antennes
     * et les pieds de l'ennemi.
     *
     * @param gl Contexte OpenGL utilisé pour le rendu graphique.
     */
    @Override
    public void displayNormalized(GL2 gl) {
        if (isAlive) {
            // Début de la création de l'alien
            gl.glBegin(GL2.GL_QUADS);

            // Couleur principale : vert clair
            gl.glColor3f(0.1f, 0.8f, 0.1f);

            // Corps principal (centre)
            gl.glVertex3f(-0.8f, -0.8f, 0.0f);
            gl.glVertex3f(0.8f, -0.8f, 0.0f);
            gl.glVertex3f(0.8f, 0.8f, 0.0f);
            gl.glVertex3f(-0.8f, 0.8f, 0.0f);

            // Yeux (deux carrés orange)
            gl.glColor3f(1.0f, 0.5f, 0.0f); // Orange
            gl.glVertex3f(-0.4f, 0.2f, 0.02f);
            gl.glVertex3f(-0.2f, 0.2f, 0.02f);
            gl.glVertex3f(-0.2f, 0.4f, 0.02f);
            gl.glVertex3f(-0.4f, 0.4f, 0.02f);

            gl.glVertex3f(0.1f, 0.1f, 0.01f);
            gl.glVertex3f(0.4f, 0.2f, 0.02f);
            gl.glVertex3f(0.4f, 0.4f, 0.02f);
            gl.glVertex3f(0.2f, 0.4f, 0.02f);

            // Antennes (deux rectangles verts foncés)
            gl.glColor3f(0.0f, 0.6f, 0.0f);
            gl.glVertex3f(-0.6f, 0.8f, 0.0f);
            gl.glVertex3f(-0.4f, 0.8f, 0.0f);
            gl.glVertex3f(-0.4f, 1.0f, 0.0f);
            gl.glVertex3f(-0.6f, 1.0f, 0.0f);

            gl.glVertex3f(0.4f, 0.8f, 0.0f);
            gl.glVertex3f(0.6f, 0.8f, 0.0f);
            gl.glVertex3f(0.6f, 1.0f, 0.0f);
            gl.glVertex3f(0.4f, 1.0f, 0.0f);

            // Pieds (deux rectangles verts foncés)
            gl.glVertex3f(-0.6f, -1.0f, 0.0f);
            gl.glVertex3f(-0.2f, -1.0f, 0.0f);
            gl.glVertex3f(-0.2f, -0.8f, 0.0f);
            gl.glVertex3f(-0.6f, -0.8f, 0.0f);

            gl.glVertex3f(0.2f, -1.0f, 0.0f);
            gl.glVertex3f(0.6f, -1.0f, 0.0f);
            gl.glVertex3f(0.6f, -0.8f, 0.0f);
            gl.glVertex3f(0.2f, -0.8f, 0.0f);

            gl.glEnd();
        }
    }

    /**
     * Renvoie l'état actuel de l'ennemi.
     *
     * @return true si l'ennemi est vivant, false sinon.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Marque l'ennemi comme mort.
     */
    public void kill() {
        this.isAlive = false;
    }

    /**
     * Déplace l'ennemi selon les décalages donnés.
     *
     * @param dx Décalage selon l'axe X.
     * @param dy Décalage selon l'axe Y.
     */
    public void move(float dx, float dy) {
        this.translate(dx, dy, 0); // Déplacer l'ennemi dans le plan XY
    }
}
