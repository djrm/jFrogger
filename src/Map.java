/*----------------------------------------------------------------------
 *  NOMBRE: Map.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Mon Oct 17 2011
 *  
 *  DESCRIPCION: clase para un tablero.
 *
 *    Map[4][5]  ----------- x       Map[0][1] = a
 *             |  o a o o o          Map[2][2] = c
 *             |  o o o o o
 *             |  o o c o o          Map[y][x]
 *             |  t o o o o
 *             y
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;

public class Map {
    public MapElement [][] map;     // tablero.

    private Actor [][] actorLayer;  // capa para los actores
    private Actor [][] pLayer;      // capa del jugador

    public int height;              // alto.
    public int width;               // ancho.

    public int sectionSide;         // tamaño de la seccion en px.

    

    /** Construye un tablero rectangular */
    public Map(int height, int width, int sectionSide) {
        this.height = height;
        this.width = width;
        this.sectionSide = sectionSide;

        this.map = new MapElement [height][width];

        this.actorLayer = new Actor [height][width];
        this.pLayer = new Actor [height][width];

        /* llenamos el tablero con vacio (Empty class) 
         * Empty class o NullActor class
         */
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = new Empty(i, j, this.sectionSide);
                
                this.actorLayer[i][j] = this.pLayer[i][j] = 
                    new NullActor(i, j, this.sectionSide);
            }
    }

    /** Construye un tablero cuadrado */
    public Map(int side, int sectionSide) {
        this.height = side;
        this.width = side;
        this.sectionSide = sectionSide;

        this.map = new MapElement [height][width];

        this.actorLayer = new Actor [height][width];
        this.pLayer = new Actor [height][width];

        /* llenamos el tablero con vacio (Empty class) 
         * Empty class o NullActor class
         */
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = new Empty(i, j, this.sectionSide);

                this.actorLayer[i][j] = this.pLayer[i][j] = 
                    new NullActor(i, j, this.sectionSide);
            }                           
    }
       
    

    /*  --M E T O D O S--  */

    /** Dibuja todas las secciones del mapa */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {

                this.map[i][j].draw(g2d);
                this.actorLayer[i][j].draw(g2d);
                this.pLayer[i][j].draw(g2d);
            }
                    
    }

    /** Agrega un actor en la casilla (y , x) del tablero */
    public void addActor(Actor actor) {
        /* (y , x) del tablero para el nuevo elemento */
        int y = actor.y / this.sectionSide;
        int x = actor.x / this.sectionSide;

        for (int i = x; i < actor.width / this.sectionSide; i++) {
            this.actorLayer[y][i] = actor;
        }
    }

    /** Agrega un jugador al tablero de jugadores */
    public void addPlayer(Actor player) {
        int y = player.y / this.sectionSide;
        int x = player.x / this.sectionSide;
            
        this.pLayer[y][x] = player;
    }
    
    /** Devuelve lo que hay en la seccion (y , x) del tablero */
    public MapElement whatsIn(int y, int x) {
        if (pLayer[y][x].property.equals("player")) {
            return this.pLayer[y][x];
        }
        else if (!actorLayer[y][x].property.equals("null")) {
            return this.actorLayer[y][x];
        }
        else {
            return this.map[y][x];
        }
    }

    /** Reubica a los actores en el tablero 'actorLayer'.
     *  Esta funcion verifica si aun es vigente que un actor
     *  tenga posesion de una casilla del tablero, y de no serlo
     *  reasignara las casillas correspondientes al actor.
     */
    public void checkActors() {
        Actor current = this.actorLayer[0][0];
        int tail = current.x + current.width / this.sectionSide;
        int limit = this.sectionSide / 3;

        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {

                if (!(this.actorLayer[i][j] instanceof NullActor)) {
                    current = this.actorLayer[i][j];

                    /* calcula el actor actual y la posicion en
                     * en el tablero de su longitud. Este solo es el
                     * caso para un actor con desplazamiento en x.
                     */                    
                    tail = (current.x + current.width) / sectionSide;

                    /* en caso de que el objeto se salga de los 
                     * limites */
                    if (current.x < 0) {
                        current.x += this.width * sectionSide;
                        current.width += current.x;
                    }
                    else if (current.x + current.width > 
                             this.width * sectionSide) {
                        current.x -= (this.width * sectionSide);
                        current.width -= current.x;
                    }
                    

                    /* desplazamiento hacia la izquierda */
                    if (current.x < (j - 1) * this.sectionSide) {
                        Actor N = new NullActor(i, tail, sectionSide);

                        this.actorLayer[i][j - 1] = current;
                        this.actorLayer[i][tail] = N;
                    }

                    /* desplazamiento hacia la derecha */
                    else if (current.x > limit * 2) {
                        Actor N = new NullActor(i, tail, sectionSide);

                        this.actorLayer[i][j] = N;
                        this.actorLayer[i][tail + 1] = current;
                    }

                    /* falta una implementacion como esta pero 
                     *  para un actor que se desplaza en y 
                     */


                    /*
                    if (tail > this.width)
                        break;
                    
                    j = tail;
                    */
                }
            }
    }

    /** Valida el movimiento de un actor. Retornara 1 siempre 
     *  y cuando no haya ocurrido un siniestro.
     *  newY y newX son los incrementos a la posicion actual del
     *  actor.
     */
    public boolean checkMovement(Actor actor, int newY, int newX) {
        checkActors();

        int y = actor.y / this.sectionSide;
        int x = actor.x / this.sectionSide;
        Actor N = new NullActor(y, x, this.sectionSide);

        /* chequeo de los limites del mapa */
        if (y + newY == this.height) {
            actor.y -= actor.height;
            newY = 0;
        }
        else if (x + newX == this.width || x + newX == -1) {
            return false;
        }


        if (actor.property.equals("player")) {
            this.pLayer[y][x] = N;
            this.pLayer[y + newY][x + newX] = actor;
        }
        else {
            this.actorLayer[y][x] = N;
            this.actorLayer[y + newY][x + newX] = actor;
        }

        /* chequeo de desgracias */
        y = actor.y / this.sectionSide;
        x = actor.x / this.sectionSide;

        if (actor.property.equals("player")) {
            if (this.actorLayer[y][x] instanceof NullActor) {
                if (this.map[y][x].property.equals("killer"))
                    return false;
            }        
            else {
                if (this.actorLayer[y][x].property.equals("enemy"))
                    return false;
            }
        }
        /* si un actor mata a un jugador */
        else if (actor.property.equals("killer")) {            
            for (int i = x; i < actor.width; i++)
                if (this.pLayer[y][i].property.equals("player"))
                    return false;
        }

        return true;
    }
}
