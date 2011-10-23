/*----------------------------------------------------------------------
 *  NOMBRE: NullActor.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 18 2011
 *  
 *  DESCRIPCION: clase de actor nulo
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;

public class NullActor extends Actor {
    /* recibe las coordenadas (y , x) en medida del tablero */    
    public NullActor(int y, int x, int side) {
        this.x = x * side;  // la cantidad en pixeles.
        this.y = y * side;  // la cantidad en pixeles.
        this.width = side;
        this.height = side;
        this.speed = 0;

        this.property = new String("null");
    }

    
    
    /*  --M E T O D O S--  */
    
    public void move() { /* mueve nada */ }
    public void draw(Graphics2D g2d) { /* dibuja nada*/ }
}
