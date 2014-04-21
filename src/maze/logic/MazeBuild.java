package maze.logic;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Random;

/**
 * The Class MazeBuild. This class builds the maze automatically.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class MazeBuild implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** The Constant MAZE_WALL, that represents a wall in the maze. */
	public static final char MAZE_WALL = 'x';
	
	/** The Constant MAZE_HERO, that represents an hero in the maze. */
	public static final char MAZE_HERO = 'H';
	
	/** The Constant MAZE_PATH, that represents an open path in the maze. */
	public static final char MAZE_PATH = ' ';
	
	/** The Constant MAZE_EXIT, that represents an exit in the maze. */
	public static final char MAZE_EXIT = 'S';
	
	/** The Constant MAZE_SWORD, that represents a sword in the maze. */
	public static final char MAZE_SWORD = 'E';
	
	/** The Constant MAZE_DRAGON, that represents a dragon in the maze. */
	public static final char MAZE_DRAGON = 'D';
	
	/** The Constant MAZE_EAGLE, that represents an eagle in the maze. */
	public static final char MAZE_EAGLE = 'B';
	
	private char[][] matrix;

	private ArrayDeque<Integer[]> tracker; // stack to trace location

	private int rows; // number of rows in the representative 2r+1 array
	private int cols; // number of cols in the representative 2c+1 array
	private int act_rows; // number of rows in the real maze
	private int act_cols; // "" cols ""
	
	/**
	 * Instantiates the construction of a new maze
	 *
	 * @param row the rows
	 * @param col the columns
	 */
	public MazeBuild (int row, int col) {
		act_rows = row;
		act_cols = col;
		rows = 2 * row + 1;
		cols = 2 * col + 1;
		matrix = new char[rows][cols]; // initializes the matrix to proper size

		tracker = new ArrayDeque<Integer[]>(rows * cols);
		// initialize tracker to ample size

		// setting the inside to filled
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = MAZE_WALL;
			}
		}
		makeMaze();

	}// end constructor

	// actual implementation of the algorithm
	/**
	 * Constructs the maze
	 */
	public void makeMaze() {

		int[] cut_order;// an array that gets all the possible motions
						// to which can be cut into

		int not_done; /*
					 * a variable that is positive when there are itemsin the
					 * stack, and negative when the stack oflocations is empty
					 */

		start(); // picks a random location to start building the maze

		not_done = stackPeek(); /*
								 * initializes the looping condition nowthat
								 * there is a location (the start)in the stack
								 */

		// loops while there are locations in the stack
		while (not_done > 0) {
			cut_order = canCut(); // gets the availible locations

			// if there are directions to cut into
			if (cut_order[0] != 0) {

				cut_order = mix(cut_order); // shuffle the availible directions

				// take the first direction to be cut into after shuffling and
				// cut into it. like shuffling a deck of cards and drawing the
				// top card
				if (cut_order[0] == 1) {
					cutNextUp();
				} else if (cut_order[0] == 2) {
					cutNextRight();
				} else if (cut_order[0] == 3) {
					cutNextDown();
				} else if (cut_order[0] == 4) {
					cutNextLeft();
				}
			} else { // if there are no directions to cut, back up one locaiton
				back();
			}

			not_done = stackPeek(); // are the more locations in stack?
		}

		makeOpenings();

	}


	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public char[][] getMaze() {
		return matrix;
	} 

	// starts making the maze at a random location
	private void start() {

		Random rand = new Random(); // initialize random number generator
		Integer[] loc = new Integer[2]; // a temp array to access the stack

		// pick a random start location. the location must be odd to be valid
		int start_row_index = rand.nextInt(act_rows - 1) * 2 + 1;
		int start_col_index = rand.nextInt(act_cols - 1) * 2 + 1;

		loc[0] = start_row_index;
		loc[1] = start_col_index;

		tracker.addFirst(loc);

		// clears the start point
		matrix[start_row_index][start_col_index] = MAZE_PATH;

	}

	// check to see if there are more locations in the tracker
	private int stackPeek() {
		if (tracker.peekFirst() == null)
			return -1;

		return 1;
	}

	private int[] canCut() {
		int[] cut = new int[4]; // and array of the directions able to be cut
		int place = 0; // number of directions that can be cut into

		// check to see if up is a valid direction
		if (canUp() != 0) {
			cut[place] = canUp();
			place++;
		}
		// check to see if right is a valid direction
		if (canRight() != 0) {
			cut[place] = canRight();
			place++;
		}
		// check to see if down is a valid direction
		if (canDown() != 0) {
			cut[place] = canDown();
			place++;
		}
		// check to see if left is a valid direction
		if (canLeft() != 0) {
			cut[place] = canLeft();
			place++;
		}

		// return array full of 0 is there are no valid directions
		if (place == 0) {
			for (int i = 0; i < 4; i++) {
				cut[i] = 0;
			}
			return cut;

		} else { // otherwise trim the array to the right length and return it
			int[] cancut = new int[place];
			for (int i = 0; i < place; i++) {
				cancut[i] = cut[i];
			}

			return cancut;
		}
	}

	private int canUp() {
		Integer[] current = tracker.peekFirst(); // current location

		int nxt_row = current[0] - 2; // next location
		int nxt_col = current[1];

		// if next location is in the array and not already cut, can cut it
		if (nxt_row < 0 || matrix[nxt_row][nxt_col] == MAZE_PATH) {
			return 0;
		} else {

			return 1; // 1 corresponds to up
		}
	}

	private int canDown() {

		Integer[] current = tracker.peekFirst(); // current location

		int nxt_row = current[0] + 2; // next location
		int nxt_col = current[1];

		// if the next location is in the array and not already cut, can cut it
		if (nxt_row > rows - 1 || matrix[nxt_row][nxt_col] == MAZE_PATH) {
			return 0;
		} else {
			return 3; // 3 corresponds to down
		}
	}

	private int canRight() {
		Integer[] current = tracker.peekFirst(); // current location

		int nxt_row = current[0]; // next location
		int nxt_col = current[1] + 2;

		// if the next location is in the array and not already cut, can cut it
		if (nxt_col > cols - 1 || matrix[nxt_row][nxt_col] == MAZE_PATH) {
			return 0;
		} else {
			return 2; // 2 corresponds to right
		}
	}

	private int canLeft() {
		Integer[] current = tracker.peekFirst(); // current location

		int nxt_row = current[0]; // next location
		int nxt_col = current[1] - 2;

		// if next location is in the array and not already cut, can cut it
		if (nxt_col < 0 || matrix[nxt_row][nxt_col] == MAZE_PATH) {
			return 0;
		} else {
			return 4; // 4 corresponds to left
		}
	}

	private int cutNextUp() {
		Integer[] current = tracker.peekFirst(); // gets the current location
		Integer[] loc = new Integer[2]; // temp var to access the stack

		int nxt_row = current[0] - 2; // the location of the next row index
		int nxt_col = current[1]; // locaiton of next col index

		// sets the next index and the wall between it to blank
		matrix[current[0] - 1][current[1]] = MAZE_PATH;
		matrix[current[0] - 2][current[1]] = MAZE_PATH;

		loc[0] = nxt_row;
		loc[1] = nxt_col;

		// adds the new index to the stack
		tracker.addFirst(loc);

		return 1;
	}

	private int cutNextDown() {
		Integer[] current = tracker.peekFirst(); // gets the current locaiton
		Integer[] loc = new Integer[2]; // temp variable to access stack

		int nxt_row = current[0] + 2; // locaiton of next row and col
		int nxt_col = current[1];

		// clears the next index and the wall between it
		matrix[current[0] + 1][current[1]] = MAZE_PATH;
		matrix[current[0] + 2][current[1]] = MAZE_PATH;

		loc[0] = nxt_row;
		loc[1] = nxt_col;

		// adds new index to stack
		tracker.addFirst(loc);

		return 1;
	}

	private int cutNextRight() {
		Integer[] current = tracker.peekFirst(); // gets the current locaiton
		Integer[] loc = new Integer[2]; // dummy variable to access stack

		int nxt_row = current[0]; // location of next row and col
		int nxt_col = current[1] + 2;

		// clears the necessary locaitons
		matrix[current[0]][current[1] + 1] = MAZE_PATH;
		matrix[current[0]][current[1] + 2] = MAZE_PATH;

		loc[0] = nxt_row;
		loc[1] = nxt_col;

		// adds index to stack
		tracker.addFirst(loc);

		return 1;
	}

	private int cutNextLeft() {
		Integer[] current = tracker.peekFirst(); // gets current locaiton
		Integer[] loc = new Integer[2]; // temp varaible to access stack

		int nxt_row = current[0]; // location of next row and col
		int nxt_col = current[1] - 2;

		// clears the necessary locaitons
		matrix[current[0]][current[1] - 1] = MAZE_PATH;
		matrix[current[0]][current[1] - 2] = MAZE_PATH;

		loc[0] = nxt_row;
		loc[1] = nxt_col;

		// adds new index to stack
		tracker.addFirst(loc);

		return 1;
	}

	private void back() {
		tracker.removeFirst();
	}

	/**
	 * Makes paths on the maze so that the hero can move.
	 */
	public void makeOpenings() {

		Random rand = new Random(); // random number generator
		Random rand2 = new Random();
		// a random location for the exit
		int i = rand2.nextInt(4);
		if (i == 0) {
			int exit_row = rand.nextInt(act_rows - 1) * 2 + 1;
			matrix[exit_row][cols - 1] = MAZE_EXIT;
		}
		else if (i == 1){
			int exit_column = rand.nextInt(act_cols - 1) * 2 + 1;
			matrix[rows - 1][exit_column] = MAZE_EXIT;
		}
		else if(i == 2) {
			int exit_column = rand.nextInt(act_cols - 1) * 2 + 1;
			matrix[0][exit_column] = MAZE_EXIT;
		}
		else {
			int exit_row = rand.nextInt(act_rows - 1) * 2 + 1;
			matrix[exit_row][0] = MAZE_EXIT;
		}
	}

	private static int[] mix(int[] arr) {

		Random rand = new Random(); // a random number generator
		int temp; // a temp variable for swapping
		int place1; // one location to be swapped
		int place2;

		// shuffle seven times
		for (int i = 0; i < 7; i++) {

			// pick two random indices of the array
			place1 = rand.nextInt(arr.length);
			place2 = rand.nextInt(arr.length);

			// swap the two random indices
			temp = arr[place1];
			arr[place1] = arr[place2];
			arr[place2] = temp;
		}

		return arr;
	}
	
}
