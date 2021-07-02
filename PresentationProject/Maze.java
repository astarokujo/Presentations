

/*
 *Name: Ryan Kanwar
 */

import java.io.*;
//Import for java.io.*

public class Maze {

    static boolean[][] split2 = new boolean[1][1];
    //Static boolean 2D array split2 that holds one row and one col
    //All values in array are set to be false by default 

    public static void main(String[] args) {
        try {
            //Try/catch is used as a requirement to read the maze.txt file
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\rykan\\eclipse-workspace\\test\\src\\test\\Maze2.txt"));
            //Sets up new Buffered reader to read the maze text file from its location       
            String temp = br.readLine();
            //New string temp stores in a line from the file 
            int y = Integer.parseInt(temp);
            //Int Y uses the number that's on the first line of the maze file, so it's converted from a string to an int. Array column number
            temp = br.readLine();
            //Temp stores in the second line from the file
            int x = Integer.parseInt(temp);
            //Int x uses the number that's on the second line of the maze file, so it's converted from a string to an int. Array row number
            temp = br.readLine();
            //Temp now stores in the third line from the file 
            char[][] split = new char[x][y];
            //New 2D char array split is set to have the row size of x, and the column size of y
            split2 = new boolean[x][y];
            //Split2 is also set to have the row size of x, and the column size of y
            int count = 0;
            //New int counter initialized as 0
            while (temp != null) {
                //While loop goes over all of the lines in the maze.txt file and signifies 'null' once it's done reading the entire file        

                for (int i = 0; i < split[count].length; i++) {
                    split[count][i] = temp.charAt(i);
                }
                //For loop goes over every value in the split array. The counter is increased each time the while loop runs to represent the array's row number
                //Each time a line is read from the maze file in the while loop, it stores the string's charAt value 
                count++;
                //Counter increases by one each time the while loop runs
                temp = br.readLine();
                //Each time the while loop runs temp stores in a line from the maze file
            }

            int i = 0;
            //Int i initialized as 0. Used for the recursion call and in for loops
            int j;
            //Int j. Used in for loops
            int start = 0;
            //Int start initialized as 0

            for (j = 0; j < split[i].length; j++) {
                if (split[0][j] == 'S') {
                    start = j;
                }
            }
            //For loop goes over the first row in the split array. If there is an S, then int 'start' will be that value
            recursion(i, start, split);
            //Sends int i's number, int start's number, and the split array to the method named recursion
            //int i is the initial row number, and start is the inital column number

            for (i = 0; i < split2.length; i++) {
                for (j = 0; j < split2[i].length; j++) {
                    //Two for loops - the first one that uses int 'i' goes over the rows of the split2 array. 
                    //The second one that uses int 'j' goes over the columns of each row in the split2 array
                    if (split[i][j] == 'E') {
                        split[i][j] = 'E';
                        //If there is ever an 'E' in the file, which signifies the maze's end, then E will be stored into the split array's loop value
                    } else if (split2[i][j] == true) {
                        split[i][j] = '-';
                    }
                    //Else if, whenever the split2 array holds a value that is set to be true a dash will be stored into the split array's loop value
                    System.out.print("[" + split[i][j] + "]");
                }
                System.out.println();
                //Output for the split array- has brackets around each value, and a System.out.println() to make each row on the next line each time
            }
        } catch (Exception e) {
            System.out.println(e);
            //Catch to find problems in try
        }

    }

    public static boolean recursion(int row, int col, char[][] split) {
        //New method recursion returns a boolean value. Its parameters takes in int i's number 
        //(now as int row), int start's number (now as int col), and the split array
        //The purpose of this method is to signify what pathways of the maze are found to be true
        //when going over the split array, and by setting values to be true/false in the split2 boolean array
        if (split[row][col] == 'E') {
            return true;
        }
        //If any value of the split array is found to be 'E', the method will return 'true' and all the 'O's in the path will be set to true in the split2 array
        if (row > 0) {
            //If the current row number is ever above 0, this set of code will be executed
            if (split2[row - 1][col] == false) {
                //If the value of split2's array (that is one less row than the current row number) is set to be false, this set of code will be executed
                if (split[row - 1][col] == 'O' || split[row - 1][col] == 'E') {
                    split2[row - 1][col] = true;
                    //If the value of split's array (that is one less row than the current row number) is either 'O' or 'E', the split2 value will be set to true
                    if (recursion(row - 1, col, split)) {
                        //If the above has occurred, and the recursion has reached 'E', the method returns true
                        return true;
                    }
                    split2[row - 1][col] = false;
                    //If however the trail of 'O's from the split array don't reach 'E', the current value of split2 will be false 
                }
            }
        }
        if (row < split.length - 1) {
            //If split's length number is ever above the current row number, this set of code will be executed
            if (split2[row + 1][col] == false) {
                //If the value of split2's array (that is one more row than the current row number) is set to be false, this set of code will be executed
                if (split[row + 1][col] == 'O' || split[row + 1][col] == 'E') {
                    split2[row + 1][col] = true;
                    //If the value of split's array (that is one more row than the current row number) is either 'O' or 'E', the split2 value will be set to true
                    if (recursion(row + 1, col, split)) {
                        //If the above has occurred, and the recursion has reached 'E', the method returns true
                        return true;
                    }
                    split2[row + 1][col] = false;
                    //If however the trail of 'O's from the split array don't reach 'E', the current value of split2 will be false 
                }
            }
        }
        if (col > 0) {
            //If the current column number is ever above 0, this set of code will be executed
            if (split2[row][col - 1] == false) {
                //If the value of split2's array (that is one less column than the current column number) is set to be false, this set of code will be executed
                if (split[row][col - 1] == 'O' || split[row][col - 1] == 'E') {
                    //If the value of split's array (that is one less column than the current column number) is either 'O' or 'E', the split2 value will be set to true
                    split2[row][col - 1] = true;
                    if (recursion(row, col - 1, split)) {
                        //If the above has occurred, and the recursion has reached 'E', the method returns true
                        return true;
                    }
                    split2[row][col - 1] = false;
                    //If however the trail of 'O's from the split array don't reach 'E', the current value of split2 will be false 
                }
            }
        }
        if (col < split[row].length - 1) {
            //If split's current row length number is ever above the current column number, this set of code will be executed
            if (split2[row][col + 1] == false) {
                //If the value of split2's array (that is one more column than the current column number) is set to be false, this set of code will be executed
                if (split[row][col + 1] == 'O' || split[row][col + 1] == 'E') {
                    //If the value of split's array (that is one more column than the current column number) is either 'O' or 'E', the split2 value will be set to true
                    split2[row][col + 1] = true;
                    if (recursion(row, col + 1, split)) {
                        //If the above has occurred, and the recursion has reached 'E', the method returns true
                        return true;
                    }
                    split2[row][col + 1] = false;
                    //If however the trail of 'O's from the split array don't reach 'E', the current value of split2 will be false 
                }
            }
        }
        return false;
        //The method returns false as its return statement, since the boolean method requires a boolean return statement
    }
}

