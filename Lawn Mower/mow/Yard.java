package mow;

public class Yard {
    //Initialize variables and 2D array
    private int row;
    private int column;
    private char[][] yard;
    private int wall_row;
    private int wall_column;

    //Constructor
    public Yard(int row, int column) {

        //grass covered part of the lawn
        this.row = row;
        this.column = column;

        //calculate the row and column of lawn with wall included
        wall_row = row + 2;
        wall_column =  column + 2;

        //initialize the array to draw the yard
        yard = new char[wall_row][wall_column];


        //draw the top wall
        for (int i = 0; i < wall_column; i++){
            yard [0][i] = 'R';
        }

        //draw the middle for portion with wall and grass
        for (int i = 1; i < yard.length; i++){
            for (int j = 0; j < yard[0].length; j++){
                if (j == 0 || j == wall_column - 1){ 
                    yard[i][j] = 'R';
                } else {
                    yard[i][j] = '+';
                }
            }
        }

        //draw the bottom wall
        for (int i = 0; i < wall_column - 1; i++){
            yard [wall_row-1][i] = 'R';
        }
    }

    //method to return the value of the character in a certain position
    public char getValue (int x, int y){
        return yard[x][y];
    }

    //method to override the value of a given position
    public void override (int x, int y, char input) {
        yard [x][y] = input;
    }

    //method to return the row of the yard
    public int getrow (Yard yard) {
        return wall_row;
    }

    //method to return the column of the yard
    public int getcolumn (Yard yard) {
        return wall_column;
    }

    //method to print the yard
    public void printYard(Yard yard) {
        for (int i = 0; i < wall_row; i++) {
            for (int j = 0; j < wall_column; j++) {
                System.out.print(this.yard[i][j]);
            }
            System.out.println("");
        }
    }
    
    //method to print the yard with the mower
    public void printMower(Mower mower) {
        for (int i = 0; i < wall_row; i++) {
            for (int j = 0; j < wall_column; j++) {
                if (i == mower.getRow() && j == mower.getcolumn()) {
                    if (mower.getDirection() == 0) {
                        System.out.print("↑");
                    } else if (mower.getDirection() == 1) {
                        System.out.print("→");
                    } else if (mower.getDirection() == 2) {
                        System.out.print("↓");
                    } else if (mower.getDirection() == 3) {
                        System.out.print("←");
                    }
                } else {
                    System.out.print(this.yard[i][j]);
                }
            }
            System.out.println("");
        }
    }
}