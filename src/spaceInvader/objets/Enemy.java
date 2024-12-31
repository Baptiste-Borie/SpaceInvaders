package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Enemy extends GraphicalObject {
    private boolean isAlive;

    public Enemy(float pX, float pY, float pZ, float scale) {
        super(pX, pY, pZ, 0, 0, 0, 1.0f, 0.0f, 0.0f, scale); // Rouge par défaut
        this.isAlive = true;
    }

    @Override
    public void displayNormalized(GL2 gl) {
        if (isAlive) {
            gl.glBegin(GL2.GL_TRIANGLES);

            // Base de la pyramide (rouge clair)
            gl.glColor3f(0.3f, 0.0f, 0.0f); // Rouge clair
            gl.glVertex3f(-0.8f, -0.8f, -0.8f);
            gl.glVertex3f(0.8f, -0.8f, -0.8f);
            gl.glVertex3f(0.8f, -0.8f, 0.8f);

            gl.glVertex3f(-0.8f, -0.8f, -0.8f);
            gl.glVertex3f(-0.8f, -0.8f, 0.8f);
            gl.glVertex3f(0.8f, -0.8f, 0.8f);

            // Face 0.8rouge foncé)
            gl.glColor3f(0.8f, 0.0f, 0.0f); // Rouge foncé
            gl.glVertex3f(0.0f, 0.8f, 0.0f); // Sommet
            gl.glVertex3f(-0.8f, -0.8f, -0.8f); // Coin arrière gauche
            gl.glVertex3f(0.8f, -0.8f, -0.8f); // Coin arrière droit

            // Face 2 (rouge clair)
            gl.glColor3f(0.8f, 0.0f, 0.0f); // Rouge clair
            gl.glVertex3f(0.0f, 0.8f, 0.0f); // Sommet
            gl.glVertex3f(0.8f, -0.8f, -0.8f); // Coin arrière droit
            gl.glVertex3f(0.8f, -0.8f, 0.8f); // Coin avant droit

            // Face 3 (rouge foncé)
            gl.glColor3f(0.6f, 0.0f, 0.0f); // Rouge foncé
            gl.glVertex3f(0.0f, 0.8f, 0.0f); // Sommet
            gl.glVertex3f(0.8f, -0.8f, 0.8f); // Coin avant droit
            gl.glVertex3f(-0.8f, -0.8f, 0.8f); // Coin avant gauche

            // Face 4 (rouge clair)
            gl.glColor3f(0.8f, 0.0f, 0.0f); // Rouge clair
            gl.glVertex3f(0.0f, 0.8f, 0.0f); // Sommet
            gl.glVertex3f(-0.8f, -0.8f, 0.8f); // Coin avant gauche
            gl.glVertex3f(-0.8f, -0.8f, -0.8f); // Coin arrière gauche

            gl.glEnd();
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        this.isAlive = false; // Marque l'ennemi comme mort
    }

    public void move(float dx, float dy) {
        this.translate(dx, dy, 0); // Déplacer l'ennemi dans le plan XY
    }

    public boolean isFullyContained(Projectile projectile) {
        float enemyHalfSize = 0.4f; // Taille de l'ennemi
        float projectileHalfSize = 0.2f; // Taille du projectile

        // Coordonnées des bords du projectile
        float projectileMinX = projectile.getX() - projectileHalfSize;
        float projectileMaxX = projectile.getX() + projectileHalfSize;
        float projectileMinY = projectile.getY() - projectileHalfSize;
        float projectileMaxY = projectile.getY() + projectileHalfSize;

        // Coordonnées des bords de l'ennemi
        float enemyMinX = this.getX() - enemyHalfSize;
        float enemyMaxX = this.getX() + enemyHalfSize;
        float enemyMinY = this.getY() - enemyHalfSize;
        float enemyMaxY = this.getY() + enemyHalfSize;

        // Vérifier si la boîte du projectile est entièrement contenue dans celle de
        // l'ennemi
        return (projectileMinX >= enemyMinX && projectileMaxX <= enemyMaxX &&
                projectileMinY >= enemyMinY && projectileMaxY <= enemyMaxY);
    }

}
