/*----------------------------------------------------------------------
 *  NOMBRE: MapElement.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Mon Oct 17 2011
 *  
 *  DESCRIPCION: clase abstracta para un elemento del mapa
 * 
 *  ya que todos los elementos del mapa cuentan con una imagen que los
 *  interpreta, se colocara aqui la funcion para ImageObserver.
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.awt.Image;

abstract class MapElement {
    public int x;  // coordenada x del elemento en px.
    public int y;  // coordenada y del elemento en px.

    /* propiedad que identifica a el elemento 
     * 'ver propiedades disponibles en las subclases'
     */
    public String property;
    


    /*  --M E T O D O S--  */

    /** Dibuja el elemento de el mapa */
    public abstract void draw(Graphics2D g2d);

    public boolean imageUpdate(Image img, int infoFlags, int x, int y,
                               int width, int height) {
        return true;
    }
}