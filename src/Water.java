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
import java.awt.Color;

public class Water extends Section {
    Color color;


    public Water(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;

        this.property = new String("killer");
        this.color = Color.BLUE;
    }



    /*  --M E T O D O S--  */
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        
        /* provisionalmente sera un cuadro azul */
        g2d.fillRect(this.x, this.y, this.side, this.side);
    }
}