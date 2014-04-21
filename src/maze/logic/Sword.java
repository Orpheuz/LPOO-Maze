package maze.logic;



/**
 * The Class Sword. This class is responsible for the sword.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Sword extends Elements{

	
	private static final long serialVersionUID = 1L;
	private boolean picked = false;
	private boolean inDrag = false;
	
	/**
	 * Instantiates a new sword.
	 *
	 * @param pos the position of the new sword
	 */
	public Sword(int[] pos) {
		super(pos);
	}
	
	/**
	 * The hero picks the sword up
	 */
	public void pickSword()
	{
		picked = true;
		setAlive(false);
	}
	
	/**
	 * Checks if is the sword has been picked up.
	 *
	 * @return true, if the sword has been picked up
	 */
	public boolean isPickedUp() {
		return picked;
	}
	
	/** 
	 * @see maze.logic.Elements#getElem()
	 */
	public char getElem() {
		if (picked)
			return ' ';
		else
			return 'E';
	}

	/**
	 * Releases the sword.
	 *
	 * @param pos the position on which the sword is released
	 */
	public void releaseSword(int pos[]) {
		picked = false;
		this.setWPos(pos[0]);
		this.setHPos(pos[1]);
	}

	/**
	 * Checks if a dragon is on the same position as the sword.
	 *
	 * @return true, if a dragon is on the same position as the sword
	 */
	public boolean isInDrag() {
		return inDrag;
	}

	/**
	 * Sets the sword as being in the same position as a dragon.
	 *
	 * @param inDrag determines if the sword is in the same position as a dragon 
	 */
	public void setInDrag(boolean inDrag) {
		this.inDrag = inDrag;
	}
}
