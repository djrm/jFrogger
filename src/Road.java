/*----------------------------------------------------------------------
 *  NOMBRE: Road.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Wed Oct 19 2011
 *  
 *  DESCRIPCION: clase para una seccion de carretera.
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Color;

public class Road extends Section {
    Color color;


    public Road(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;

        this.property = new String("ghost");
        this.color = Color.GRAY;
    }



    /*  --M E T O D O S--  */
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        
        /* provisionalmente sera un cuadro negro */
        g2d.fillRect(this.x, this.y, this.side, this.side);
    }
}