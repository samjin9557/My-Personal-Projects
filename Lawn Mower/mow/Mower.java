package mow;

import java.util.Random;

public class Mower {
    private int row_mower;
    private int column_mower;
    private int direction; //max is 3

    //constuctor
    public Mower(int row, int column, int direction){
        this.row_mower = row;
        this.column_mower = column;
        this.direction = direction;
    }

    //if the user has no input, then the mower's postion is set to (1,1) and pointing up
    public Mower(){
        this.row_mower = 1;
        this.column_mower = 1;
        this.direction = 0;
    }

    //methods

    //method to reset the position
    public void setPosition (int newrow, int newcol){
        this.row_mower = newrow;
        this.column_mower = newcol;
    }

    //methods to get the position
    public int getRow() {
        return row_mower;
    }

    public int getcolumn() {
        return column_mower;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int newdirection) {
        if (this.direction > 3){
            System.out.println("Invalid direction input");
        } else {
            this.direction = newdirection;
        }
    }
    
    //methods to allow the mower to move forward based on its current direction
    public void moveForward() {
        if (direction == 0) { //move up
            row_mower = row_mower - 1;
        } else if (direction == 1) { //move right
            column_mower = column_mower + 1;
        } else if (direction == 2) { //move down
            row_mower = row_mower + 1;
        } else if (direction == 3) { //move left
            column_mower = column_mower - 1;
        }
    }

    //method to detect grass in front of mower
    public boolean detectGrass(Yard yard) {
        if (direction == 0) {
            if (yard.getValue(row_mower - 1, column_mower) == '+') {
                return true;
            } else {
                return false;
            }
        } else if (direction == 1) {
            if (yard.getValue(row_mower, column_mower + 1) == '+') {
                return true;
            } else {
                return false;
            }
        } else if (direction == 2) {
            if (yard.getValue(row_mower + 1, column_mower) == '+') {
                return true;
            } else {
                return false;
            }
        } else if (direction == 3) {
            if (yard.getValue(row_mower, column_mower - 1) == '+') {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    //method to cut the grass (override value with space)
    public void cutGrass(Yard yard) {
        yard.override(row_mower, column_mower, ' ');
    }

    //methods to turn left/right
    public void turnRight() {
        if (direction < 3) {
            direction = direction + 1;
        } else if (direction == 3) {
            direction = 0;
        }
    }

    public void turnLeft() {
        if (direction > 0) {
            direction = direction - 1;
        } else if (direction == 0) {
            direction = 3;
        }
    }
    
    //sensor method
    public char senseValue(Yard yard) {
        if (direction == 0) {
            if (yard.getValue(row_mower - 1, column_mower) == '+') {
                return '+';
            } else if (yard.getValue(row_mower - 1, column_mower) == 'R') {
                return 'R';
            } else {
                return ' ';
            }
        } else if (direction == 1) {
            if (yard.getValue(row_mower, column_mower + 1) == '+') {
                return '+';
            } else if (yard.getValue(row_mower, column_mower + 1) == 'R') {
                return 'R';
            } else {
                return ' ';
            }
        } else if (direction == 2) {
            if (yard.getValue(row_mower + 1, column_mower) == '+') {
                return '+';
            } else if (yard.getValue(row_mower + 1, column_mower) == '+') {
                return 'R';
            } else {
                return ' ';
            }
        } else if (direction == 3) {
            if (yard.getValue(row_mower, column_mower - 1) == '+') {
                return '+';
            } else if (yard.getValue(row_mower, column_mower - 1) == '+') {
                return 'R';
            } else {
                return ' ';
            }
        } else {
            return ' ';
        }
    }

    //method to place the mower on a random corner and on a random direction
    public void randomizePosition(Yard yard) {
        Random generator = new Random();
        int corner_pos = generator.nextInt(0, 4);
        //0 = topleft
        //1 = topright
        //2 = botleft
        //3 = botright
        if (corner_pos == 0) {
            row_mower = 1;
            column_mower = 1;
        } else if (corner_pos == 1) {
            row_mower = 1;
            column_mower = yard.getcolumn(yard) - 2;
        } else if (corner_pos == 2) {
            row_mower = yard.getrow(yard) - 2;
            column_mower = 1;
        } else if (corner_pos == 3) {
            row_mower = yard.getrow(yard) - 2;
            column_mower = yard.getcolumn(yard) - 2;
        }
        int random_direction = generator.nextInt(0,4);
        //0 = up
        //1 = right
        //2 = down
        //3 = left
        if (random_direction == 0) {
            this.direction = 0;
        } else if (random_direction == 1) {
            this.direction = 1;
        } else if (random_direction == 2) {
            this.direction = 2;
        } else if (random_direction == 3) {
            this.direction = 3;
        }
    }

    //method to check if there is remaining grass
    public boolean checkGrass(Yard yard) {
        for (int i = 0; i < yard.getrow(yard); i++) {
            for (int j = 0; j < yard.getcolumn(yard); j++) {
                if (yard.getValue(i, j) == '+') {
                    return true;
                }
            }
        }
        return false;
    }

    //method to check unmowed spots
    public boolean updateMower(Yard yard) {
        for (int i = 0; i < yard.getrow(yard); i++) {
            for (int j = 0; j < yard.getcolumn(yard); j++) {
                if (yard.getValue(i, j) == '+') {
                    //four while loops to align the mower with the grass
                    //if the mower is under the grass
                    while (row_mower > j){
                        direction = 0;
                        moveForward();
                    }
                    //if the mower is above the grass
                    while (row_mower < j){
                        direction = 2;
                        moveForward();
                    }
                    //if the mower is left of the grass
                    while (column_mower < i){
                        direction = 1;
                        moveForward();
                    }
                    //if the mover is right of the grass
                    while (column_mower > i) {
                        direction = 3;
                        moveForward();
                    }
                    return true;
                }
            }
        }
        return false; //once the mower is at position
    }
}