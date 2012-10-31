/*----------------------------------------------------------------------
 *  NOMBRE: Turtles.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Fri Oct 21 2011
 *  
 *  DESCRIPCION: clase para las tortugas.
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Turtles extends Actor implements ImageObserver {
    private Image img;
    private int cycle;
    private boolean decreassing;
    private boolean trap;      // 'true' si se oculta

    private static Image up = 
        new ImageIcon("img/TurtleUp.png").getImage();
    private static Image mid = 
        new ImageIcon("img/TurtleMid.png").getImage();
    private static Image down = 
        new ImageIcon("img/water.png").getImage();


    public Turtles(int y, int x, int side, int turtles, int direction,
                   int speed, boolean trap) {

        this.x = x * side;
        this.y = y * side;
        this.height = side;
        this.width = turtles * side;
        this.direction = direction;
        this.speed = speed;
        this.property = new String("dynamic");
        this.trap = trap;
        this.decreassing = false;
        this.cycle = 0;
        this.img = up;
    }



    /*  --M E T O D O S--  */

    public void draw(Graphics2D g2d) {
        if (this.trap) {
            if (!this.decreassing)
                this.cycle++;
            else 
                this.cycle--;

            // posicion de las tortugas
            if (this.cycle == 0) {
                this.img = up;
                this.decreassing = false;
                this.property = new String("dynamic");
            }
            if (this.cycle == 15) {
                this.img = mid;
                this.property = new String("dynamic");
            }
            else if (this.cycle == 30) {
                this.img = down;
                this.property = new String("enemy");
                this.decreassing = true;
            }

        }                
        for (int i = this.x; i < this.x + this.width; i += this.height)
            g2d.drawImage(this.img, i, this.y, this);
    }
}