/*----------------------------------------------------------------------
 *  NOMBRE: Empty.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Fri Oct 14 2011
 *  
 *  DESCRIPCION: clase para un espacio vacio.
                 propiedad: ghost
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;

public class Empty extends Section {

    /* recibe los parametros en casillas */
    public Empty(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.side = side;

        this.property = new String("ghost");
    }

    

    /*  --M E T O D O S--  */
    public void draw(Graphics2D g2d) {/* dibuja vacio */}
}