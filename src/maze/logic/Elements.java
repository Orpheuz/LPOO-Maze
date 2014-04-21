package maze.logic;

import java.io.Serializable;

/**
 * The Class Elements. This is a super class.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public abstract class Elements implements Serializable{

	
	private static final long serialVersionUID = 1L;
	protected int[] pos;
	private boolean isAlive;

	/**
	 * Instantiates a new element, with a position and alive.
	 *
	 * @param pos the position of the element.
	 */
	public Elements(int[] pos){
		this.pos = pos.clone();
		this.isAlive = true;
	}

	/**
	 * Gets the position of the element.
	 *
	 * @return the position of the element
	 */
	public int[] getPos() {
		return pos.clone();
	}

	/**
	 * Gets the y position of the element.
	 *
	 * @return the y position of the element
	 */

	public int getWPos() {
		return pos[0];
	}

	/**
	 * Gets the x position of the element.
	 *
	 * @return the x position of the element
	 */
	public int getHPos() {
		return pos[1];
	}

	/**
	 * Sets the new y position of the element.
	 *
	 * @param Wpos the new y position of the element
	 */
	public void setWPos(int Wpos) {
		this.pos[0] = Wpos;
	}

	/**
	 * Sets the new x position of the element.
	 *
	 * @param Hpos the new x position of the element
	 */
	public void setHPos(int Hpos) {
		this.pos[1] = Hpos;
	}

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public abstract char getElem();

	/**
	 * Checks if the element is alive.
	 *
	 * @return true, if the element is alive
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * Sets the element alive or dead.
	 *
	 * @param isAlive determines whether the element is alive or not
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}