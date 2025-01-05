/**
 * Classe abstraite GraphicalObject représentant un objet graphique dans le jeu Space Invaders.
 * Cette classe définit les propriétés de position, de rotation, d'échelle et de couleur,
 * ainsi que les méthodes de base pour afficher et manipuler un objet graphique.
 *
 * @author Baptiste Borie
 */
package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public abstract class GraphicalObject {

    /** Position en X de l'objet graphique. */
    private float posX;

    /** Position en Y de l'objet graphique. */
    private float posY;

    /** Position en Z de l'objet graphique. */
    private float posZ;

    /** Angle de rotation selon l'axe X. */
    private float angX;

    /** Angle de rotation selon l'axe Y. */
    private float angY;

    /** Angle de rotation selon l'axe Z. */
    private float angZ;

    /** Composante rouge de la couleur de l'objet graphique. */
    private float r;

    /** Composante verte de la couleur de l'objet graphique. */
    private float g;

    /** Composante bleue de la couleur de l'objet graphique. */
    private float b;

    /** Facteur d'échelle de l'objet graphique. */
    private float scale;

    /** Hauteur de l'objet graphique. */
    private float height;

    /** Largeur de l'objet graphique. */
    private float width;

    /**
     * Constructeur de la classe GraphicalObject.
     * Initialise les paramètres de position, de rotation, d'échelle, de hauteur et
     * de largeur de l'objet.
     *
     * @param pX     Position initiale en X.
     * @param pY     Position initiale en Y.
     * @param pZ     Position initiale en Z.
     * @param angX   Angle initial de rotation selon l'axe X.
     * @param angY   Angle initial de rotation selon l'axe Y.
     * @param angZ   Angle initial de rotation selon l'axe Z.
     * @param scale  Facteur d'échelle initial de l'objet.
     * @param height Hauteur de l'objet.
     * @param width  Largeur de l'objet.
     */
    public GraphicalObject(float pX, float pY, float pZ, float angX, float angY, float angZ, float scale, float height,
            float width) {
        this.posX = pX;
        this.posY = pY;
        this.posZ = pZ;
        this.angX = angX;
        this.angY = angY;
        this.angZ = angZ;
        this.scale = scale;
        this.height = height;
        this.width = width;
    }

    /**
     * Affiche l'objet sous forme normalisée.
     * Cette méthode doit être implémentée par les classes dérivées.
     *
     * @param gl Contexte OpenGL utilisé pour le rendu graphique.
     */
    public abstract void displayNormalized(GL2 gl);

    /**
     * Affiche l'objet avec ses transformations appliquées.
     *
     * @param gl Contexte OpenGL utilisé pour le rendu graphique.
     */
    public void display(GL2 gl) {
        gl.glPushMatrix();
        gl.glScalef(this.scale, this.scale, this.scale);
        gl.glColor3f(this.r, this.g, this.b);
        this.displayNormalized(gl);
        gl.glPopMatrix();
    }

    /**
     * Applique une rotation à l'objet.
     *
     * @param aX Angle de rotation à ajouter selon l'axe X.
     * @param aY Angle de rotation à ajouter selon l'axe Y.
     * @param aZ Angle de rotation à ajouter selon l'axe Z.
     */
    public void rotate(float aX, float aY, float aZ) {
        this.angX += aX;
        this.angY += aY;
        this.angZ += aZ;
    }

    /**
     * Translate l'objet selon les axes X, Y et Z.
     *
     * @param pX Décalage selon l'axe X.
     * @param pY Décalage selon l'axe Y.
     * @param pZ Décalage selon l'axe Z.
     */
    public void translate(float pX, float pY, float pZ) {
        this.posX += pX;
        this.posY += pY;
        this.posZ += pZ;
    }

    /**
     * Renvoie la position en X de l'objet.
     *
     * @return Position en X.
     */
    public float getX() {
        return posX;
    }

    /**
     * Renvoie la position en Y de l'objet.
     *
     * @return Position en Y.
     */
    public float getY() {
        return posY;
    }

    /**
     * Renvoie la position en Z de l'objet.
     *
     * @return Position en Z.
     */
    public float getZ() {
        return posZ;
    }

    /**
     * Renvoie le facteur d'échelle de l'objet.
     *
     * @return Facteur d'échelle.
     */
    public float getScale() {
        return scale;
    }

    /**
     * Renvoie la hauteur de l'objet.
     *
     * @return Hauteur de l'objet.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Renvoie la largeur de l'objet.
     *
     * @return Largeur de l'objet.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Met à jour la position en X de l'objet.
     *
     * @param posX Nouvelle position en X.
     */
    public void setX(float posX) {
        this.posX = posX;
    }

    /**
     * Met à jour la position en Y de l'objet.
     *
     * @param posY Nouvelle position en Y.
     */
    public void setY(float posY) {
        this.posY = posY;
    }

    /**
     * Met à jour la position en Z de l'objet.
     *
     * @param posZ Nouvelle position en Z.
     */
    public void setZ(float posZ) {
        this.posZ = posZ;
    }

    /**
     * Dessine une boîte englobante autour de l'objet.
     * La boîte est dessinée sous forme de lignes jaunes.
     *
     * @param gl Contexte OpenGL utilisé pour le rendu graphique.
     */
    public void drawBoundingBox(GL2 gl) {
        float halfScale = this.getScale();

        gl.glColor3f(1.0f, 1.0f, 0.0f); // Couleur jaune pour la boîte
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex3f(-halfScale, -halfScale, 0);
        gl.glVertex3f(halfScale, -halfScale, 0);
        gl.glVertex3f(halfScale, halfScale, 0);
        gl.glVertex3f(-halfScale, halfScale, 0);
        gl.glEnd();
    }
}
