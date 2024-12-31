package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Cube extends GraphicalObject {

    public Cube(float pX, float pY, float pZ,
            float angX, float angY, float angZ,
            float r, float g, float b,
            float scale) {
        super(pX, pY, pZ, angX, angY, angZ, r, g, b, scale);
    }

    @Override
    public void displayNormalized(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        // Face arrière (z = -1.0)
        gl.glColor3f(0.0f, 0.0f, 0.5f); // Bleu foncé
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);

        // Face avant (z = 1.0)
        gl.glColor3f(0.0f, 0.0f, 0.7f); // Bleu un peu plus clair
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);

        // Face gauche (x = -1.0)
        gl.glColor3f(0.0f, 0.0f, 0.6f); // Bleu intermédiaire
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);

        // Face droite (x = 1.0)
        gl.glColor3f(0.0f, 0.0f, 0.8f); // Bleu plus clair
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        // Face du bas (y = -1.0)
        gl.glColor3f(0.0f, 0.0f, 0.4f); // Bleu très foncé
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);

        // Face du haut (y = 1.0)
        gl.glColor3f(0.0f, 0.0f, 0.9f); // Bleu très clair
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);

        gl.glEnd();
    }

}
