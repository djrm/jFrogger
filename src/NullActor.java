/*----------------------------------------------------------------------
 *  NOMBRE: NullActor.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 18 2011
 *  
 *  DESCRIPCION: clase de actor nulo
 *----------------------------------------------------------------------
 */

public class NullActor extends Actor {

    /* recibe las coordenadas (y , x) ene medida del tablero */    
    public NullActor(int y, int x, int side) {
        this.x = x * side;
        this.y = y * side;
        this.width = side;
        this.height = side;

        this.property = new String("null");
    }

    
    
    /*  --M E T O D O S--  */
    
    public void move() { /* mueve nada */ }
    public void draw() { /* dibuja nada*/ }
}
