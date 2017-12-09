/**
 * This file contains testing methods for the MineSweeper project. These methods are intended to
 * serve several objectives: 1) provide an example of a way to incrementally test your code 2)
 * provide example method calls for the MineSweeper methods 3) provide examples of creating,
 * accessing and modifying arrays
 * 
 * Toward these objectives, the expectation is that part of the grade for the MineSweeper project is
 * to write some tests and write header comments summarizing the tests that have been written.
 * Specific places are noted with FIXME but add any other comments you feel would be useful.
 * 
 * Some of the provided comments within this file explain Java code as they are intended to help you
 * learn Java. However, your comments and comments in professional code, should summarize the
 * purpose of the code, not explain the meaning of the specific Java constructs.
 * 
 */

import java.util.Random;
import java.util.Scanner;


/**
 * This class contains a few methods for testing methods in the MineSweeper class as they are
 * developed. These methods are all private as they are only intended for use within this class.
 * 
 * @author Jim Williams
 * @author FIXME add your name here when you add tests and comment the tests
 *
 */
public class TestMineSweeper {

    /**
     * This is the main method that runs the various tests. Uncomment the tests when you are ready
     * for them to run.
     * 
     * @param args (unused)
     */
    public static void main(String[] args) {

        // Milestone 1
        // testing the main loop, promptUser and simplePrintMap, since they have
        // a variety of output, is probably easiest using a tool such as diffchecker.com
        // and comparing to the examples provided.
         testEraseMap();

        // Milestone 2
        testPlaceMines();
        testNumNearbyMines();
        testShowMines();
        testAllSafeLocationsSwept();

        // Milestone 3
         testSweepLocation();
         testSweepAllNeighbors();
        // testing printMap, due to printed output is probably easiest using a
        // tool such as diffchecker.com and comparing to the examples provided.
    }

    /**
     * This is intended to run some tests on the eraseMap method. 
     * 1. This test create a empty studentmap. It calls erase map from the minesweeper class, 
     * then checks to see if each value is equal to Congfig.UNSWEPT. If it is not the test has 
     * failed and it exits the loop. If it never fails and there is no error.
     * 2.Test two checks a smaller array to make sure everything is initialized correctly
     * 3. Test three checks a larger map to make sure everything is initialized correctly
     * 4. Test four texts and array that is not a square to make sure everything is initialized correctly 
     */
    private static void testEraseMap() {
        // Review the eraseMap method header carefully and write
        // tests to check whether the method is working correctly.
        boolean error = false;
        char[][] studentMap = new char[5][5];
        MineSweeper.eraseMap(studentMap);
        for (int row = 0; row < studentMap.length; ++row) {
            for (int col = 0; col < studentMap.length; ++col) {
                if (studentMap[row][col] != Config.UNSWEPT) {
                    error = true;
                    System.out.println("testEraseMap 1: map not initialized correctly");
                    break;
                }
            }
        }
        char[][] studentMap2 = new char[3][3];
        MineSweeper.eraseMap(studentMap2);
        for (int row = 0; row < studentMap.length; ++row) {
            for (int col = 0; col < studentMap.length; ++col) {
                if (studentMap[row][col] != Config.UNSWEPT) {
                    error = true;
                    System.out.println("testEraseMap 2: map not initialized correctly");
                    break;
                }
            }
        }
        char[][] studentMap3 = new char[15][15];
        MineSweeper.eraseMap(studentMap3);
        for (int row = 0; row < studentMap.length; ++row) {
            for (int col = 0; col < studentMap.length; ++col) {
                if (studentMap[row][col] != Config.UNSWEPT) {
                    error = true;
                    System.out.println("testEraseMap 3: map not initialized correctly");
                    break;
                }
            }
        }
        char[][] studentMap4 = new char[17][16];
        MineSweeper.eraseMap(studentMap4);
        for (int row = 0; row < studentMap.length; ++row) {
            for (int col = 0; col < studentMap.length; ++col) {
                if (studentMap[row][col] != Config.UNSWEPT) {
                    error = true;
                    System.out.println("testEraseMap 4: map not initialized correctly");
                    break;
                }
            }
        }
        if (error) {
            System.out.println("testEraseMap: failed");
        } else {
            System.out.println("testEraseMap: passed");
        }
    }

