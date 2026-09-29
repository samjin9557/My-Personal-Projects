import java.util.Scanner;
import mow.*;

public class PartCdemo {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void delay(long mseconds) {
        try {
            Thread.sleep(mseconds);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException received!");
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        System.out.println("Please enter the number of rows in the yard (without walls): ");
        int rownum = in.nextInt();
        System.out.println("Please enter the number of columns in the yard (without walls): ");
        int colnumnum = in.nextInt();

        //initialize yard
        clearScreen();
        Yard yard = new Yard(rownum, colnumnum);

        //initialize mower
        Mower mower = new Mower();

        //randomize mower
        mower.randomizePosition(yard);
        
        //cut the corner it's placed
        mower.cutGrass(yard);

        //forever loop to cut

        while (true) {
            if (mower.senseValue(yard) == '+') {
                mower.cutGrass(yard);
                yard.printMower(mower);
                mower.moveForward();
                delay(700);
                clearScreen();
                continue;
            } else {
                mower.turnRight();
                if (mower.senseValue(yard) == '+') {
                    mower.cutGrass(yard);
                    yard.printMower(mower);
                    mower.moveForward();
                    delay(700);
                    clearScreen();
                    continue;
                } else {
                    mower.turnRight();
                    if (mower.senseValue(yard) == '+') {
                        mower.cutGrass(yard);
                        yard.printMower(mower);
                        mower.moveForward();
                        delay(700);
                        clearScreen();
                        continue;
                    } else {
                        mower.turnRight();
                        if (mower.senseValue(yard) == '+') {
                            mower.cutGrass(yard);
                            yard.printMower(mower);
                            mower.moveForward();
                            delay(700);
                            clearScreen();
                            continue;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        while (true) {
            if (mower.senseValue(yard) == '+') {
                mower.cutGrass(yard);
                yard.printMower(mower);
                mower.moveForward();
                delay(700);
                clearScreen();
                continue;
            } else {
                mower.turnRight();
                if (mower.senseValue(yard) == '+') {
                    mower.cutGrass(yard);
                    yard.printMower(mower);
                    mower.moveForward();
                    delay(700);
                    clearScreen();
                    continue;
                } else {
                    mower.turnRight();
                    if (mower.senseValue(yard) == '+') {
                        mower.cutGrass(yard);
                        yard.printMower(mower);
                        mower.moveForward();
                        delay(700);
                        clearScreen();
                        continue;
                    } else {
                        mower.turnRight();
                        if (mower.senseValue(yard) == '+') {
                            mower.cutGrass(yard);
                            yard.printMower(mower);
                            mower.moveForward();
                            delay(700);
                            clearScreen();
                            continue;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        yard.printMower(mower);
    }
}