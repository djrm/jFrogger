/*----------------------------------------------------------------------
 *  NOMBRE: Frog.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 11 2011
 *  
 *  DESCRIPCION: clase para la rana
 *----------------------------------------------------------------------
 */

import java.awt.Color; // <- descartable
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.RenderingHints;

public class Frog extends Actor {
    public int dx, dy;             // distancia al origen <- descartable
    private int direction;         // direccion de la rana
    private boolean walking;       // false si esta estatico 

    public int score;              // puntuacion de la rana
    public boolean alive;          // true si esta vivo
    public byte lives;             // las vidas de la rana

    /* descartable si se usan imagenes */
    private Color color;           // color de la rana



    /** Constructor basico.  (y, x)  son las coordenadas en el tablero
        de inicio de la rana */
    public Frog(int y, int x, Color color) {
        this.x = Math.abs(x * 100);
        this.y = Math.abs(y * 100);
        this.dx = 0;
        this.dy = 0;

        this.color = color; // <- descartable
        this.width = 100;
        this.height = 100;
        this.walking = false;

        this.alive = true;
        this.score = 0;
        this.lives = 3;
        this.property = new String("player");

        this.direction = 90;
    }

    /** Constructor con tamaño.  (y, x)  son las coordenadas 
        de inicio de la rana, lado el tamaño de un lado de la rana */
    public Frog(int y, int x, int side, Color color) {
        this.x = Math.abs(x * side);
        this.y = Math.abs(y * side);
        this.dx = 0;
        this.dy = 0;

        this.color = color; // <- descartable
        this.width = Math.abs(side);
        this.height = Math.abs(side);
        this.walking = false;

        this.alive = true;
        this.score = 0;
        this.lives = 3;
        this.property = new String("player");

        this.direction = 90;
    }

    

    /*  --M E T O D O S--  */

    /** Dibuja a la rana */
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);

        /* frog */
        // temporalmente sera un rectangulo
        g2d.fillRect(this.x, this.y, this.width, this.height);
               
    }

    /** Accion para el movimiento de la rana */
    public void move() {
        this.x += this.dx * this.width;
        this.y += this.dy * this.height;

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.dx = -1;
            this.dy = 0;
            this.direction = 180;
        }
        else if (key == KeyEvent.VK_RIGHT) {
            this.dx = 1;
            this.dy = 0;
            this.direction = 0;
        }
        else if (key == KeyEvent.VK_DOWN) {
            this.dy = 1;
            this.dx = 0;
            this.direction = 270;
        }
        else if (key == KeyEvent.VK_UP) {
            this.dy = -1;
            this.dx = 0;
            this.direction = 90;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) 
            dx = 0;
        else if (key == KeyEvent.VK_RIGHT)
            dx = 0;
        else if (key == KeyEvent.VK_DOWN)
            dy = 0;
        else if (key == KeyEvent.VK_UP) 
            dy = 0;

    }
}