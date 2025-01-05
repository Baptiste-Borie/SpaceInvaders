package spaceInvader;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import spaceInvader.objets.*;

/**
 * Classe Canva
 * 
 * Cette classe est la classe principal de l'application. Elle gère la logique
 * du jeu et l'affichage graphique. Elle hérite de GLCanvas et implémente les
 * interfaces GLEventListener et KeyListener.
 */
public class Canva extends GLCanvas implements GLEventListener, KeyListener {

    /** Référence à la fenêtre principale du jeu. */
    private Fenetre frame;

    /** Limite horizontale de la scène. */
    private static final float X_LIMIT = 10.0f;

    /** Limite verticale de la scène. */
    private static final float Y_LIMIT = 5.0f;

    /** Nombre maximum de rangées d'ennemis affichées. */
    private static final int MAX_ROWS = 5;

    /** Objet Animator pour animer la scène. */
    private Animator animator;

    /** Joueur contrôlé par l'utilisateur. */
    private Player player;

    /** Liste des projectiles tirés par le joueur. */
    private ArrayList<Projectile> projectiles;

    /** Liste des ennemis présents dans la scène. */
    private ArrayList<Enemy> enemies;

    /** Tableau d'état des touches pressées. */
    private boolean[] keys;

    /** Thread dédié à la gestion des entrées clavier. */
    private Thread inputThread;

    /** Indicateur de l'état d'exécution du thread. */
    private volatile boolean running;

    /** Direction des ennemis sur l'axe X. */
    private float enemyDirection = 0.1f;

    /** Compteur du nombre d'ennemis tués. */
    private int enemiesKilled = 0;

    /** Numéro de la vague actuelle d'ennemis. */
    private int waveNumber = 1;

    /** Numéro de vague utilisé pour l'affichage (ne diminue pas). */
    private int waveNumberLabel = 1;

    /**
     * Constructeur de la classe Canva.
     * Initialise la scène, le joueur, les ennemis et démarre l'animation.
     *
     * @param frame Référence à la fenêtre principale.
     */
    public Canva(Fenetre frame) {
        super();
        this.frame = frame;
        this.projectiles = new ArrayList<>();
        this.enemies = new ArrayList<>(); // Initialiser la liste d'ennemis
        this.keys = new boolean[256]; // Tableau pour les touches (max 256 codes)
        this.addGLEventListener(this);
        this.addKeyListener(this); // Ajoute le KeyListener
        animator = new Animator(this);
        animator.start();

        // Initialise le Player avec les coordonnées et les paramètres nécessaires
        this.player = new Player(
                0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                0.4f,
                1.0f, 1.0f);
        // Initialisation des ennemis en grille
        initializeEnemies();

        // Démarre le thread de gestion des entrées
        this.running = true;
        this.inputThread = new Thread(this::processInput);
        this.inputThread.start();
    }

    /**
     * Méthode d'initialisation de la scène.
     * 
     * @param drawable L'objet GLAutoDrawable.
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        this.requestFocus(); // Nécessaire pour recevoir les événements
    }

    /**
     * Méthode d'affichage de la scène.
     * 
     * @param drawable L'objet GLAutoDrawable.
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        GLUT glut = new GLUT();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(
                0.0f, 2.5f, 20.0f, // Position de la caméra
                0.0f, 0.0f, 0.0f, // Point regardé par la caméra
                0.0f, 1.0f, 0.0f // Vecteur "haut"
        );

        moveEnemies();
        updateProjectiles();
        checkCollisions();

        projectiles.removeIf(p -> p.getY() > Y_LIMIT || p.getY() < -Y_LIMIT);

        gl.glPushMatrix();
        gl.glTranslatef(player.getX(), player.getY(), player.getZ());
        player.display(gl);
        gl.glPopMatrix();

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                gl.glPushMatrix();
                gl.glTranslatef(enemy.getX(), enemy.getY(), enemy.getZ());
                enemy.display(gl);
                gl.glPopMatrix();
            }
        }

        for (Projectile projectile : projectiles) {
            gl.glPushMatrix();
            gl.glTranslatef(projectile.getX(), projectile.getY(), projectile.getZ());
            projectile.display(gl);
            gl.glPopMatrix();
        }

        // Dessiner l'interface utilisateur (HUD)
        drawHUD(gl, glut);
    }

    /**
     * Méthode de redimensionnement de la scène.
     * 
     * @param drawable L'objet GLAutoDrawable.
     * @param x        Position en X.
     * @param y        Position en Y.
     * @param width    Largeur.
     * @param height   Hauteur.
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        float aspect = (float) width / height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(30.0, aspect, 1.0, 50.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * Méthode de libération des ressources.
     * 
     * @param drawable L'objet GLAutoDrawable.
     */
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

