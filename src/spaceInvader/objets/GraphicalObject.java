package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public abstract class GraphicalObject {
    private float posX, posY, posZ;
    private float angX, angY, angZ;
    private float r, g, b;
    private float scale;
    private float height, width;

    public GraphicalObject(float pX, float pY, float pZ,
            float angX, float angY, float angZ,
            float scale, float height, float width) {
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

    public float getScale() {
        return scale;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
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