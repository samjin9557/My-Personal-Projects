package Animate;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;


public class Board extends JPanel implements KeyListener {

    //initialize variables
    private static final int B_WIDTH = 1600;
    private static final int B_HEIGHT = 900;
    private final int FLOOR = B_HEIGHT - 25;
    private final int G_BOARD = 1;

    //declare cannon and the Cannon ball object
    private Cannon cannon = new Cannon();
    private Cannonball cannonball = new Cannonball(0, G_BOARD, FLOOR);

    //utility timer to track and update the cannonball
    Timer timer;
    private final int DELAY = 100;
    private final int INTERVAL = 20;

    //constructor
    public Board() {
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer();
        timer.scheduleAtFixedRate(new cannonBallUpdate(), DELAY, INTERVAL);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //draw our virtual ground
        g.setColor(Color.GREEN);
        g.fillRect(0, FLOOR, B_WIDTH, B_HEIGHT - FLOOR);

        //draw the horizontal black line on the ground
        g.setColor(Color.BLACK);
        g.drawLine(0, FLOOR, B_WIDTH, FLOOR);

        //draw cannon
        cannon.drawCannon(g2d);
        cannonball.draw(g2d);

        //display information
        Graphics text = (Graphics2D) g;
        text.setColor(Color.BLACK);
        text.setFont(new Font("Georgia", Font.PLAIN, 18));
        text.drawString("Press left/right arrow key to adjust cannon angle", 50, 50);
        text.drawString("Press space to fire cannon", 50, 75);
        text.drawString("Current angle " + String.format("%.2f", -1 * cannon.getAngle()) + " degrees", 50, 100);
        text.drawString("Current time: " + String.format("%.2f", cannonball.getTimeScale()) + "s", 50, 125);
    }

    //declare methods under the KeyListener class
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            System.out.println("The spacebar was pressed");
            //call upon fire method
            cannon.fireCannonBall(cannonball);
        } else if (e.getKeyCode() == 37) {
            System.out.println("The left arrow key was pressed");
            //call upon rotatecounterclockwise method
            cannon.rotateCounterClockwise();
            repaint();
        } else if (e.getKeyCode() == 39) {
            System.out.println("The right arrow key was pressed");
            //call upon rotataclockwise method
            cannon.rotateClockwise();
            repaint();
        } else if (e.getKeyCode() == 38) {
            System.out.println("The up arrow key was pressed");
        } else if (e.getKeyCode() == 40) {
            System.out.println("The down arrow key was pressed");
        }
    }

    public void keyReleased(KeyEvent e) {
        //no need to declare anything
    }

    public void keyTyped(KeyEvent e) {
        //no need to declare anything
    }

    //method to update the ball
    public class cannonBallUpdate extends TimerTask {
        public void run() {
            cannonball.updateBall();
            repaint();
        }
    }
}
