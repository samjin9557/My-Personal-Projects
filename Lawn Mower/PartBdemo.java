import mow.*;

public class PartBdemo {
    //clear screen function
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //sleep function
    public static void delay(long mseconds) {
        try {
            Thread.sleep(mseconds);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException received!");
        }
    }
    public static void main(String[] args) {
        clearScreen();
        //construct the yard to be 24 columns wide, 5 rows tall
        Yard yard = new Yard(5, 26);

        //construct the mower to default position (1,1), facing up
        Mower mower = new Mower();

        //reset to mower to row 3, column 1 in the yard, pointing right
        mower.turnRight();
        mower.setPosition(3, 1);

        
        //start to cut grass
        while (true) {
            if (mower.detectGrass(yard) == true) {
                mower.cutGrass(yard);
                yard.printMower(mower);
                mower.moveForward();
                delay(700);
                clearScreen();
            } else {
                break;
            }
        }
        yard.printMower(mower);
    }
}