    /*
     * ----------------------------------
     * 
     * 
     * Game logic enemies methods
     * 
     * ----------------------------------
     */

    /**
     * Méthode d'initialisation des ennemis.
     * Crée une grille d'ennemis en fonction du numéro de vague.
     */
    private void initializeEnemies() {
        float startX = -2.0f; // Position de départ en X
        float startY = 4.0f; // Position de départ en Y
        float spacingX = 1.5f; // Espacement entre les ennemis en X
        float spacingY = 1.0f; // Espacement entre les rangées en Y

        int rows = waveNumber; // Le nombre de rangées dépend de la vague
        for (int j = 0; j < rows; j++) {
            int enemiesInRow = rows - j;
            float rowStartX = startX - (enemiesInRow - 1) * spacingX / 2;

            for (int i = 0; i < enemiesInRow; i++) {
                float x = rowStartX + i * spacingX;
                float y = startY - j * spacingY;
                enemies.add(new Enemy(x, y, 0.0f, 0.4f, 0.8f, 0.8f));
            }
        }
    }

    /**
     * Méthode pour charger la vague suivante d'ennemis.
     * Réinitialise la liste des ennemis et augmente la difficulté.
     * Si le nombre de rangées atteint le maximum, il est réinitialisé.
     */
    private void nextWave() {
        enemies.clear(); // Supprimer les ennemis existants

        // Si le nombre de rangées atteint le maximum, on le réinitialise
        if (waveNumber >= MAX_ROWS) {
            waveNumber = 1; // Remet à 1 le nombre de rangées
            enemyDirection += 0.05f; // Augmente la vitesse des ennemis
        } else {
            waveNumber++; // Passe à la vague suivante en augmentant le nombre de rangées
            waveNumberLabel++;
        }

        initializeEnemies(); // Réinitialise les ennemis avec la nouvelle vague
    }

