/*----------------------------------------------------------------------
 *  NOMBRE: Actor.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Thu Oct 13 2011
 *  
 *  DESCRIPCION: clase para un elemento dinamico.
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;

abstract class Actor extends MapElement {
    public int width;   // ancho del actor
    public int height;  // alto del acto

    /*  propiedades:
     *  player
     *  enemy
     *  dynamic
     */
    public String property;


    /*  --M E T O D O S--  */

    /** Realiza el movimiento del actor */
    public abstract void move();
}
