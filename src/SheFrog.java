/*----------------------------------------------------------------------
 *  NOMBRE: SheFrog.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 25 2011
 *  
 *  DESCRIPCION: rana hembra.
 *----------------------------------------------------------------------
 */

import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class SheFrog extends Actor implements ImageObserver {

    private static Image img =
        new ImageIcon("img/SheFrog-static.png").getImage();


    public SheFrog(int y, int x, int side, int direction, int speed) {
        this.x = Math.abs(x * side);
        this.y = Math.abs(y * side);
        this.width = side;
        this.height = side;
        this.property = new String("bonus");
        this.speed = speed;
        this.direction = direction;
    }


    
    /*  --M E T O D O S--  */

    /** Dibuja a la rana */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(SheFrog.img, this.x, this.y, this);
    }
}