    /*
     * Calculate the number of elements in array1 with different values from those in array2
     */
    private static int getDiffCells(boolean[][] array1, boolean[][] array2) {
        int counter = 0;
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array1[i].length; j++) {
                if (array1[i][j] != array2[i][j])
                    counter++;
            }
        }
        return counter;
    }

    /**
     * This runs some tests on the placeMines method. 
     * 1. The first test checks to see if the number of random generated mines 
     * are the same between expected map and student map
     * 2.The second test calls getDiffCells in order to check if the objects
     * in the iterated positions are the same between the two arrays.
     */
    private static void testPlaceMines() {
        boolean error = false;

        // These expected values were generated with a Random instance set with
        // seed of 123 and MINE_PROBABILITY is 0.2.
        boolean[][] expectedMap = new boolean[][] {{false, false, false, false, false},
            {false, false, false, false, false}, {false, false, false, true, true},
            {false, false, false, false, false}, {false, false, true, false, false}};
        int expectedNumMines = 3;

        Random studentRandGen = new Random(123);
        boolean[][] studentMap = new boolean[5][5];
        int studentNumMines = MineSweeper.placeMines(studentMap, studentRandGen);

        if (studentNumMines != expectedNumMines) {
            error = true;
            System.out.println("testPlaceMines 1: expectedNumMines=" + expectedNumMines
                + " studentNumMines=" + studentNumMines);
        }
        int diffCells = getDiffCells(expectedMap, studentMap);
        if (diffCells != 0) {
            error = true;
            System.out.println("testPlaceMines 2: mine map differs.");
        }

        // Can you think of other tests that would make sure your method works correctly?
        // if so, add them.

        if (error) {
            System.out.println("testPlaceMines: failed");
        } else {
            System.out.println("testPlaceMines: passed");
        }

    }

    /**
     * This runs some tests on the numNearbyMines method. 
     * 1. After numNearbyMines is called with the boolean and the row and col
     * it checks to see if the expected amount of mines is returned. If it is not it 
     * has an error
     * 2. The second test does the same just with different inputs to check for exceptions
     * 3.The third test checks for the corner case in order to make sure the out of bounds
     * positions don't cause an error.
     */
    private static void testNumNearbyMines() {
        boolean error = false;

        boolean[][] mines = new boolean[][] {{false, false, true, false, false},
            {false, false, false, false, false}, {false, true, false, true, true},
            {false, false, false, false, false}, {false, false, true, false, false}};
        int numNearbyMines = MineSweeper.numNearbyMines(mines, 1, 1);

        if (numNearbyMines != 2) {
            error = true;
            System.out.println(
                "testNumNearbyMines 1: numNearbyMines=" + numNearbyMines + " expected mines=2");
        }

        numNearbyMines = MineSweeper.numNearbyMines(mines, 2, 1);

        if (numNearbyMines != 0) {
            error = true;
            System.out.println(
                "testNumNearbyMines 2: numNearbyMines=" + numNearbyMines + " expected mines=0");
        }

        // Can you think of other tests that would make sure your method works correctly?
        // if so, add them.
        numNearbyMines = MineSweeper.numNearbyMines(mines, 4, 4);

        if (numNearbyMines != 0) {
            error = true;
            System.out.println(
                "testNumNearbyMines 3: numNearbyMines=" + numNearbyMines + " expected mines=3");
        }

        if (error) {
            System.out.println("testNumNearbyMines: failed");
        } else {
            System.out.println("testNumNearbyMines: passed");
        }
    }

    /**
     * This runs some tests on the showMines method. 
     * 1. Test one checks to see if the object in the positions given are all hidden mines 
     * in order to match the boolean mines if they are not there is an error
     * 2.Test two checks to make sure the objects in the positions given are not mines in order
     * to make boolean mines if they are not there is an error.
     */
    private static void testShowMines() {
        boolean error = false;


        boolean[][] mines = new boolean[][] {
            {false, false, true, false, false},
            {false, false, false, false, false}, 
            {false, true, false, false, false},
            {false, false, false, false, false}, 
            {false, false, true, false, false}};

        char[][] map = new char[mines.length][mines[0].length];
        map[0][2] = Config.UNSWEPT;
        map[2][1] = Config.UNSWEPT;
        map[4][2] = Config.UNSWEPT;

        MineSweeper.showMines(map, mines);
        if (!(map[0][2] == Config.HIDDEN_MINE && map[2][1] == Config.HIDDEN_MINE
            && map[4][2] == Config.HIDDEN_MINE)) {
            error = true;
            System.out.println("testShowMines 1: a mine not mapped");
        }
        if (map[0][0] == Config.HIDDEN_MINE || map[0][4] == Config.HIDDEN_MINE
            || map[4][4] == Config.HIDDEN_MINE) {
            error = true;
            System.out.println("testShowMines 2: unexpected showing of mine.");
        }

        // Can you think of other tests that would make sure your method works correctly?
        // if so, add them.

        if (error) {
            System.out.println("testShowMines: failed");
        } else {
            System.out.println("testShowMines: passed");
        }
    }

    /**
     * This is intended to run some tests on the allSafeLocationsSwept method. 
     * 1. Test one checks to see if allSafeLocationsSwept returns true when a 
     * specific boolean and char arrays if the function works the 
     * function should return false
     * 2.Test two checks to see if allSafeLocationsSwept returns true when a 
     * specific boolean and char arrays the function works the 
     * function should return true
     */
    private static void testAllSafeLocationsSwept() {
        // Review the allSafeLocationsSwept method header carefully and write
        // tests to check whether the method is working correctly.
       
        boolean error = false;


        boolean[][] mines = new boolean[][] {
            {false, false, true, false, false},
            {false, false, false, false, false}, 
            {false, true, false, false, false},
            {false, false, false, false, false}, 
            {false, false, true, false, false}};

        char[][] map = new char[][] {
            {Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, Config.UNSWEPT,
                Config.UNSWEPT},
            {Config.NO_NEARBY_MINE, Config.UNSWEPT, Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE,
                Config.NO_NEARBY_MINE},
            {Config.NO_NEARBY_MINE, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT,
                Config.UNSWEPT,},
            {Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, Config.UNSWEPT, Config.NO_NEARBY_MINE,
                Config.NO_NEARBY_MINE}};
         char[][] map2 = new char[][] {
            {Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, Config.UNSWEPT, 
                Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE},
            {Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE,
                    Config.NO_NEARBY_MINE},
            {Config.NO_NEARBY_MINE, Config.UNSWEPT, Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE,
                Config.NO_NEARBY_MINE},
            {Config.NO_NEARBY_MINE,Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE,
                    Config.NO_NEARBY_MINE,},
            {Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, Config.UNSWEPT, Config.NO_NEARBY_MINE,
                Config.NO_NEARBY_MINE}};
          
        boolean returnValue = false;
        returnValue = MineSweeper.allSafeLocationsSwept(map, mines);
        if (returnValue == true){
            error = true;
            System.out.println("testAllSafeLocationsSwept 1: not all safe locations are swept");
        }
        returnValue = MineSweeper.allSafeLocationsSwept(map2, mines);
        if (returnValue == false){
            error = true;
            System.out.println("testAllSafeLocationsSwept 2: all safe locations are supposed to be swept");
        }
        if (error) {
            System.out.println("testAllSafeLocationsSwept: failed");
        } else {
            System.out.println("testAllSafeLocationsSwept: passed");
        }
    }


    /**
     * This is intended to run some tests on the sweepLocation method. 
     * 1. Test one takes a corner case and makes sure sweep location 
     * returns zero because there are no mines nearby.
     * 2.Takes a middle case with a lot of mines nearby if sweeplocation
     * does not return 3 then there is an error. This is because there are
     * 3 mines nearby
     * 3. Takes a case that is out of bounds. If sweeplocation does not return
     * -3 then there is an error because this location does not exist
     * 4. Test four is used to check that sweeplocation return -2 when a place has
     * already been swept. We don't need to change an number twice so it skips over it
     * 5. Test five checks to make sure sweeplocations returns -1 because that location
     * contains a mine so it should be changed to Config.SWEPT_MINE.
     */
    private static void testSweepLocation() {
        // Review the sweepLocation method header carefully and write
        // tests to check whether the method is working correctly.
        boolean error = false;
       
        char[][] studentMap = new char[5][5];
        boolean[][] mines = new boolean[][] {
            {false, false, true, false, false},
            {false, false, false, false, false}, 
            {false, true, false, true, true},
            {false, false, false, false, false}, 
            {false, false, true, false, false}};
            studentMap [0][0] = Config.UNSWEPT;
            studentMap [3][3] = Config.UNSWEPT;  
            studentMap [4][0] = Config.NO_NEARBY_MINE;
            studentMap [0][2] = Config.UNSWEPT;
            int sweptLocation = MineSweeper.sweepLocation(studentMap, mines, 0, 0);
            if (sweptLocation != 0) {
                error = true;
                System.out.println(
                    "testSweepLocation 1: sweptLocation=" + sweptLocation + " expected 0");
            }
            int sweptLocation2 = MineSweeper.sweepLocation(studentMap, mines, 3, 3);
            if (sweptLocation2 != 3) {
                error = true;
                System.out.println(
                    "testSweepLocation 2: sweptLocation=" + sweptLocation + " expected 3");
            }
            int sweptLocation3 = MineSweeper.sweepLocation(studentMap, mines, 6, 6);
            if (sweptLocation3 != -3) {
                error = true;
                System.out.println(
                    "testSweepLocation 3: sweptLocation=" + sweptLocation + " expected  -3");
            }
            int sweptLocation4 = MineSweeper.sweepLocation(studentMap, mines, 4, 0);
            if (sweptLocation4 != -2) {
                error = true;
                System.out.println(
                    "testSweepLocation 4: sweptLocation=" + sweptLocation + " expected  -2");
            }
            int sweptLocation5 = MineSweeper.sweepLocation(studentMap, mines, 0, 2);
            if (sweptLocation5 != -1) {
                error = true;
                System.out.println(
                    "testSweepLocation 5: sweptLocation=" + sweptLocation + " expected  -1");
            }
            if (error) {
                System.out.println("testSweepLocation: failed");
            } else {
                System.out.println("testSweepLocation: passed");
            }
        }


    /**
     * This is intended to run some tests on the sweepAllNeighbors method. 
     * 1. Test one tests a corner case in order to make sure it isn't sweeping 
     * things that are out of bounds. It make sure the values that are supposed to
     * be swept are no longer swept in the map.
     * 2.Test two sweeps 8 locations it makes sure all the neighboring locations
     * to the location we put in are no long equal to Config.UNSWEPT but it does
     * not include its own location
     */
    private static void testSweepAllNeighbors() {
        // Review the sweepAllNeighbors method header carefully and write
        // tests to check whether the method is working correctly.
        boolean error = false;
        
        char[][] studentMap = new char[5][5];
        boolean[][] mines = new boolean[][] {
            {false, false, true, false, false},
            {false, false, false, false, false}, 
            {false, true, false, true, true},
            {false, false, false, false, false}, 
            {false, false, true, false, false}};
        char[][] map = new char[][] {
            {Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT,},
            {Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT}};
            MineSweeper.sweepAllNeighbors(map, mines, 0, 0);
            if(map[0][1]==Config.UNSWEPT || map[1][0]==Config.UNSWEPT || map[1][1]==Config.UNSWEPT) {
                error = true;
                System.out.println(
                   "testSweepAllNeighbors 1: did not sweep all locations nearby");
            }
            MineSweeper.sweepAllNeighbors(map, mines, 3, 3);
            if(map[4][3]==Config.UNSWEPT || map[3][4]==Config.UNSWEPT || map[4][4]==Config.UNSWEPT
                || map[3][2]==Config.UNSWEPT || map[4][2]!=Config.UNSWEPT || map[2][2]==Config.UNSWEPT 
                || map[2][3]!=Config.UNSWEPT || map[2][4]!=Config.UNSWEPT ) {
                error = true;
                System.out.println(
                   "testSweepAllNeighbors 2: did not sweep all locations nearby");
            }
            if (error) {
                System.out.println("testSweepAllNeighbors: failed");
            } else {
                System.out.println("testSweepAllNeighbors: passed");
            }
    }
}
