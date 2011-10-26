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
    private boolean moving;      // movimiento.
    private byte cycle;          // contador de cambio de movimiento
    private Image img;           // imagen actual

    private static Image water2 = 
        new ImageIcon("img/Water2.png").getImage();
    private static Image water1 = 
        new ImageIcon("img/Water1.png").getImage();


    public Water(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;
        this.property = new String("killer");
        this.moving = false;
        this.cycle = 0;
    }



    /*  --M E T O D O S--  */
    public void draw(Graphics2D g2d) {
        if (this.moving)  img = water2;
        else  img = water1;

        g2d.drawImage(this.img, this.x, this.y, this);
        
        this.cycle++;
        if (this.cycle == 10) {
            this.moving = !this.moving;
            this.cycle = 0;
        }
    }
}