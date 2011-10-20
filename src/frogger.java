/*----------------------------------------------------------------------
 *  NOMBRE: frogger.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 11 2011
 *  
 *  DESCRIPCION: juego de frogger
 *----------------------------------------------------------------------
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import javax.swing.JPanel;
import javax.swing.JFrame;



public class frogger extends JPanel implements ActionListener {
    private Frog froggy;
    private Map level;
    private Timer timer;

    /* tiempo */
    private int timeL;


    public static void main(String args[]) {
	JFrame frame = new JFrame("Frogger");

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(new frogger());
       	frame.setSize(500, 500);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }


    public frogger() {
	addKeyListener(new TAdapter());
	setFocusable(true);
	setDoubleBuffered(true);
        setBackground(Color.BLACK);

        /* inicializacion del mapa y rana */
        this.level = new Map(13, 500 / 25, 25);
	this.froggy = new Frog(9, 9, 25, new Color(128, 255, 0));

        /* agrega a el jugador al tablero */
        this.level.addPlayer(this.froggy);

        /* contador */
	this.timer = new Timer(90, this);
	this.timer.start();

        /* construccion del mapa */
        /* lago y carretera */
        for (int i = 1; i < 6; i++)
            for (int j = 0; j < this.level.width; j++) {
                this.level.map[i][j] = new Water(i, j, 25);
                this.level.map[i + 6][j] = new Road(i + 6, j, 25);
            }
        
        /* banquetas */
        for (int j = 0; j < this.level.width; j++) {
            this.level.map[6][j] = new Sidewalk(6, j, 25);
            this.level.map[12][j] = new Sidewalk(12, j, 25);
        }                
       
 
        /* temporizador */
        this.timeL = 450;

    }



    /*  --M E T O D O S-- */    
    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;

	super.paint(g);

        /* Activa antialiasing */        
	RenderingHints rh = 
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_OFF);
	rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHints(rh);

        /* dibujar los elementos */
        this.level.draw(g2d);


        /* pantalla de informacion */
        g2d.setColor(Color.WHITE);
        
        /* puntos del jugador */
        g2d.drawString("SCORE: " + this.froggy.score, 20, 370);

        /*vidas*/
        int k = 15;

        for (int i = 0; i < this.froggy.lives; i++) {
            g2d.fillRect(480 - k, 340, 10, 10);
            k += 15;
        }

        /* temporizador */
        g2d.drawString("TIME: ", 20, 400);

        if (this.timeL < 60) 
            g2d.setColor(Color.RED);
        g2d.fillRect(20, 410, this.timeL, 20);


        g2d.setColor(Color.WHITE);        

    }
    
    public void actionPerformed(ActionEvent e) {
        boolean alive;

        alive = level.checkMovement(froggy, froggy.dy, froggy.dx);

        if (alive) {
            this.froggy.move();

            if (this.froggy.dy == -1) {
                this.froggy.score += 10;
            }
        }

        else {
            System.out.println("oh no, you're dead!!!");
            out();
        }  

        this.timeL--;

        /* se acabo el timepo? */
        if (this.timeL == 0) {
            System.out.println("Time is out!!!");
            this.timeL = 450;
            out();
        }

        this.repaint(); 
    }

    /** Realiza la accion de salida del juego */
    private void out() {
        /* poner aqui la accion de salida */
        if (this.froggy.lives > 0) {
            this.froggy.lives--;

            this.froggy.x = 6 * this.froggy.width;
            this.froggy.y = 12 * this.froggy.height;            
        }
        else 
            System.exit(1);
    }




    /*  --C L A S E S--  */
    /* Clase privada TAdapter */
    private class TAdapter extends KeyAdapter {
	public void keyPressed(KeyEvent e) {
            froggy.keyPressed(e);
        }

	public void keyReleased(KeyEvent e) {
            froggy.keyReleased(e);
	}
    }   
}

