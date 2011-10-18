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
    public int x, y;               // coordenadas
    public int dx, dy;             // distancia al origen
    private int direction;         // direccion de la rana
    private boolean walking;       // false si esta estatico 

    /* descartable si se usan imagenes */
    private Color color;           // color de la rana


    /** Constructor basico.  (x, y)  son las coordenadas 
        de inicio de la rana */
    public Frog(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;

        this.color = color; // <- descartable
        this.ancho = 100;
        this.alto = 100;
        this.walking = false;

        this.direction = 90;
    }

    /** Constructor con tamaño.  (x, y)  son las coordenadas 
        de inicio de la rana, lado el tamaño de un lado de la rana */
    public Frog(int x, int y, int lado, Color color) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;

        this.color = color; // <- descartable
        this.ancho = Math.abs(lado);
        this.alto = Math.abs(lado);
        this.walking = false;

        this.direction = 90;
    }

    

    /*  --M E T O D O S--  */
    /** Dibuja a la rana */
    public void live(Graphics2D graph) {
        graph.setColor(this.color);

        /* frog */
    }

    /** Accion para el movimiento de la rana */
    public void move() {
        this.x += this.dx;
        this.y += this.dy;


    }

    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.dx = -1;
            this.direction = 180;
        }
        else if (key == KeyEvent.VK_RIGHT) {
            this.dx = 1;
            this.direction = 0;
        }
        else if (key == KeyEvent.VK_DOWN) {
            this.dy = 1;
            this.direction = 270;
        }
        else if (key == KeyEvent.VK_UP) {
            this.dy = -1;
            this.direction = 90;
        }
    }

    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();

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