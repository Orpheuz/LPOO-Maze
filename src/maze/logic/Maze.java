package maze.logic;

import java.io.Serializable;

import maze.logic.MazeBuild;

/**
 * The Class Maze. This class is responsible for the deafult maze and the maze's elements.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Maze implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private char[][] maze;

	/**
	 * Instantiates a new maze with the predefined setup.
	 */
	public Maze() {
		char[][] maze = { 
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x' },
				{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
				{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
				{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
				{ 'x', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' ', 'S' },
				{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
				{ 'x', ' ', 'x', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
				{ 'x', ' ', 'x', 'x', ' ', ' ', ' ', ' ', ' ', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };
		this.maze = maze;
	}

	/**
	 * Instantiates a new maze with a random setup and a size chosen by the user.
	 *
	 * @param size the size  of the maze
	 */
	public Maze(int size) {
	MazeBuild build = new MazeBuild(size, size);
		maze = build.getMaze();
	}
	
	/**
	 * Gets the character in a specific position of the maze
	 *
	 * @param pos the position in the maze
	 * @return the character on that position
	 */
	public char getPosElem(int pos[]) {
		return maze[pos[0]][pos[1]];
	}
	
	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public char[][] getMaze() {
		char[][] copy = maze.clone();
		for (int i = 0; i < maze.length; i++) {
			copy[i] = maze[i].clone();
		}
		return copy;
	}
	
	/**
	 * Gets the c maze.
	 *
	 * @return the c maze
	 */
	public char[][]getCMaze() {
		
		return maze;
	}
	
	/**
	 * Changes the maze to become the new maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(char[][] maze) {
		this.maze = maze;
	}
	
	/**
	 * Changes the character in a position to become what the user chooses.
	 *
	 * @param pos the position of the maze
	 * @param c the character the user chose
	 */
	public void setPosElem(int pos[], char c) {
		maze[pos[0]][pos[1]] = c;
	}
	
	/**
	 * Gets the exit position.
	 *
	 * @return the exit position
	 */
	public int[] getExitPos() {
		int coord[] = new int[2];
		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[i].length; j++)
			{
				if(maze[i][j] == 'S')
				{
					coord[0] = i;
					coord[1] = j;
				}
			}
		}
		return coord;
	}
	
}