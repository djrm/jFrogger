/*----------------------------------------------------------------------
 *  NOMBRE: Map.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Mon Oct 17 2011
 *  
 *  DESCRIPCION: clase para un tablero.
 *----------------------------------------------------------------------
 */

public class Map {
    public MapElement [][] map;     // tablero.

    private Actor [][] actorLayer;  // capa para los actores
    private Actor [][] pLayer;      // capa del jugador

    public int height;              // alto.
    public int width;               // ancho.

    public int sectionSide;         // tamaño de la seccion en px.

    

    /** Construye un tablero rectangular */
    public Map(int height, int widht, int sectionSide) {
        this.height = height;
        this.widht = widht;
        this.sectionSide = sectionSide;
        this.map = new map[widht][height];

        /* llenamos el tablero con vacio (Empty class) */
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                this.map[i][j] = new Empty(i, j, this.sectionSide);
                    
    }

    /** Construye un tablero cuadrado */
    public Map(int side, int sectionSide) {
        this.height = side;
        this.widht = side;
        this.sectionSide = sectionSide;
        this.map = new map[widht][height];

        /* llenamos el tablero con vacio (Empty class) */
        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++)
                this.map[i][j] = new Empty(i, j, this.sectionSide);
                    
    }
       
    

    /*  --M E T O D O S--  */

    /** Dibuja todas las secciones del mapa */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++)
                this.map[i][j].draw(g2d);
                    
    }

    /** Agrega un actor en la casilla (y , x) del tablero */
    public void addActor(Actor actor) {
        for (int i = actor.x / this.sectionSide; 
             i < actor.width / this.sectionSide; i++) {

            this.map[actor.height / this.sectionSide][i] = actor;
        }
    }
    
    /** Devuelve lo que hay en la seccion (y , x) del tablero */
    public Section whatsIn(int y, int x) {
        /*  este caso solo sucede cuando el elemento por el que se 
         *  pregunta es un actor. y solo considera el desplazamiento
         *  del actor sobre x.
         */
        if (this.map[y][x].x + (Actor) this.map[y][x].width < 
            this.sectionSide / 3 ||
            this.map[y][x].x > this.sectionSide / 3 * 2) {

            if (x == 0) {
                return this.map[y][x + 1];
            }
            else
                return this.map[y][x - 1];
        }

        if (this.map[y][x].x + (Actor) this.map[y][x].width > 
            this.sectionSide / 3 * 2 ||
            this.map[y][x].x > this.sectionSide / 3) {

            if (x == 0) {
                return this.map[y][x + 1];
            }
            else
                return this.map[y][x - 1];
        }

        /*  probablemente sea necesario implementar el 
         *  equivalente para el desplazamiento en y.
         */

        return this.map[y][x];
    }

    /** Reubica a los actores en el tablero */
    public void checkActors() {
        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++) {
                if (this.map[i][j] instanceof Actor) {
                    
                


    public boolean checkMovement(Actor actor, int newY, int newX) {
        
    