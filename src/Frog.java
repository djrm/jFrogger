/*----------------------------------------------------------------------
 *  NOMBRE: Frog.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 11 2011
 *  
 *  DESCRIPCION: clase para la rana
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;


public class Frog extends Actor implements ImageObserver {
    public int dx, dy;             // distancia al origen
    public int score;              // puntuacion de la rana
    public boolean alive;          // true si esta vivo
    public byte lives;             // las vidas de la rana
    private Image img;             // imagen para la rana

    private static Image frogStatic =
        new ImageIcon("img/Frog-static.png").getImage();
    private static Image frogJumping =
        new ImageIcon("img/Frog-jumping.png").getImage();
    private static Image frogDead =
        new ImageIcon("img/Frog-dead.png").getImage();
    

    /** Constructor con tamaño.  (y, x)  son las coordenadas 
        de inicio de la rana, lado el tamaño de un lado de la rana */
    public Frog(int y, int x, int side) {
        this.x = Math.abs(x * side);
        this.y = Math.abs(y * side);
        this.dx = 0;
        this.dy = 0;
        this.width = Math.abs(side);
        this.height = Math.abs(side);
        this.alive = true;
        this.score = 0;
        this.lives = 3;
        this.property = new String("player");
        this.direction = 90;
        this.speed = 0;
        this.img = frogStatic;
    }
   


    /*  --M E T O D O S--  */

    /** Dibuja a la rana */
    public void draw(Graphics2D g2d) {
        AffineTransform backup = g2d.getTransform(); // respaldo
        AffineTransform at = g2d.getTransform(); // temporal

        if (!this.alive) {
            img = frogDead;
        }        
        else {
            /* mueve los puntos de la transformacion a (x , y)
             * de la rana, y gira la direccion sobre el punto central.
             */
            at.translate(this.x, this.y);
            at.rotate(Math.toRadians(this.direction - 90),
                      (this.width / 2), 
                      (this.height / 2));
        }

        g2d.drawImage(this.img, at, this);
        g2d.setTransform(backup);
    }

    /** Accion para el movimiento de la rana */
    public void move() {
        this.x += this.dx * this.width;
        this.y += this.dy * this.height;
        
        this.x += this.speed;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.dx = -1;
            this.dy = 0;
            this.direction = 0; // por que asi sirve
        }
        else if (key == KeyEvent.VK_RIGHT) {
            this.dx = 1;
            this.dy = 0;
            this.direction = 180; // por que asi sirve
        }
        else if (key == KeyEvent.VK_DOWN) {
            this.dy = 1;
            this.dx = 0;
            this.speed = 0;
            this.direction = 270;
        }
        else if (key == KeyEvent.VK_UP) {
            this.dy = -1;
            this.dx = 0;
            this.speed = 0;
            this.direction = 90;
        }

        this.img = frogJumping;
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

        this.img = frogStatic;
    }
}
