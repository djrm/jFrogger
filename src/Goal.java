/*----------------------------------------------------------------------
 *  NOMBRE: Goal.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Sat Oct 22 2011
 *  
 *  DESCRIPCION: Meta.
 *----------------------------------------------------------------------
 */


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Goal extends Section implements ImageObserver {
    private Image img;

    private static Image goal = 
        new ImageIcon("img/Goal.png").getImage();
    private static Image taken = 
        new ImageIcon("img/Goal-taken.png").getImage();


    public Goal(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;
        this.property = new String("goal");
    }



    /*  --M E T O D O S--  */

    public void draw(Graphics2D g2d) {
        if (this.property.equals("goal")) {
            this.img = goal;
        }
        else {
            this.img = taken;
        }

        g2d.drawImage(this.img, this.x, this.y, this);
    }
}
