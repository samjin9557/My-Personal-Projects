import java.util.Scanner;
import mow.*;

public class PartAdemo {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        System.out.println("Please enter the number of rows in the yard (without walls): ");
        int rownum = in.nextInt();
        System.out.println("Please enter the number of columns in the yard (without walls): ");
        int colnumnum = in.nextInt();

        //initialize and print yard
        clearScreen();
        Yard yard = new Yard (rownum, colnumnum);
        yard.printYard(yard);
    }
}
