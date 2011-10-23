/*----------------------------------------------------------------------
 *  NOMBRE: Tree.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Fri Oct 21 2011
 *  
 *  DESCRIPCION: clase para un arbol
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Tree extends Actor implements ImageObserver {
    private Image img;
    
    private static Image small = 
        new ImageIcon("img/TreeSmall.png").getImage();
    private static Image mid = 
        new ImageIcon("img/TreeMid.png").getImage();
    private static Image big = 
        new ImageIcon("img/TreeBig.png").getImage();


    public Tree(int y, int x, int side, String size, int direction,
                int speed) {

        this.x = x * side;
        this.y = y * side;
        this.height = side;
        this.direction = direction;
        this.speed = speed;
        this.property = new String("dynamic");

        if (size.equals("big")) {
            img = Tree.big;
            this.width = 175;
        }
        else if (size.equals("mid")) {
            img = Tree.mid;
            this.width = 125;
        }
        else if (size.equals("small")) {
            img = Tree.small;
            this.width = 75;
        }
        else {
            img = Tree.small;
            this.width = 75;
        }
    }



    /*  --M E T O D O S--  */

    public void draw(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x, this.y, this);
    }

    public void move() {
        if (this.direction == -1) {
            this.x -= speed;
        }
        else {
            this.x += speed;
        }
    }

    public boolean imageUpdate(Image img, int infoFlags, int x, int y,
                               int width, int height) {
        return true;
    }
}