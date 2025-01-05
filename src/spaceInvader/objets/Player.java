package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Player extends GraphicalObject {

    public Player(float pX, float pY, float pZ,
            float angX, float angY, float angZ,
            float r, float g, float b,
            float scale) {
        super(pX, pY, pZ, angX, angY, angZ, r, g, b, scale);
    }

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
