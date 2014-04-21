package maze.cli;

import maze.logic.MazeLogic;
import maze.logic.MazeOptions;
import maze.cli.Interface;

/**
 * The Main Class.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Main {
	
	
	/**
	 * The main method.
	 *
	 * @param args arguments for the command-line
	 */
	public static void main(String[] args) {

		boolean win = false, lose = false, mazet;
		int move, size, dragt, numdra;
		MazeOptions mazeopt = new MazeOptions();
		Interface i1 = new Interface();
		mazet = i1.getMazeType();
		mazeopt.setIsdefault(mazet);
		if (!mazet) {
			size = i1.size = i1.getSize();
			mazeopt.setSize(size);
			numdra = i1.getDrag();
			mazeopt.setNdrag(numdra);
		}
		else {
			mazeopt.setNdrag(1);
		}
		dragt = i1.getDragType();
		mazeopt.setDragtype(dragt);
		MazeLogic m1 = new MazeLogic();
		m1.setMazeopt(mazeopt);
		m1.createMaze();
		char[][] maze = m1.getMaze();
		i1.printMaze(maze);
		do {
			move = i1.Commands();
			m1.moveHero(move);
			m1.moveDragons();
			if(m1.getExitState()) 
				win = true;
			if(m1.heroDied())
				lose = true;
			maze = m1.getMaze();
			i1.printMaze(maze);	
		} while(!win && !lose);
		if(lose) {
			System.out.println("Perdeste o jogo.");
		}
		if(win) {
			System.out.println("Ganhaste o jogo.");
		}
	}

}
