package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Enemy extends GraphicalObject {

    private boolean isAlive;

    public Enemy(float pX, float pY, float pZ, float scale, float height, float width) {
        super(pX, pY, pZ, 0.0f, 1.0f, 0.0f, scale, height, width); // Vert par défaut
        this.isAlive = true;
    }

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

    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        this.isAlive = false; // Marque l'ennemi comme mort
    }

    public void move(float dx, float dy) {
        this.translate(dx, dy, 0); // Déplacer l'ennemi dans le plan XY
    }
}
