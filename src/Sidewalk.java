/*----------------------------------------------------------------------
 *  NOMBRE: Sidewalk.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Wed Oct 19 2011
 *  
 *  DESCRIPCION: clase para la banqueta
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Color;

public class Sidewalk extends Section {
    Color color;


    public Sidewalk(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;
        this.property = new String("ghost");
        this.color = new Color(245, 147, 0);
    }



    /*  --M E T O D O S--  */
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        
        /* provisionalmente sera un cuadro morado */
        g2d.fillRect(this.x, this.y, this.side, this.side);
    }
}