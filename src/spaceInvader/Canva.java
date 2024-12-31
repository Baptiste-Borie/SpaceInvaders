package spaceInvader;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.glu.GLU;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import spaceInvader.objets.*;

public class Canva extends GLCanvas implements GLEventListener, KeyListener {

    private static final float X_LIMIT = 17.0f; // Limite horizontale
    private static final float Y_LIMIT = 9.0f; // Limite verticale

    private Animator animator;
    private Cube player; // Le cube contrôlé par le joueur
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies; // Liste des ennemis
    private boolean[] keys; // Tableau pour enregistrer les touches pressées
    private Thread inputThread; // Thread pour gérer les entrées utilisateur
    private volatile boolean running; // Flag pour le thread

    private float enemyDirection = 0.1f; // Direction des ennemis (gauche-droite)

    public Canva() {
        super();
        this.projectiles = new ArrayList<>();
        this.enemies = new ArrayList<>(); // Initialiser la liste d'ennemis
        this.keys = new boolean[256]; // Tableau pour les touches (max 256 codes)
        this.addGLEventListener(this);
        this.addKeyListener(this); // Ajoute le KeyListener
        animator = new Animator(this);
        animator.start();

        // Initialise le cube avec les coordonnées et les paramètres nécessaires
        this.player = new Cube(0.0f, 0.0f, -15.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.5f); // Cube bleu

        // Initialisation des ennemis en grille
        initializeEnemies();

        // Démarre le thread de gestion des entrées
        this.running = true;
        this.inputThread = new Thread(this::processInput);
        this.inputThread.start();
    }

    private void initializeEnemies() {
        for (int i = 0; i < 1; i++) { // 3 colonnes
            for (int j = 0; j < 1; j++) { // 2 rangées
                // enemies.add(new Enemy(-8.0f + i * 2.0f, 5.0f - j * 1.5f, 0.0f, 0.4f));
                enemies.add(new Enemy(-0.0f + i * 2.0f, 3.0f - j * 1.5f, 0.0f, 0.4f));
            }
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        this.requestFocus(); // Nécessaire pour recevoir les événements
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(
                0.0f, 0.0f, 20.0f, // Position de la caméra
                0.0f, 0.0f, 0.0f, // Point regardé par la caméra
                0.0f, 1.0f, 0.0f // Vecteur "haut"
        );

        // Déplacement des ennemis
        // moveEnemies();

        // Gérer les collisions
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                System.out.println("Enemy position: X=" + enemy.getX() + " Y=" + enemy.getY() + " Z="
                        + enemy.getZ());

                if (enemy.isAlive() && enemy.isFullyContained(projectile)) {
                    System.out.println("Collision detected!");
                    // Supprimer l'ennemi et le projectile
                    enemyIterator.remove(); // Supprime l'ennemi
                    enemy.kill(); // Marque l'ennemi comme mort
                    projectileIterator.remove(); // Supprime le projectile
                    continue; // Passe au prochain projectile
                }
            }

            // Déplacer le projectile
            projectile.move();
        }

        // Supprimer les projectiles hors de la scène
        projectiles.removeIf(p -> p.getY() > Y_LIMIT || p.getY() < -Y_LIMIT);

        // Afficher les objets restants
        gl.glPushMatrix();
        gl.glTranslatef(player.getX(), player.getY(), player.getZ());
        player.display(gl);
        player.drawBoundingBox(gl);
        gl.glPopMatrix();

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                gl.glPushMatrix();
                gl.glTranslatef(enemy.getX(), enemy.getY(), enemy.getZ());
                enemy.display(gl);
                enemy.drawBoundingBox(gl);
                gl.glPopMatrix();
            }
        }

        for (Projectile projectile : projectiles) {
            gl.glPushMatrix();
            gl.glTranslatef(projectile.getX(), projectile.getY(), projectile.getZ());
            projectile.display(gl);
            projectile.drawBoundingBox(gl);
            gl.glPopMatrix();
        }
    }

    private void moveEnemies() {
        // Déplacer les ennemis horizontalement
        for (Enemy enemy : enemies) {
            enemy.move(enemyDirection, 0);
        }

        // Vérifier si un ennemi atteint une limite
        boolean changeDirection = false;
        for (Enemy enemy : enemies) {
            if (Math.abs(enemy.getX()) > X_LIMIT) {
                changeDirection = true;
                break;
            }
        }

        // Changer la direction et descendre d'un cran si nécessaire
        if (changeDirection) {
            enemyDirection = -enemyDirection; // Inverser la direction
            for (Enemy enemy : enemies) {
                enemy.move(0, -0.5f); // Descendre les ennemis
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        float aspect = (float) width / height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(30.0, aspect, 0.1, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        if (animator != null) {
            animator.stop();
        }
        this.running = false; // Arrête le thread de gestion des entrées
        try {
            this.inputThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shoot() {
        projectiles.add(new Projectile(player.getX(), player.getY(), player.getZ() - 1.0f, -0.2f));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Aucune action spécifique ici
    }

    private void processInput() {
        while (running) {
            float nextX = player.getX();
            float nextY = player.getY();

            if (keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_Z]) {
                nextY += 0.25f;
            }
            if (keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) {
                nextY -= 0.25f;
            }
            if (keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_Q]) {
                nextX -= 0.25f;
            }
            if (keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) {
                nextX += 0.25f;
            }

            if (Math.abs(nextX) <= X_LIMIT) {
                player.translate(nextX - player.getX(), 0.0f, 0.0f);
            }
            if (Math.abs(nextY) <= Y_LIMIT) {
                player.translate(0.0f, nextY - player.getY(), 0.0f);
            }

            if (keys[KeyEvent.VK_SPACE]) {
                this.shoot();
                keys[KeyEvent.VK_SPACE] = false;
            }

            this.repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
