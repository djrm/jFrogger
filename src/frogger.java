/*----------------------------------------------------------------------
 *  NOMBRE: frogger.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 11 2011
 *  
 *  DESCRIPCION: juego de frogger
 *----------------------------------------------------------------------
 */

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Font;


public class frogger extends JPanel 
    implements ActionListener, ImageObserver {
    private Frog froggy;    // jugador.
    private Map level;      // nivel.
    private int timeL;      // tiempo de juego.
    private int speed;      // dificultad del juego.
    private int beatScore;  // puntuacion a lograr para vida extra
    private int goals;      // metas que ha logrado
    private int bonusTime;  // timepo de reaparicion del bonus    
    private Death d;
    private boolean filter;
    private Font font;
    private Timer timer;
    private Image img = new ImageIcon("img/Cover.png").getImage();


    public static void main(String args[]) {
	JFrame frame = new JFrame("JFrogger");

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(new frogger());
       	frame.setSize(710, 500);
	frame.setLocationRelativeTo(null);
        frame.setResizable(false);
	frame.setVisible(true);
        frame.setIconImage(new ImageIcon("img/Icon.png").getImage());
    }


    public frogger() {
        boolean t = true;

        // propiedades de la ventana
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.BLACK);

        // propiedades del juego
        this.speed = 1;
        this.beatScore = 1000;
        this.bonusTime = 1;
        this.d = new Death(3);
        //this.font = new Font("Sans", Font.PLAIN, 16);

        // inicializacion del mapa y rana
        this.level = new Map(13, 700 / 25, 25);
        this.froggy = new Frog(12, 9, 25);

        // agrega a el jugador y actores al tablero
        this.level.addPlayer(this.froggy);      

        for (int i = 0; i < 25; i += 6) {
            level.addActor(new Racer(7, i, 25, 1, 3 + speed, "red"));
            level.addActor(new Racer(11, i + 3, 25, 1, 1 + speed, "red"));
        }
        for (int i = 0; i < 20; i += 9)
            level.addActor(new Racer(9, i, 25, 1, 5 + speed, "blue"));
        for (int i = 0; i < 21; i += 10)  {
            level.addActor(new Truck(8, i, 25, -1, 2 + speed));
            level.addActor(new Truck(10, i + 4, 25, -1, 1 + speed));
        }
        for (int i = 0; i < 27; i += 7) 
            level.addActor(new Tree(4, i, 25, "small", 1, 4 + speed));
        for (int i = 0; i < 25; i += 8) 
            level.addActor(new Tree(1, i, 25, "mid", 1, 2 + speed));
        level.addActor(new Tree(3, 0, 25, "big", 1, 1 + speed));
        level.addActor(new Tree(3, 12, 25, "big", 1, 1 + speed));
        level.addActor(new Tree(3, 24, 25, "big", 1, 1 + speed));        
        for (int i = 0; i < 31; i += 6) {
            level.addActor(new Turtles(5, i, 25, 3, -1, 1 + speed, t));
            t = !t;
        }
        for (int i = 0; i < 29; i += 4) {
            level.addActor(new Turtles(2, i, 25, 2, -1, 2 + speed, t));
            t = !t;
        }


        /* construccion del mapa */
        // rio y carretera
        for (int i = 1; i < 6; i++)
            for (int j = 0; j < this.level.width; j++) {
                this.level.map[i][j] = new Water(i, j, 25);
                this.level.map[i + 6][j] = new Road(i + 6, j, 25);
            }        
        // banquetas
        for (int j = 0; j < this.level.width; j++) {
            this.level.map[6][j] = new Sidewalk(6, j, 25);
            this.level.map[12][j] = new Sidewalk(12, j, 25);
        }
        // metas y borde superior
        this.level.map[0][0] = new Border(0, 0, 25);        
        for (int i = 0; i < 25; i += 4) {
            this.level.map[0][i] = new Border(0, i, 25);
            this.level.map[0][i + 1] = new Border(0, i + 1, 25);
        }
        this.level.map[0][26] = new Border(0, 26, 25);
        this.level.map[0][27] = new Border(0, 27, 25);
        for (int i = 2; i < 25; i += 4) {        
            this.level.map[0][i] = new Goal(0, i, 25);
            this.level.map[0][i + 1] = this.level.map[0][i];
        }

        this.timeL = 600 - speed * 4;  // timepo de juego.

	this.timer = new Timer(90, this);   // contador
	//this.timer.start();
    }



