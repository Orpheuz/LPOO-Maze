package maze.cli;

import java.util.Scanner;

import maze.logic.Hero;
import maze.logic.MovingElem;

/**
 * The Class Interface. It's responsible for the interface of the maze, such as the way the 
 * dragons behave, how the dragons are created, how the maze is created, etc.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Interface {
	
	protected int size;
	
	
	/**
	 * Gets the size of the maze.
	 *
	 * @return the size of the maze
	 */
	public int getSize() {
		System.out.println("Introduza o tamanho do labirinto (NxN)");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int i = s.nextInt();
		while(i > 100 || i < 10) {
			System.out.println("Introduza o tamanho do labirinto (NxN)");
			i = s.nextInt();
		}
		return i/2;
		
	}
	
	/**
	 * Gets the number of dragons.
	 *
	 * @return the number of dragons.
	 */
	public int getDrag() {
		System.out.println("Quantos dragões quer no labirinto?");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int i = s.nextInt();
		while(i > MaxDragForm()) {
			System.out.println("Quantos dragões quer no labirinto?");
			i = s.nextInt();
		}
		return i;
		
	}
	
	/**
	  * Gets the type of maze.
	 *
	 * @return the type of maze
	 */
	public boolean getMazeType() {
		System.out.println("Labirinto automático[1] ou labirinto aleatório[2]?");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int i = s.nextInt();
		while(i != 1 && i != 2) {
			System.out.println("Labirinto automático[1] ou labirinto aleatório[2]?");
			i = s.nextInt();
		}
		if(i == 1)
			return true;
		else return false;
	}
	
	/**
	* Gets the type of dragons.
	 *
	 * @return the type of dragons
	 */
	public int getDragType() {
		System.out.println("Dragões parados[1], a mover[2], a mover e dormir[3]?");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int i = s.nextInt();
		while(i != 1 && i != 2 && i != 3) {
			System.out.println("Dragões parados[1], a mover[2], a mover e dormir[3]?");
			i = s.nextInt();
		}
		return i;
	}
	
	/**
	 * This function is responsible to determine the maximum number of dragons.
	 *
	 * @return the maximum number of dragons
	 */
	public int MaxDragForm() {
		return 2*size/3;
	}
	
	/**
	 * Prints the maze.
	 *
	 * @param the maze created
	 */
	public void printMaze(char[][] maze) {
		System.out.println();
		for (char[] s : maze) {
			for (char c : s) {
				System.out.print(c);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Allows the user to give commands to move the hero.
	 *
	 * @return the command given by the user
	 */
	public int Commands() {
		System.out.println("\n(W) UP | (A) LEFT | (S) DOWN | (D) RIGHT | (E) EAGLE");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		switch (str) {
		case "w":
			return (int) MovingElem.UP;
		case "a":
			return (int) MovingElem.LEFT;
		case "s":
			return (int) MovingElem.DOWN;
		case "d":
			return (int) MovingElem.RIGHT;
		case "e":
			return (int) Hero.EAGLE;
		default:
			return 0;
		}
	}
}