    /**
     * Méthode pour déplacer les ennemis.
     * Les ennemis se déplacent horizontalement et descendent lorsqu'ils atteignent
     * les limites de la scène.
     */
    private void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.move(enemyDirection, 0);
        }

        boolean changeDirection = false;
        for (Enemy enemy : enemies) {
            if (Math.abs(enemy.getX()) > (X_LIMIT - 1.0f)) {
                changeDirection = true;
                break;
            }
        }

        if (changeDirection) {
            enemyDirection = -enemyDirection;
            for (Enemy enemy : enemies) {
                enemy.move(0, -0.5f);
            }
        }
    }

    /**
     * Méthode pour tirer un projectile.
     */
    public void shoot() {
        projectiles.add(new Projectile(player.getX(), player.getY(), player.getZ() - 1.0f, -0.2f, 0.8f, 0.8f));
    }

    /*
     * ----------------------------------
     * 
     * 
     * Game collision methods
     * 
     * ----------------------------------
     */

    /**
     * Méthode pour mettre à jour les projectiles.
     * Les projectiles se déplacent vers le haut de la scène.
     */
    private void updateProjectiles() {
        for (Projectile projectile : projectiles) {
            projectile.move();
        }
    }

    /**
     * Méthode pour vérifier si deux objets graphiques sont en collision.
     * 
     * @param obj1 Le premier objet graphique.
     * @param obj2 Le deuxième objet graphique.
     * @return true si les objets sont en collision, false sinon.
     */
    private boolean isColliding(GraphicalObject obj1, GraphicalObject obj2) {
        float halfWidth1 = obj1.getWidth() / 2;
        float halfHeight1 = obj1.getHeight() / 2;
        float halfWidth2 = obj2.getWidth() / 2;
        float halfHeight2 = obj2.getHeight() / 2;

        float distanceX = Math.abs(obj1.getX() - obj2.getX());
        float distanceY = Math.abs(obj1.getY() - obj2.getY());

        return distanceX < (halfWidth1 + halfWidth2) &&
                distanceY < (halfHeight1 + halfHeight2);
    }

    /**
     * Méthode pour vérifier les collisions entre les projectiles et les ennemis.
     * Si un projectile touche un ennemi, l'ennemi est marqué comme mort.
     * Si tous les ennemis sont morts, la vague suivante est chargée.
     * Si un ennemi atteint le joueur, le jeu est terminé.
     */
    private void checkCollisions() {
        Iterator<Projectile> projectileIterator = projectiles.iterator();

        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();

                if (enemy.isAlive() && isColliding(projectile, enemy)) {
                    enemy.kill(); // Marquer l'ennemi comme mort
                    projectileIterator.remove(); // Supprimer le projectile
                    enemiesKilled++; // Incrémenter le compteur d'ennemis tués
                    break; // Passer au projectile suivant
                }
            }
        }

        // Vérifier si tous les ennemis sont morts
        if (enemies.stream().noneMatch(Enemy::isAlive)) {
            nextWave(); // Charger la vague suivante
        }

        checkGameOver();

    }

    /*
     * ----------------------------------
     * 
     * 
     * Key listener methods
     * 
     * ----------------------------------
     */

    /**
     * Méthode pour gérer les événements de pression de touche.
     * 
     * @param e L'événement de pression de touche.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    /**
     * Méthode pour gérer les événements de relâchement de touche.
     * 
     * @param e L'événement de relâchement de touche.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    /**
     * Méthode pour gérer les événements de frappe de touche.
     * 
     * @param e L'événement de frappe de touche.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Aucune action spécifique ici
    }

    /**
     * Méthode pour traiter les entrées utilisateur.
     * Gère les déplacements du joueur et les tirs.
     */
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

    /*
     * ----------------------------------
     * 
     * Stop the game
     * 
     * ----------------------------------
     */

    /**
     * Méthode pour vérifier si le jeu est terminé.
     * Le jeu est terminé si un ennemi atteint le joueur ou si un ennemi touche le
     * bas de l'écran.
     */
    private void checkGameOver() {
        for (Enemy enemy : enemies) {
            if (enemy.getY() <= -Y_LIMIT || isColliding(player, enemy)) {
                running = false; // Arrête le thread de gestion des entrées
                animator.stop(); // Arrête l’animation
                frame.launchGameOverScreen(enemiesKilled, waveNumberLabel); // Affiche l'écran de fin de jeu
                return;
            }
        }
    }

    /**
     * Méthode pour arrêter le jeu.
     * Arrête le thread de gestion des entrées et l'animation.
     */
    public void stop() {
        running = false; // Arrête le thread d’entrée utilisateur
        if (animator != null && animator.isStarted()) {
            animator.stop(); // Arrête l’animation proprement
        }
        try {
            inputThread.join(); // Attendre la fin du thread d’entrée
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * 
     * 
     * HUD methods
     * 
     * 
     */

    /**
     * Méthode pour dessiner l'interface utilisateur (HUD).
     * Affiche le nombre d'ennemis tués et le numéro de la vague.
     * 
     * @param gl   Objet GL2.
     * @param glut Objet GLUT.
     */
    private void drawHUD(GL2 gl, GLUT glut) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(0, 800, 0, 600, -1, 1); // Configure une projection orthogonale
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glColor3f(1.0f, 1.0f, 1.0f); // Couleur blanche

        // Ennemis tués
        String scoreText = "Ennemis tués : " + enemiesKilled;
        gl.glRasterPos2f(10, 580); // Position du texte
        for (char c : scoreText.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, c);
        }

        // Numéro de vague
        String waveText = "Vague : " + waveNumberLabel;
        gl.glRasterPos2f(10, 550); // Position du texte
        for (char c : waveText.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, c);
        }

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPopMatrix();
    }

}
