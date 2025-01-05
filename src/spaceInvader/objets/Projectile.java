/**
 * Classe Projectile représentant un projectile dans le jeu Space Invaders.
 * Cette classe hérite de GraphicalObject et gère l'affichage et le déplacement des projectiles.
 *
 * @author Baptiste Borie
 */
package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Projectile extends GraphicalObject {

    /**
     * Vitesse du projectile.
     */
    private float speed;

    /**
     * Constructeur de la classe Projectile.
     * Initialise la position, la vitesse, la hauteur et la largeur du projectile.
     *
     * @param x      Position initiale en X.
     * @param y      Position initiale en Y.
     * @param z      Position initiale en Z.
     * @param speed  Vitesse du projectile.
     * @param height Hauteur du projectile.
     * @param width  Largeur du projectile.
     */
    public Projectile(float x, float y, float z, float speed, float height, float width) {
        super(x, y, z, 1.0f, 1.0f, 0.0f, 0.2f, height, width); // Couleur jaune par défaut
        this.speed = speed;
    }

    /**
     * Affiche le projectile sous forme normalisée en utilisant OpenGL.
     * Le projectile est représenté par un petit rectangle jaune.
     *
     * @param gl Contexte OpenGL utilisé pour le rendu graphique.
     */
    @Override
    public void displayNormalized(GL2 gl) {
        gl.glColor3f(1.0f, 1.0f, 0.0f); // Couleur jaune
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.4f, -0.4f, 0.0f);
        gl.glVertex3f(0.4f, -0.4f, 0.0f);
        gl.glVertex3f(0.4f, 0.4f, 0.0f);
        gl.glVertex3f(-0.4f, 0.4f, 0.0f);
        gl.glEnd();
    }

    /**
     * Déplace le projectile selon sa vitesse.
     * Le déplacement se fait uniquement sur l'axe Y.
     * La position Y est arrondie à 2 décimales après chaque déplacement.
     */
    public void move() {
        this.translate(0.0f, -speed, 0.0f);
        this.setY(roundToDecimals(this.getY(), 2)); // Arrondir à 2 décimales
    }

    /**
     * Arrondit une valeur flottante à un nombre donné de décimales.
     *
     * @param value    Valeur à arrondir.
     * @param decimals Nombre de décimales souhaité.
     * @return Valeur arrondie.
     */
    public static float roundToDecimals(float value, int decimals) {
        float scale = (float) Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }

}
