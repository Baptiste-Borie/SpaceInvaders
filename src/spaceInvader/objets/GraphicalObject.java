package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public abstract class GraphicalObject {
    private float posX, posY, posZ;
    private float angX, angY, angZ;
    private float r, g, b;
    private float scale;

    public GraphicalObject(float pX, float pY, float pZ,
            float angX, float angY, float angZ,
            float r, float g, float b,
            float scale) {
        this.posX = pX;
        this.posY = pY;
        this.posZ = pZ;
        this.angX = angX;
        this.angY = angY;
        this.angZ = angZ;
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;

    }

    public abstract void displayNormalized(GL2 gl);

    public void display(GL2 gl) {
        gl.glPushMatrix();
        gl.glScalef(this.scale, this.scale, this.scale);
        gl.glColor3f(this.r, this.g, this.b);
        this.displayNormalized(gl);
        gl.glPopMatrix();
    }

    public void rotate(float aX, float aY, float aZ) {
        this.angX += aX;
        this.angY += aY;
        this.angZ += aZ;
    }

    public void translate(float pX, float pY, float pZ) {
        this.posX += pX;
        this.posY += pY;
        this.posZ += pZ;
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public float getZ() {
        return posZ;
    }

    public void setX(float posX) {
        this.posX = posX;
    }

    public void setY(float posY) {
        this.posY = posY;
    }

    public void setZ(float posZ) {
        this.posZ = posZ;
    }

    public void drawBoundingBox(GL2 gl) {
        // Taille de la boîte englobante

        gl.glColor3f(0.0f, 1.0f, 0.0f); // Vert pour la boîte
        gl.glBegin(GL2.GL_LINES);

        float boxSize = 0.4f;
        // Face avant
        gl.glVertex3f(-boxSize, -boxSize, 0.0f);
        gl.glVertex3f(boxSize, -boxSize, 0.0f);

        gl.glVertex3f(boxSize, -boxSize, 0.0f);
        gl.glVertex3f(boxSize, boxSize, 0.0f);

        gl.glVertex3f(boxSize, boxSize, 0.0f);
        gl.glVertex3f(-boxSize, boxSize, 0.0f);

        gl.glVertex3f(-boxSize, boxSize, 0.0f);
        gl.glVertex3f(-boxSize, -boxSize, 0.0f);

        // Ajout pour dessiner les bords verticaux si en 3D
        gl.glVertex3f(-boxSize, -boxSize, 0.0f);
        gl.glVertex3f(-boxSize, -boxSize, 0.5f);

        gl.glVertex3f(boxSize, -boxSize, 0.0f);
        gl.glVertex3f(boxSize, -boxSize, 0.5f);

        gl.glVertex3f(boxSize, boxSize, 0.0f);
        gl.glVertex3f(boxSize, boxSize, 0.5f);

        gl.glVertex3f(-boxSize, boxSize, 0.0f);
        gl.glVertex3f(-boxSize, boxSize, 0.5f);

        // Face arrière
        gl.glVertex3f(-boxSize, -boxSize, 0.5f);
        gl.glVertex3f(boxSize, -boxSize, 0.5f);

        gl.glVertex3f(boxSize, -boxSize, 0.5f);
        gl.glVertex3f(boxSize, boxSize, 0.5f);

        gl.glVertex3f(boxSize, boxSize, 0.5f);
        gl.glVertex3f(-boxSize, boxSize, 0.5f);

        gl.glVertex3f(-boxSize, boxSize, 0.5f);
        gl.glVertex3f(-boxSize, -boxSize, 0.5f);

        gl.glEnd();
    }

}