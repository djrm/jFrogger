/*----------------------------------------------------------------------
 *  NOMBRE: Map.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Mon Oct 17 2011
 *  
 *  DESCRIPCION: clase para un tablero. soporte para un jugador
 *----------------------------------------------------------------------
 */

import java.awt.Graphics2D;
import java.util.ArrayList;

@SuppressWarnings({"unchecked"})

public class Map {
    public int height;               // alto.
    public int width;                // ancho.
    public int sectionSide;          // tamaño de la seccion en px.
    public MapElement [][] map;      // tablero.
    public Actor player;             // jugador.
    private ArrayList <Actor> [] actorMap;

    
    /** Construye un tablero rectangular */
    public Map(int height, int width, int sectionSide) {
        this.height = height;
        this.width = width;
        this.sectionSide = sectionSide;
        this.map = new MapElement [height][width];
        this.actorMap = new ArrayList [height];

        // llenamos el tablero con vacio (Empty class) 
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = new Empty(i, j, this.sectionSide);
            }
            this.actorMap[i] = null;
        }
    }

    /** Construye un tablero cuadrado */
    public Map(int side, int sectionSide) {
        this.height = side;
        this.width = side;
        this.sectionSide = sectionSide;
        this.map = new MapElement [height][width];
        this.actorMap = new ArrayList [height];

        // llenamos el tablero con vacio (Empty class) 
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.map[i][j] = new Empty(i, j, this.sectionSide);
            }        
            this.actorMap[i] = null;
        }
    }
       
    

/*=========================  M E T O D O S  =========================*/

    /** Dibuja todas las secciones del mapa */
    public void draw(Graphics2D g2d) {
        // dibuja el mapa estatico
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++)
                this.map[i][j].draw(g2d);

        // dibuja los actores
        Actor bonus = null;
        ArrayList <Actor> actorList;

        for (int y = 0; y < this.actorMap.length; y++) {
            if (this.actorMap[y] == null)
                continue;

            actorList = this.actorMap[y];
            for (int i = 0; i < actorList.size(); i++) {
                if (actorList.get(i).property.equals("bonus")) {
                    bonus = actorList.get(i);
                    continue;
                }
                actorList.get(i).draw(g2d);
            }
        }
        if (bonus != null)
            bonus.draw(g2d);

        // dibuja a el jugador
        if (player != null)
            this.player.draw(g2d);
    }

    /** Agrega un actor en el mapa de actores, si el mapa
     *  de actores ya posee un elemento en la posicion 'actor.y'
     *  entonces lo agregara a la lista de dicha posiscion 'y'
     *  en otro caso creara una nueva lista para para alojar el
     *  el actor insertado.
     */
    public void addActor(Actor actor) {
        int y = actor.y / this.sectionSide;
        
        if (this.actorMap[y] == null) {
            this.actorMap[y] = new ArrayList <Actor>();
        }
        if (actor.property.equals("bonus")) {
            // los bonus van al principio
            this.actorMap[y].add(0, actor);
        }
        else
            this.actorMap[y].add(actor);
    }

    /** Agrega un jugador al mapa */
    public void addPlayer(Actor player) {
        this.player = player;
    }
    
    /** Verifica si el actor tiene una collision con otro actor.
     *  Retornara 
     *  0 si no la tuvo.
     *  1 si collisiono y debe morir
     *  2 si esta sobre un actor no asesino
     *  3 si esta sobre un actor bonus
     */
    private int checkCollision(Actor actor, int y) {
        int tolerance = this.sectionSide / 4;
        ArrayList <Actor> actorList;

        if (this.actorMap[y] == null) {
            actor.speed = 0;
            return 0;
        }

        actorList = this.actorMap[y];

        for (int i = 0; i < actorList.size(); i++) {
            Actor current = actorList.get(i);

            if (actor.equals(current))
                continue;

            if (actor.x + actor.width >= current.x + tolerance * 2 &&
                actor.x <= current.x + current.width - tolerance * 2) {

                if (current.property.equals("enemy"))
                    return 1;

                if (current.property.equals("dynamic")) {
                    if (actor.property.equals("bonus")) {
                        actor.speed = current.speed;
                        actor.direction = current.direction;
                        return 2;
                    }

                    actor.speed = current.speed * current.direction;
                    return 2;
                }
                    
                if (current.property.equals("bonus")) {
                    actor.speed = current.speed * current.direction;
                    actorList.remove(i);
                        
                    return 3;
                }
            }            
        }
    
        actor.speed = 0;
        return 0;
    }

    /** Mueve a los actores */
    public void moveActors() {
        ArrayList <Actor> actorList;

        for (int y = 0; y < this.actorMap.length; y++) {
            if (this.actorMap[y] == null)
                continue;
            
            actorList = this.actorMap[y];
            for (int i = 0; i < actorList.size(); i++) {
                Actor current = actorList.get(i);

                // verifica si hay un bonus estancado
                if (current.property.equals("bonus") &&
                    current.speed == 0) {
                    checkCollision(current, y);
                }


                // si el actor actual esta fuera del limite derecho
                if (current.x > this.width * this.sectionSide && 
                    current.direction == 1) {
                    if (current.property.equals("bonus")) {
                        actorList.remove(i);
                        continue;
                    }
                    current.x = -current.width;
                }

                // si el actor actual esta fuera del limite izquierdo
                else if (current.x + current.width < 0 &&
                         current.direction == -1) {
                    current.x = this.width * this.sectionSide + 
                        current.width;
                }
                current.move();
            }            
        }
    }


    /** Valida el movimiento de un actor. Retornara:
     *  0 si sucedio siniestro
     *  1 si no sucedio siniestro
     *  2 si llego a una meta
     *  3 si llego a un bonus
     *  newY y newX son los incrementos a la posicion actual del
     *  actor.
     */
    public int checkMovement(Actor actor, int newY, int newX) {
        int y = actor.y / this.sectionSide;
        int x = actor.x / this.sectionSide;
        int tolerance = this.sectionSide / 4;

        moveActors();

        /* calcula la casilla actual del actor CASO para x */
        if (actor.x > x * this.sectionSide + tolerance * 2)
            x += 1;


        /* chequeo de los limites del mapa y colisionadores*/
        // limite inferior del mapa
        if (y + newY >= this.height) {
            actor.y -= actor.height;
            return 1;
        }
        // limite superior del mapa
        else if (actor.y + (newY * this.sectionSide) < 0) {
            actor.y = 0;
            return 1;
        }
        // muere si sale de los limites laterales
        else if (x + newX >= this.width || x + newX < 0) {
            return 0;
        }
        // collision en y
        if (this.map[y + newY][x].property.equals("collider")) {
            actor.y += actor.height;
            return 1;
        }


        /* chequeo de desgracias */
        if (actor.property.equals("player")) {
            int collision = checkCollision(actor, y);
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
            else if (collision == 3) {
                return 3;
            }
        }

        return 1;
    }
}
