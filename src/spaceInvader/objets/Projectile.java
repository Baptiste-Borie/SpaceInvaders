package spaceInvader.objets;

import com.jogamp.opengl.GL2;

public class Projectile extends GraphicalObject {
    private float speed; // Vitesse du projectile

    public Projectile(float x, float y, float z, float speed, float height, float width) {
        super(x, y, z, 1.0f, 1.0f, 0.0f, 0.2f, height, width); // Couleur jaune par défaut
        this.speed = speed;
    }

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

    public void move() {
        this.translate(0.0f, -speed, 0.0f);
        this.setY(roundToDecimals(this.getY(), 2)); // Arrondir à 2 décimales
    }

    public static float roundToDecimals(float value, int decimals) {
        float scale = (float) Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }

}
