/*----------------------------------------------------------------------
 *  NOMBRE: Map.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Mon Oct 17 2011
 *  
 *  DESCRIPCION: clase para un tablero.
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.util.ArrayList;


public class Map {
    public MapElement [][] map;            // tablero.
    private Actor [][] pLayer;             // capa del jugador
    private ArrayList <Actor> actorList;
    public int height;                     // alto.
    public int width;                      // ancho.
    public int sectionSide;                // tamaño de la seccion en px.

    

    /** Construye un tablero rectangular */
    public Map(int height, int width, int sectionSide) {
        this.height = height;
        this.width = width;
        this.sectionSide = sectionSide;
        this.map = new MapElement [height][width];
        this.pLayer = new Actor [height][width];
        this.actorList = new ArrayList <Actor>();

        /* llenamos el tablero con vacio (Empty class) 
         * Empty class o NullActor class
         */
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = new Empty(i, j, this.sectionSide);
                
                this.pLayer[i][j] = 
                    new NullActor(i, j, this.sectionSide);
            }
    }

    /** Construye un tablero cuadrado */
    public Map(int side, int sectionSide) {
        this.height = side;
        this.width = side;
        this.sectionSide = sectionSide;
        this.map = new MapElement [height][width];
        this.pLayer = new Actor [height][width];
        this.actorList = new ArrayList <Actor>();

        /* llenamos el tablero con vacio (Empty class) 
         * Empty class o NullActor class
         */
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = new Empty(i, j, this.sectionSide);

                this.pLayer[i][j] = 
                    new NullActor(i, j, this.sectionSide);
            }                           
    }
       
    

    /*  --M E T O D O S--  */

    /** Dibuja todas las secciones del mapa */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++)
                this.map[i][j].draw(g2d);

        Actor bonus = this.actorList.get(0);
        for (int i = 0; i < this.actorList.size(); i++) {
            if (this.actorList.get(i).property.equals("bonus")) {
                bonus = this.actorList.get(i);
                continue;
            }
            this.actorList.get(i).draw(g2d);
        }
        bonus.draw(g2d);

        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++)
                this.pLayer[i][j].draw(g2d); 
                    
    }

    /** Agrega un actor en la casilla (y , x) del tablero */
    public void addActor(Actor actor) {
        this.actorList.add(actor);

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
        else {
            return this.map[y][x];
        }
    }

    /** Verifica si el jugador tiene una collision con un actor.
     *  Retornara 
     *  0 si no la tuvo.
     *  1 si collisiono y debe morir
     *  2 si esta sobre un actor no asesino
     */
    public int checkCollision(Actor player) {
        int tolerance = this.sectionSide / 4;

        for (int i = 0; i < this.actorList.size(); i++) {
            Actor current = this.actorList.get(i);

            if (player.y == current.y) { 
                if (player.x + player.width > current.x + tolerance &&
                    player.x < current.x + current.width - tolerance) {

                    if (current.property.equals("enemy"))
                        return 1;
                    
                    if (current.property.equals("bonus")) {
                        player.speed = current.speed * current.direction;
                        this.actorList.remove(i);
                        
                        // ADVERTENCIA: esto es para uso especifico de 
                        // frogger.java
                        if (player instanceof Frog) {
                            Frog n = (Frog) player;
                            n.score += 100;
                            //player = n;
                        }

                        return 2;
                    }

                    if (current.property.equals("dynamic")) {
                        player.speed = current.speed * current.direction;
                        
                        return 2;
                    }
                }                    
            }
        }
        player.speed = 0;
        return 0;
    }                   

    /** Mueve a los actores */
    public void moveActors() {
        for (int i = 0; i < this.actorList.size(); i++) {
            Actor current = this.actorList.get(i);

            /* si el actor actual esta fuera del limite derecho */
            if (current.x > this.width * this.sectionSide && 
                current.direction == 1) {
                if (current.property.equals("bonus")) {
                    this.actorList.remove(i);
                    continue;
                }                
                current.x = -current.width;
            }

            /* si el actor actual esta fuera del limite izquierdo */
            else if (current.x + current.width 
                     < 0 && current.direction == -1)
                current.x = this.width * this.sectionSide + current.width;
                                        
            current.move();
            
        }
    }


    /** Valida el movimiento de un actor. Retornara:
     *  0 si sucedio siniestro
     *  1 si no sucedio siniestro
     *  2 si llego a una meta
     *  newY y newX son los incrementos a la posicion actual del
     *  actor.
     */
    public int checkMovement(Actor actor, int newY, int newX) {
        int y = actor.y / this.sectionSide;
        int x = actor.x / this.sectionSide;
        Actor N = new NullActor(y, x, this.sectionSide);
        int tolerance = this.sectionSide / 4;

        moveActors();

        /* calcula la casilla actual del actor CASO para x */
        if (actor.x + actor.width < x * this.sectionSide + tolerance * 3)
            newX = -1;
        else if (actor.x > x * this.sectionSide + tolerance * 3)
            newX = 1;


        /* chequeo de los limites del mapa y colisionadores*/
        if (y + newY == this.height) {
            actor.y -= actor.height;
            newY = 0;
            return 1;
        }
        else if (y + newY < 0) {
            actor.y = 0;
            newY = 0;
            return 1;
        }
        else if (x + newX == this.width || x + newX == -1) {
            return 0;
        }
        if (this.map[y + newY][x].property.equals("collider")) {
            actor.y += actor.height;
            newY = 0;
            return 1;
        }

        if (actor.property.equals("player")) {
            this.pLayer[y][x] = N;
            this.pLayer[y + newY][x + newX] = actor;
        }


        /* chequeo de desgracias */
        y = actor.y / this.sectionSide;  // refresca y
        x = actor.x / this.sectionSide;  // refresca x

        if (actor.property.equals("player")) {
            int collision = checkCollision(actor);
            if (collision == 0) {
                if (this.map[y][x].property.equals("killer"))
                    return 0;
                else if (this.map[y][x].property.equals("goal")) {
                    this.map[y][x].property = new String("killer");
                    return 2;
                }
                    
            }
            else if (collision == 1) {
                return 0;
            }
        }
        /* si un actor mata a un jugador */
        else if (actor.property.equals("killer")) {            
            for (int i = x; i < actor.width; i++)
                if (this.pLayer[y][i].property.equals("player"))
                    return 0;
        }

        return 1;
    }
}
