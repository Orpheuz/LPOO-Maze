package maze.logic;

import java.io.Serializable;

/**
 * The Class MazeOptions. This class sets the maze's options, such as number and type of 
 * dragons and the size of the maze.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class MazeOptions implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int size;
	private int ndrag;
	private int dragtype; //1- static, 2- move, 3- moveNSleep
	private boolean isdefault;
	
	/**
	 * Creates new options for the maze
	 */
	public MazeOptions() {
		size = 5;
		ndrag = 1;
		dragtype = 3;
		isdefault = true;
		//default opt
	}
	
	/**
	 * Gets the size of the maze
	 *
	 * @return the size of the maze
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Sets the size of the maze
	 *
	 * @param size the new size of the maze
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * Gets the number of dragons on the maze.
	 *
	 * @return the number of dragons on the maze
	 */
	public int getNdrag() {
		return ndrag;
	}
	
	/**
	 * Sets the number of dragons on the maze.
	 *
	 * @param ndrag the new number of dragons on the maze
	 */
	public void setNdrag(int ndrag) {
		this.ndrag = ndrag;
	}
	
	/**
	 * Checks if the maze's options are the default
	 *
	 * @return true, if the maze's options are the default
	 */
	public boolean isIsdefault() {
		return isdefault;
	}
	
	/**
	 * Sets the maze's options as the default.
	 *
	 * @param isdefault determines if the maze's options are the default options
	 */
	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
	}
	
	/**
	 * Gets the type of the dragons, which can be static, moving or moving and sleeping.
	 *
	 * @return the type of the dragons, which can be static, moving or moving and sleeping
	 */
	public int getDragtype() {
		return dragtype;
	}
	
	/**
	 * Allows the user to change the type of dragons, which can be static, moving or
	 *  moving and sleeping.
	 *
	 * @param dragtype the new type of the dragons, which can be static, moving or 
	 * moving and sleeping.
	 */
	public void setDragtype(int dragtype) {
		this.dragtype = dragtype;
	}	
}
