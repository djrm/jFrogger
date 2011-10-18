/*----------------------------------------------------------------------
 *  NOMBRE: game.java 
 *  POR: Daniel Ramirez Martinez
 *  EMAIL: danielramirezz123@gmail.com
 *  FECHA: Tue Oct 11 2011
 *  
 *  DESCRIPCION: juego
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



public class game extends JPanel implements ActionListener {
    private Pacman pac;
    private Map level;
    private Timer timer;


    public static void main(String args[]) {
	JFrame frame = new JFrame("Pacman");

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(new game());
       	frame.setSize(500, 500);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }


    public game() {
	addKeyListener(new TAdapter());
	setFocusable(true);
	setDoubleBuffered(true);
        this.level = new Map(500, 25);


        this.level.map[4][4] = new Wall(4, 4, 25);
        this.level.map[6][4] = new Wall(4, 6, 25);
        this.level.map[7][3] = new Wall(3, 7, 25);
        this.level.map[7][7] = new Wall(7, 7, 25);

	this.pac = new Pacman(50, 50, 25, new Color(255, 174, 0));
	this.timer = new Timer(5, this);
	this.timer.start();
    }



    /*  --M E T O D O S-- */    
    public void paint(Graphics graph) {
	super.paint(graph);

	Graphics2D graph2D = (Graphics2D) graph;

        /* Activa antialiasing */        
	RenderingHints rh = 
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);

	rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);

	graph2D.setRenderingHints(rh);

        //frame.removeAll();

        for (int i = 0; i < this.level.alto; i++)
            for (int j = 0; j < this.level.alto; j++)
                this.level.map[i][j].draw(graph2D);
        
        

	this.pac.live(graph2D);
    }
    
    public void actionPerformed(ActionEvent event) {
        /* capturar dx y dy de pac y verificar si son validos 
           para evitar una colision */
        
        if (this.level.canMove(pac, pac.dx, pac.dy)) {
            this.pac.move();        
            this.repaint(); 
        }
    }


    /*  --C L A S E S--  */
    /* Clase privada TAdapter */
    private class TAdapter extends KeyAdapter {
	public void keyPressed(KeyEvent event) {
	    pac.keyPressed(event);
        }

	public void keyReleased(KeyEvent event) {	    
	    pac.keyReleased(event);
	}
    }   
}

