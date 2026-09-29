package Animate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import sound.SoundClip;

public class Cannonball {
    //initialize varibles
    private double x;
    private double y;
    private double v_x;
    private double v_y;
    private double a_x;
    private double a_y;
    private double timeElapsed;
    private double ground;
    private STATE state = STATE.IDLE;

    //variables for the flame animation
    private BufferedImage[] flameFrames = new BufferedImage[4];
    private int currentFrame = 0;
    private int frameDelay = 5;
    private int frameCounter = 0;
    private boolean loadedFrame = false;

    boolean explosionPlayed = false;

    private Clip burningSoundClip = null;

    //enumeration of the cannon state
    public enum STATE {
        IDLE,
        FLYING,
        EXPLODING
    }

    public Cannonball(double ax, double ay, double ground) {
        // public constructor for CannonBall class.
        // takes the acceleration rates (x and y) and the location of the ground (as a
        // double)
        // as arguments
        this.a_x = ax;
        this.a_y = ay;
        this.ground = ground;
    }
    
    //methods to load flame frames
    private void loadFlameFrames() {
        for (int i = 0; i < 4; i++) {
            flameFrames[i] = loadImage("Media/flame0" + (i + 1) + ".png");
        }
        loadedFrame = true;
    }

    private BufferedImage loadImage(String path) {
        // loads a buffered image (for the flame animation).
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (Exception e) {
            System.err.println("falled to load one or more flame image(s)");
        }
        return img;
    }
    
    //methods to play explosion sound
    public void playExplosionSound() {
        try {
            String path = "Media/Explosion.wav";
            SoundClip explosionSound = new SoundClip(path);
            explosionSound.open();
            explosionSound.play();
        } catch (Exception e) {
            System.err.println("Failed to open sound file: Explosion.wav");
        }
    }

    public void playFireSound() {
        try {
            String path = "Media/fireburning.wav";
            SoundClip burningSound = new SoundClip(path);
            burningSound.open();
            burningSound.play();

            burningSoundClip = burningSound.getClip();
        } catch (Exception e) {
            System.err.println("Failed to open sound file: Explosion.wav");
        }
    }

    public void stopBurningSound() {
        if (burningSoundClip != null && burningSoundClip.isRunning()) {
        burningSoundClip.stop();
        burningSoundClip.close();
        burningSoundClip = null;
    }
    }
    /*
     * The draw method is called by the Board object
     * and is used to paint the current location and state of the ball.
     * If the ball is flying through the air, the ball is drawn as a red
     * filled in circle. If the ball is in the exploded state, a flame image is
     * drawn. If the ball is idle, then the ball is not drawn at all
     * (since we assume the ball is hidden inside the cannon).
     */
    public void draw(Graphics2D g2d) {
        //when the ball is flying
        if (state == STATE.FLYING) {
            g2d.setColor(Color.RED);
            g2d.fillOval((int) x, (int) y, 15, 15);
        } else if (state == STATE.EXPLODING) { //after the ball explodes
            if (loadedFrame != true) {
                loadFlameFrames();
            }

            AffineTransform transform = new AffineTransform();
            transform.translate(x, y - 34);
            g2d.drawImage(flameFrames[currentFrame], transform, null);
        }
    }

    /*
     * The updateBall() method uses the constant acceleration equations
     * to update the velocity and position of the ball each timer interval.
     * note that when calculating the new velocity, the acceleration term is divided
     * by the
     * time scale in case the user wants to slow down the animation.
     * Similarly, when calculating the new position, the velocity term is divided by
     * the time scale
     * in case the user wants to slow down the animation.
     */
    public void updateBall() {
        if (state == STATE.FLYING) {
            v_x = v_x + a_x;
            v_y = v_y + a_y;
            x = x + v_x;
            y = y + v_y;

            timeElapsed = timeElapsed + 0.02;
        }

        //detect if the ball hits the ground
        if (y >= ground) {
            y = ground;
            state = STATE.EXPLODING;
            while (explosionPlayed == false) {
                playExplosionSound();
                playFireSound();
                explosionPlayed = true;
            }
        }

        if (state == STATE.EXPLODING) {
            frameCounter = frameCounter + 1;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrame = (currentFrame + 1) % 4;
            }
        }
    }

    /*
     * If the ball is not flying through the air, the launch() method
     * will change the state to FLYING and start the ball moving from position (x,y)
     * with the inital velocity of (vx, vy).
     */
    public void launch(double x, double y, double vx, double vy) {
        if (state != STATE.FLYING) {
            state = STATE.FLYING;
            stopBurningSound();
            this.x = x;
            this.y = y;
            this.v_x = vx;
            this.v_y = vy;
            this.timeElapsed = 0;
            explosionPlayed = false;
        }
    }

    /*
     * Get/set methods for the private member variables.
     */
    public STATE getState() {
        return state;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVX() {
        return v_x;
    }

    public double getVY() {
        return v_y;
    }

    public double getAX() {
        return a_x;
    }

    public double getAY() {
        return a_y;
    }

    public double getTimeScale() {
        return timeElapsed;
    }

    public double getGround() {
        return ground;
    }

    public void setState(STATE newState) {
        this.state = newState;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVX(double vx) {
        this.v_x = vx;
    }

    public void setVY(double vy) {
        this.v_y = vy;
    }

    public void setAX(double ax) {
        this.a_x = ax;
    }

    public void setAY(double ay) {
        this.a_y = ay;
    }

    public void setTimeScale(double timeScale) {
        this.timeElapsed = timeScale;
    }

    public void changeTimeScale(double delta) {
        this.timeElapsed = timeElapsed + delta;
    }

    public void setGround(double ground) {
        this.ground = ground;
    }
}