/*=========================  M E T O D O S  =========================*/

    /** Agrega una rana de bonus al juego */
    private void addBonus() {
        Random r = new Random();
        int pos = Math.abs(r.nextInt()) % 5;

        if (pos == 0) pos = 1;
        if (pos == 2) pos = 3;
        if (pos == 3) pos = 4;
        
        // la colocamos aqui y esperamos a que un dinamico se la
        // lleve
        this.level.addActor(new SheFrog(pos, -2, 25, 0, 0));
    }

    /** Realiza la accion de salida del juego 
     *  valores de entrada:
     *  0 perdio por tiempo
     *  1 perdio por accidente
     *  2 gano
     *  3 si llego a una meta
     */
    private void out(int outCode) {
        if (outCode == 2) {
            System.out.println("croooac, Ganaste!!!");
            System.out.println("Tu puntuacion: " + froggy.score);
            System.exit(1);
        }
        else if (outCode == 3) {
            this.froggy.score += 200; 
            this.goals++;            
            this.timeL += 100;
            // punto de reaparicion
            this.froggy.x = 6 * this.froggy.width; 
            this.froggy.y = 12 * this.froggy.height;
            System.out.println("Binevenido a casa!!!");
        }
        /* muerto */
        else {
            if (outCode == 1) {
                System.out.println("Wooops, has muerto!!!");
            }
            if (outCode == 0) {
                System.out.println("birik, se acabo el tiempo!!!");
                this.timeL = 600 - speed * 4;
            }
            if (this.froggy.lives > 0) {
                this.froggy.lives--;               
                // punto de reaparicion
                this.froggy.x = 6 * this.froggy.width;
                this.froggy.y = 12 * this.froggy.height;
            }
            else {
                /* accion de salida aqui */
                System.out.println("croooac, perdiste!!!");
                System.out.println("Tu puntuacion: " + froggy.score);
                System.exit(1);
            }            
        }
    }

    /** Muestra la informacion sobre el juego (barra de tiempo,
     *  vidas y puntuacion).
     */
    public void infoScreen(Graphics2D g2d) {
        int k = 15;

        g2d.setColor(Color.WHITE);        

        // puntos del jugador
        g2d.drawString("SCORE: " + this.froggy.score, 20, 370);

        // vidas
        for (int i = 0; i < this.froggy.lives; i++) {
            g2d.fillRect(680 - k, 340, 10, 10);
            k += 15;
        }

        // temporizador
        g2d.drawString("TIME: ", 20, 400);
        if (this.timeL < 60) 
            g2d.setColor(Color.RED);
        g2d.fillRect(20, 410, this.timeL, 20);
        g2d.setColor(Color.WHITE);
    }        

    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;

	super.paint(g);
        this.level.draw(g2d);   // dibuja todos los elementos de mapa
        this.infoScreen(g2d);
        this.d.draw(g2d);       // dibuja la imagen de muerte
    
        // filtro
        if (this.filter) {
            //g2d.setColor(new Color(0, 0, 100, 134));
            g2d.setColor(new Color(0, 0, 0, 134));
            g2d.fillRect(0, 0, 710, 13*25);
        }

        if (!this.timer.isRunning()) {
            // dibuja la pantalla de presentacion
            g2d.drawImage(this.img, 0, 0, this);
        }       
    }
    
    public void actionPerformed(ActionEvent e) {
        int state = this.level.checkMovement(froggy, froggy.dy, froggy.dx);

        if (state == 1) {
            this.froggy.move();

            if (this.froggy.dy == -1)
                this.froggy.score += 10; // puntos por subir
        }
        else if (state == 2) {
            this.froggy.move(); 
            this.out(3);                 // llego a la meta
        }
        else if (state == 0){
            this.d.active = true;        // activa la imagen de muerte
            this.d.x = this.froggy.x;    // copia la posicion de la rana
            this.d.y = this.froggy.y;    // antes de la reaparicion.
            this.out(1);                 // murio
        }  
        else if (state == 3) {
            this.froggy.score += 100;    // tomo un bonus
        }
        
        // vidas extras por puntuacion
        if (this.froggy.score >= this.beatScore) {
            this.froggy.lives++;
            this.beatScore += 1000;
            this.timeL += 100;
        }
        // metas del juego
        if (this.goals == 6)
            this.out(2);                 // termino el juego

        // tiempo de juego
        this.timeL--;
        if (this.timeL == 0) {
            this.d.active = true;
            this.d.x = this.froggy.x;
            this.d.y = this.froggy.y;
            this.out(0);                 // se acabo el tiempo
        }

        // bonus
        if (this.bonusTime % 700 == 0)
            this.addBonus();
        this.bonusTime++;
           
        this.repaint(); 
    }

    public boolean imageUpdate(Image img, int infoFlags, int x, int y,
                               int width, int height) {
        return true;
    }


/*=========================  C L A S E S  =========================*/

    /* Clase privada TAdapter */
    private class TAdapter extends KeyAdapter {
	public void keyPressed(KeyEvent e) {
            if (!timer.isRunning())
                timer.start();

            if (e.getKeyCode() == KeyEvent.VK_F)
                filter = !filter;

            froggy.keyPressed(e);
            //System.out.println("p");
        }

	public void keyReleased(KeyEvent e) {
            froggy.keyReleased(e);
            //System.out.println("r");
	}
    }              

    /** clase privada para el icono de muerte */
    private class Death implements ImageObserver {
        private int time;
        private int cTime;
        public int x;
        public int y;
        public boolean active;
        private Image img = new ImageIcon("img/Frog-dead.png").getImage();
        
        public Death(int time) {
            this.time = time;
            this.cTime = time;
            this.active = false;
        }
        
        public void draw(Graphics2D g2d) {
            if (this.active) {
                if (this.cTime != 0) {
                    this.cTime--;
                }
                else {
                    cTime = time;
                    this.active = false;
                }

                g2d.drawImage(this.img, this.x, this.y, this);
            }
        }

        public boolean imageUpdate(Image img, int infoFlags, 
                                   int x, int y,
                                   int width, int height) {
            return true;
        }
    }
}


