/*----------------------------------------------------------------------
 *  NOMBRE: Truck.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Fri Oct 21 2011
 *  
 *  DESCRIPCION: camion
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Truck extends Actor implements ImageObserver {
    private int direction;      // -1: left , 1: right
    private int speed;          // velocidad.
    private Image img;

    private static Image truck1 = 
        new ImageIcon("img/Truck1.png").getImage();


    public Truck(int y, int x, int side, int direction, int speed) {
        this.x = x * side;
        this.y = y * side;
        this.width = side * 2;
        this.height = side;
        this.direction = direction;
        this.speed = speed;
        this.property = new String("enemy");
        this.img = Truck.truck1;
    }


    /*  --M E T O D O S--  */

    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x, this.y, this);
    }

    public void move() {
        if (this.direction == -1)
            this.x -= speed;
        else
            this.x += speed;
    }

    public boolean imageUpdate(Image img, int infoFlags, int x, int y,
                               int width, int height) {
        return true;
    }
}
