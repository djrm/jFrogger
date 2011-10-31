/*----------------------------------------------------------------------
 *  NOMBRE: Racer.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Fri Oct 21 2011
 *  
 *  DESCRIPCION: Clase para un auto de carreras.
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Racer extends Actor implements ImageObserver {
    private Image img;

    private static Image car1 = 
        new ImageIcon("img/Racer1.png").getImage();
    private static Image car2 = 
        new ImageIcon("img/Racer2.png").getImage();


    public Racer(int y, int x, int side, int direction, 
                 int speed, String color) {
        this.x = x * side;
        this.y = y * side;
        this.width = side;
        this.height = side;
        this.direction = direction;
        this.speed = speed;
        this.property = new String("enemy");

        if (color.equals("red")) {
            img = Racer.car1;
        }
        else if (color.equals("blue")) {
            img = Racer.car2;
        }
        else 
            img = Racer.car1;
    }


/*=========================  M E T O D O S  =========================*/

    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x, this.y, this);
    }
}
