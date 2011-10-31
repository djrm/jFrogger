/*----------------------------------------------------------------------
 *  NOMBRE: Border.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 25 2011
 *  
 *  DESCRIPCION: bordes superiores
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Border extends Section implements ImageObserver {
    private static Image img = new ImageIcon("img/Border.png").getImage();


    public Border(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;
        this.property = new String("collider");
    }



/*=========================  M E T O D O S  =========================*/

    public void draw(Graphics2D g2d) {
        g2d.drawImage(Border.img, this.x, this.y, this);
    }
}