package Animate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import sound.SoundClip;

public class Cannon {

    //initialize variables
    private static final int B_HEIGHT = 900;
    
    //initialize the position and direction values of the cannon
    private double x;
    private double y;
    private double angle;
    private double v_initial;

    //set limites for the maximum angles the cannon can rotate (0-90 degrees)
    private static final double MIN_ANGLE = 0;
    private static final double MAX_ANGLE = -90;

    //cannon dimensions
    final int CANNON_HEIGHT = 51;
    final int CANNON_WIDTH = 124;
    final int X_PIVOT = 15;
    final int Y_PIVOT = CANNON_HEIGHT / 2;

    //load the image
    BufferedImage Cannon;

    //constructor
    public Cannon() {
        try {
            String path = "Media/sm_cannon.png";
            File imageFile = new File(path);
            Cannon = ImageIO.read(imageFile);
        } catch (Exception e) {
            System.err.println("Unable to load the cannon image");
        }
        this.x = 60 - X_PIVOT;
        this.y = 900 - 60 - Y_PIVOT;
        this.angle = -45;
        this.v_initial = 33; //I picked this number so that the cannon ball never leaves my screen (could be different depending on the aspect ratio of your scree)
    }

    
    //methods to get and set the cannon position
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }

    //methods to get angle and rotate the cannon
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        if (angle <= MAX_ANGLE || angle >= MIN_ANGLE) {
            this.angle = angle;
        } else {
            System.out.println("The cannon is at its max/min angle, unable to rotate further.");
        }
    }

    //methdos to get and set the initial velocity
    public double getMuzzleVelocity() {
        return v_initial;
    }

    public void setMuzzleVelocity(double new_velocity) {
        this.v_initial = new_velocity;
    }

    //methods to play sound effects
    public void playWheelSound() {
        try {
            String path = "Media/wheel.wav";
            SoundClip wheelSound = new SoundClip(path);
            wheelSound.open();
            wheelSound.play();
        } catch (Exception e) {
            System.err.println("Failed to open sound file: wheel.wav");
        }
    }
    
    public void playCannonSound() {
        try {
            String path = "Media/cannon.wav";
            SoundClip cannonSound = new SoundClip(path);
            cannonSound.open();
            cannonSound.play();
        } catch (Exception e) {
            System.err.println("Failed to open sound file: wheel.wav");
        }
    }

    //Methods to rotate the cannon
    public void rotateClockwise() {
        playWheelSound();
        for (int i = 0; i < 30; i++) {
            angle = angle + 0.167;
        if (angle < MAX_ANGLE) {
            angle = MAX_ANGLE;
        } else if (angle > MIN_ANGLE) {
            angle = MIN_ANGLE;
        }
        }
    }

    public void rotateCounterClockwise() {
        playWheelSound();
        for (int i = 0; i < 30; i++) {
            angle = angle - 0.167;
        if (angle > MIN_ANGLE) {
            angle = MIN_ANGLE;
        } else if (angle < MAX_ANGLE) {
            angle = MAX_ANGLE;
        }
        } 
    }
    
    //method to draw the cannon
    public void drawCannon(Graphics2D g2d) {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(Math.toRadians(angle), X_PIVOT, Y_PIVOT);

        // draw the cannon
        g2d.drawImage(Cannon, transform, null);

        //draw the pink base
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(new int[] { 40, 60, 80 }, new int[] { B_HEIGHT - 20, B_HEIGHT - 60, B_HEIGHT - 20 }, 3);
        g2d.setColor(Color.PINK);
        g2d.fillPolygon(new int[] { 40, 60, 80 }, new int[] { B_HEIGHT - 20, B_HEIGHT - 60, B_HEIGHT - 20 }, 3);

        //draw the blue circle
        g2d.setColor(Color.BLUE);
        g2d.fillOval(60 - (10 / 2), B_HEIGHT - 60 - (10 / 2), 10, 10);
    }
    
    //method to fire the cannon
    public void fireCannonBall(Cannonball cannonball) {
        System.out.println("The cannon is fired");

        //play sound effect
        if (cannonball.getState() != Cannonball.STATE.FLYING) {
            playCannonSound();
        }

        //fire the ball
        cannonball.launch((x + X_PIVOT) + (100 * Math.cos(Math.toRadians(angle))) - (20/2), (y + Y_PIVOT) + (100 * Math.sin(Math.toRadians(angle))) - (20/2), Math.cos(Math.toRadians(angle)) * v_initial, Math.sin(Math.toRadians(angle)) * v_initial);
    }

}
