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


/* sonido */
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.File;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;


public class frogger extends JPanel implements ActionListener {
    private Frog froggy;  // juagador.
    private Map level;    // nivel.
    private int timeL;    // tiempo de juego.

    private Timer timer;
    
    /* sonido */
    private Sequence intro;
    private Sequencer sequencer;


    public static void main(String args[]) {
	JFrame frame = new JFrame("Frogger");

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(new frogger());
       	frame.setSize(710, 500);
	frame.setLocationRelativeTo(null);
        frame.setResizable(false);
	frame.setVisible(true);
    }



    public frogger() {
	addKeyListener(new TAdapter());
	setFocusable(true);
	setDoubleBuffered(true);
        setBackground(Color.BLACK);

        /* inicializacion del mapa y rana */
        this.level = new Map(13, 700 / 25, 25);
	this.froggy = new Frog(12, 9, 25);

        /* agrega a el jugador al tablero */
        this.level.addPlayer(this.froggy);

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
        
        this.timeL = 450;   // timepo de juego.

        /* contador */
	this.timer = new Timer(90, this);
	this.timer.start();

        
        /* sonido midi */
        try {
            intro = MidiSystem.getSequence(new File("audio/intro.mid"));
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(intro);
            sequencer.start();
        } catch (IOException e) {
        } catch (MidiUnavailableException e) {
        } catch (InvalidMidiDataException e) {
        }       
    }


    
    /*  --M E T O D O S-- */    

    /** Realiza la accion de salida del juego */
    private void out() {
        /* poner aqui la accion de salida */
        if (this.froggy.lives > 0) {
            this.froggy.lives--;

            /* punto de reaparicion */
            this.froggy.x = 6 * this.froggy.width;
            this.froggy.y = 12 * this.froggy.height;

            this.froggy.alive = true;
        }
        else 
            System.exit(1);
    }

    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;

	super.paint(g);

        this.level.draw(g2d);   // dibuja todos los elementos de mapa

        /* pantalla de informacion */
        g2d.setColor(Color.WHITE);        
        /* puntos del jugador */
        g2d.drawString("SCORE: " + this.froggy.score, 20, 370);
        /* vidas */
        int k = 15;
        for (int i = 0; i < this.froggy.lives; i++) {
            g2d.fillRect(680 - k, 340, 10, 10);
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
        this.froggy.alive = 
            this.level.checkMovement(froggy, froggy.dy, froggy.dx);

        if (this.froggy.alive) {
            this.froggy.move();

            if (this.froggy.dy == -1)
                this.froggy.score += 10;            
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

