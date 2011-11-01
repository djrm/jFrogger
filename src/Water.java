/*----------------------------------------------------------------------
 *  NOMBRE: Water.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Wed Oct 19 2011
 *  
 *  DESCRIPCION: clase para una seccion de agua
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Water extends Section implements ImageObserver {
    private static Image img = 
        new ImageIcon("img/water.png").getImage();


    public Water(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;
        this.property = new String("killer");
    }



    /*  --M E T O D O S--  */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x, this.y, this);

    }
}