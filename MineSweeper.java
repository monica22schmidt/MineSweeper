//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Mine Sweeper
// Files: Config.com
// Course: CS 200, Fall, and 2017
//
// Author: Monica Schmidt
// Email: meschmidt6@wisc.edu
// Lecturer's Name: Jim Williams
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Dini Schweikert
// Partner Email: dschweikert@wisc.edu
// Lecturer's Name: Marc Renault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment. X
// ___ We have both read and understand the course Pair Programming Policy. X
// ___ We have registered our team prior to the team registration deadline. X
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: Dini Schweikert
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Random;
import java.util.Scanner;

/**
 * The Mine Sweeper Class creates the game and game play feature of Mine Sweeper. The class creates
 * all the necessary methods to successfully run and play the game.
 *
 * Bugs: (a list of bugs and other problems)
 *
 * @author Monica Schmidt & Dini Schweikert
 */

public class MineSweeper {

    /**
     * This is the main method for Mine Sweeper game! This method contains the within game and play
     * again loops and calls the various supporting methods.
     *
     * @param args (unused)
     */

    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        Random randGen = new Random(Config.SEED);
        System.out.println("Welcome to Mine Sweeper!");
        String playAgain = "y";
        // the while loop replays the game as long as the user doesn't type n or N
        while ((playAgain.contains("y") || playAgain.contains("Y"))
            && !(playAgain.contains("n") || playAgain.contains("N"))) {
            String getWidth = "What width of map would you like (" + Config.MIN_SIZE + " - "
                + Config.MAX_SIZE + "): ";
            String getHeight = "What height of map would you like (" + Config.MIN_SIZE + " - "
                + Config.MAX_SIZE + "): ";
            int userWidth = promptUser(scr, getWidth, Config.MIN_SIZE, Config.MAX_SIZE);
            int userHeight = promptUser(scr, getHeight, Config.MIN_SIZE, Config.MAX_SIZE);
            char[][] userMap = new char[userHeight][userWidth];
            boolean[][] gameMines = new boolean[userHeight][userWidth];
            eraseMap(userMap);
            boolean gameWon = allSafeLocationsSwept(userMap, gameMines);
            System.out.println();
            int numberOfMines = placeMines(gameMines, randGen);
            System.out.println("Mines: " + numberOfMines);
            printMap(userMap);
            // while loop continues as long as game is not won
            while (gameWon == false) {
                String getRow = "row: ";
                String getCol = "column: ";
                int userRow = promptUser(scr, getRow, 1, userMap.length);
                int userColumn = promptUser(scr, getCol, 1, userMap[0].length);
                userRow -= 1;
                userColumn -= 1;
                // if statement executes when user clicks on a mine
                if (gameMines[userRow][userColumn] == true) {
                    showMines(userMap, gameMines);
                    userMap[userRow][userColumn] = Config.SWEPT_MINE;
                    printMap(userMap);
                    System.out.println("Sorry, you lost.");
                    break;
                }
                // when game is not won, if statement sweeps surrounding cells of user input
                if (gameWon == false) {
                    int returnedValue= sweepLocation(userMap, gameMines, userRow, userColumn);
                    if(returnedValue == 0) {
                        sweepAllNeighbors(userMap, gameMines, userRow, userColumn);
                    }
                    gameWon = allSafeLocationsSwept(userMap, gameMines);
                    // if statement prevents the map from printing twice when game is won
                    if (gameWon == false) {
                        System.out.println();
                        System.out.println("Mines: " + numberOfMines); // prints number of mines
                        printMap(userMap);
                    }
                }

                gameWon = allSafeLocationsSwept(userMap, gameMines);
            }
            // if statement executes when user has clicked on everything except the mines
            if (gameWon == true) {
                showMines(userMap, gameMines);
                printMap(userMap);
                System.out.println("You Win!");
            }
            System.out.print("Would you like to play again (y/n)? ");
            playAgain = scr.next();
        }
        // the if statement stops the game when the player types n or N
        if (playAgain.contains("n") || playAgain.contains("N")) {
            System.out.println("Thank you for playing Mine Sweeper!");
        }
    }

    /**
     * This method prompts the user for a number, verifies that it is between min and max,
     * inclusive, before returning the number.
     *
     * If the number entered is not between min and max then the user is shown an error message and
     * given another opportunity to enter a number. If min is 1 and max is 5 the error message is:
     * Expected a number from 1 to 5.
     *
     * If the user enters characters, words or anything other than a valid int then the user is
     * shown the same message. The entering of characters other than a valid int is detected using
     * Scanner's methods (hasNextInt) and does not use exception handling.
     *
     * Do not use constants in this method, only use the min and max passed in parameters for all
     * comparisons and messages. Do not create an instance of Scanner in this method, pass the
     * reference to the Scanner in main, to this method. The entire prompt should be passed in and
     * printed out.
     *
     * @param in The reference to the instance of Scanner created in main.
     * @param prompt The text prompt that is shown once to the user.
     * @param min The minimum value that the user must enter.
     * @param max The maximum value that the user must enter.
     * @return The integer that the user entered that is between min and max, inclusive.
     */

    public static int promptUser(Scanner in, String prompt, int min, int max) {
        int userInput = 0;
        System.out.print(prompt);
        boolean keepGoing = false; // this initializes a boolean in order to enter the loop
        while (keepGoing == false) {
            // if statement executes if user enters an integer
            if (in.hasNextInt()) {
                userInput = in.nextInt();
                // if statement executes if user enters an integer within the range
                if (userInput <= max && userInput >= min) {
                    keepGoing = true;
                }
                // else statement executes if user enters an integer outside the range
                else {
                    System.out.println("Expected a number from " + min + " to " + max + ".");
                    in.nextLine();
                    keepGoing = false;
                }
            }
            // if statement executes if user enters something other than an integer
            else if (in.hasNextLine()) {
                System.out.println("Expected a number from " + min + " to " + max + ".");
                if (in.hasNextInt()) {
                    userInput = in.nextInt();
                    keepGoing = true;
                } else {
                    in.next();
                    in.nextLine();
                    keepGoing = false;
                }
            }
        }
        return userInput;
    }

    /**
     * This initializes the map char array passed in such that all elements have the Config.UNSWEPT
     * character. Within this method only use the actual size of the array. Don't assume the size of
     * the array. This method does not print out anything. This method does not return anything.
     *
     * @param map An allocated array. After this method call all elements in the array have the same
     *        character, Config.UNSWEPT.
     */

    public static void eraseMap(char[][] map) {
        // creates the map of the mine field
        for (int row = 0; row < map.length; row++) {
            // map[0].length is used to get the bounds for the columns
            for (int col = 0; col < map[0].length; col++) {
                map[row][col] = Config.UNSWEPT;
            }
        }
        return;
    }

    /**
     * This prints out a version of the map without the row and column numbers. A map with width 4
     * and height 6 would look like the following: . . . . . . . . . . . . . . . . . . . . . . . .
     * For each location in the map a space is printed followed by the character in the map
     * location.
     *
     * @param map The map to print out.
     */

    public static void simplePrintMap(char[][] map) {
        // prints out the mine field
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                System.out.print(" " + map[row][col]);
            }
            System.out.println();
        }
        return;
    }

    /**
     * This prints out the map. This shows numbers of the columns and rows on the top and left side,
     * respectively. map[0][0] is row 1, column 1 when shown to the user. The first column, last
     * column and every multiple of 5 are shown.
     *
     * To print out a 2 digit number with a leading space if the number is less than 10, you may
     * use: System.out.printf("%2d", 1);
     *
     * @param map The map to print out.
     */

    public static void printMap(char[][] map) {
        // prints map with row and column numbers
        System.out.print(" ");
        // for loop prints out column numbers
        for (int col = 1; col <= map[0].length; col++) {
            if (col < 2) { // prints first column number
                System.out.print("  " + col);
            } else if (col % 5 <= 0 && col != map[0].length) { // prints column number multiple of
                if (col == 5) { // five
                    System.out.print(" " + col);
                } else {
                    System.out.print(col);
                }
            } else if (col > map[0].length - 1) { // prints last column number
                if (col <= 5) {
                    System.out.print(" " + col);
                } else {
                    System.out.print(col);
                }
            } else {
                System.out.print("--"); // prints dashes in place of row numbers
            }
        }
        System.out.println();
        for (int row = 0; row < map.length; row++) {
            if (row + 1 < 2) { // prints first row number
                System.out.print(" " + (row + 1));
                // prints row number multiple of five
            } else if ((row + 1) % 5 <= 0 && ((row + 1) != map[0].length)) {
                if (row <= 9) {
                    System.out.print(" " + (row + 1));
                } else {
                    System.out.print((row + 1));
                }
            } else if ((row + 1) == map.length) { // prints last row number
                if ((row +1) <= 9) {
                    System.out.print(" " + (row + 1));
                } else {
                    System.out.print((row + 1));
                }
            } else { // prints out vertical bar in place of numbers
                System.out.print(" |");
            }
            for (int col = 0; col < map[0].length; col++) {
                System.out.print(" " + map[row][col]); // prints map
            }
            System.out.println();
        }
        return;
    }

    /**
     * This method initializes the boolean mines array passed in. A true value for an element in the
     * mines array means that location has a mine, false means the location does not have a mine.
     * The MINE_PROBABILITY is used to determine whether a particular location has a mine. The
     * randGen parameter contains the reference to the instance of Random created in the main
     * method.
     *
     * Access the elements in the mines array with row then column (e.g., mines[row][col]).
     *
     * Access the elements in the array solely using the actual size of the mines array passed in,
     * do not use constants.
     *
     * A MINE_PROBABILITY of 0.3 indicates that a particular location has a 30% chance of having a
     * mine. For each location the result of randGen.nextFloat() < Config.MINE_PROBABILITY
     * determines whether that location has a mine.
     *
     * This method does not print out anything.
     *
     * @param mines The array of boolean that tracks the locations of the mines.
     * @param randGen The reference to the instance of the Random number generator created in the
     *        main method.
     * @return The number of mines in the mines array.
     */

    public static int placeMines(boolean[][] mines, Random randGen) {
        int numMines = 0;
        // places mines randomly with a set seed
        for (int row = 0; row < mines.length; row++) {
            for (int col = 0; col < mines[row].length; col++) {
                if (randGen.nextFloat() < Config.MINE_PROBABILITY) {
                    mines[row][col] = true;
                    numMines++;
                }
            }
        }
        return numMines;
    }

    /**
     * This method returns the number of mines in the 8 neighboring locations. For locations along
     * an edge of the array, neighboring locations outside of the mines array do not contain mines.
     * This method does not print out anything.
     *
     * If the row or col arguments are outside the mines array, then return -1. This method (or any
     * part of this program) should not use exception handling.
     *
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     * @return The number of mines in the 8 surrounding locations or -1 if row or col are invalid.
     */
    public static int numNearbyMines(boolean[][] mines, int row, int col) {
        int nearbyMines = 0;
        // if statement checks if user input entered is out of bounds
        if (row < 0 || col < 0 || row > mines.length || col > mines[row].length) {
            return -1;
        }
        // checks the surrounding cells of user input for mines
        // rowMod = -1 checks the row before and rowMod <= 1 checks the row after row inputed
        for (int rowMod = -1; rowMod <= 1; rowMod++) {
            // colMod = -1 checks the column before and colMod <= 1 checks the column after column
            // inputed
            for (int colMod = -1; colMod <= 1; colMod++) {
                int newRow = row + rowMod;
                int newCol = col + colMod;
                if (row + rowMod < 0 || row + rowMod > (mines.length - 1)) {
                } else if (col + colMod < 0 || col + colMod > (mines[row].length - 1)) {
                } else if (newCol == col && newRow == row) {
                } else {
                    if (mines[row + rowMod][col + colMod] == true) {
                        nearbyMines++; // counts the number of nearby mines
                    } else {
                    }
                }
            }
        }
        return nearbyMines;
    }

    /**
     * This updates the map with each unswept mine shown with the Config.HIDDEN_MINE character.
     * Swept mines will already be mapped and so should not be changed. This method does not print
     * out anything.
     *
     * @param map An array containing the map. On return the map shows unswept mines.
     * @param mines An array indicating which locations have mines. No changes are made to the mines
     *        array.
     */

    public static void showMines(char[][] map, boolean[][] mines) {
        // shows hidden mines after game is either won or lost
        for (int row = 0; row < mines.length; row++) {
            for (int col = 0; col < mines[row].length; col++) {
                if ((mines[row][col] == true) && !(map[row][col] == Config.NO_NEARBY_MINE)) {
                    map[row][col] = Config.HIDDEN_MINE;
                }
            }
        }
        return;
    }

    /**
     * Returns whether all the safe (non-mine) locations have been swept. In other words, whether
     * all unswept locations have mines. This method does not print out anything.
     *
     * @param map The map showing touched locations that is unchanged by this method.
     * @param mines The mines array that is unchanged by this method.
     * @return whether all non-mine locations are swept.
     */
    public static boolean allSafeLocationsSwept(char[][] map, boolean[][] mines) {
        // checks if all the safe locations on mine field have been swept
        boolean minesNotLocated = false;
        int count = 0; // counts the number of unswept non-mines
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (mines[row][col] == false && map[row][col] == Config.UNSWEPT) {
                    count++;
                }
                if (count > 0) {
                    minesNotLocated = false;
                } else {
                    minesNotLocated = true;
                }
            }
        }
        return minesNotLocated; // if minesNotLocated true, game is won
    }

    /**
     * This method sweeps the specified row and col. -If the row and col specify a location outside
     * the map array then return -3 without changing the map. -If the location has already been
     * swept then return -2 without changing the map. - If there is a mine in the location then the
     * map for the corresponding location is updated with Config.SWEPT_MINE and return -1. - If
     * there is not a mine then the number of nearby mines is determined by calling the
     * numNearbyMines method. - If there are 1 to 8 nearby mines then the map is updated with the
     * characters '1'..'8' indicating the number of nearby mines. - If the location has 0 nearby
     * mines then the map is updated with the Config.NO_NEARBY_MINE character. - Return the number
     * of nearbyMines.
     *
     * @param map The map showing swept locations.
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     * @return the number of nearby mines, -1 if the location is a mine, -2 if the location has
     *         already been swept, -3 if the location is off the map.
     */
    public static int sweepLocation(char[][] map, boolean[][] mines, int row, int col) {
        // if statement returns -3 if user location is out of bounds
        if (row < 0 || col < 0 || row > mines.length || col > mines[row].length) {
            return -3;
        } else if (map[row][col] != Config.UNSWEPT) {
            return -2; // returns -2 if user location has already been swept
        } else if (mines[row][col] == true) {
            return -1;// returns -1 if user location contains a mine
        } else {
            int nearbyMines = numNearbyMines(mines, row, col);
            if (nearbyMines > 0) {
                map[row][col] = Character.forDigit(nearbyMines, 9);
            } else {
                map[row][col] = Config.NO_NEARBY_MINE;
            }
            return nearbyMines; // returns number of nearby mines
        }
    }



    /**
     * This method iterates through all 8 neighboring locations and calls sweepLocation for each. It
     * does not call sweepLocation for its own location, just the neighboring locations.
     *
     * @param map The map showing touched locations.
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     */
    public static void sweepAllNeighbors(char[][] map, boolean[][] mines, int row, int col) {
        for (int rowMod = -1; rowMod <= 1; rowMod++) {
            for (int colMod = -1; colMod <= 1; colMod++) {
                int newRow = row + rowMod;
                int newCol = col + colMod;
                if (newRow < 0 || newRow > (mines.length - 1)) {
                } else if (newCol < 0 || newCol > (mines[row].length - 1)) {
                } else if (newCol == col && newRow == row) {

                } else {
                    sweepLocation(map, mines, newRow, newCol);
                   
                }
            }
        }
        return;
    }
}
