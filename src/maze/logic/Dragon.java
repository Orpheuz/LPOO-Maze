package maze.logic;

import java.util.Random;



/**
 * The Class Dragon. This class is responsible for the dragons.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Dragon extends MovingElem {


	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new dragon.
	 *
	 * @param pos the position of the dragon
	 */
	public Dragon(int[] pos) {
		super(pos);
		this.sleeping = 4;
	}

	private int sleeping; //sleeping turns 
	private boolean noSleep = false; //move and sleep or just move
	private boolean isSleeping = false; //is sleeping
	private boolean inSword = false; //same pos as sword
	private int dir = 4;

	/**
	 * Checks if the dragon is sleeping.
	 *
	 * @return true, if the dragon is sleeping
	 */
	public boolean isSleeping() {
		if(sleeping == 4)
			isSleeping = false; 
		else isSleeping = true;
		return isSleeping;
	}

	/**
	 * Checks if the dragon cannot fall asleep.
	 *
	 * @return true, if the dragon cannot fall asleep
	 */
	public boolean isNoSleep() {
		return noSleep;
	}

	/**
	 * Determines if the dragon can fall asleep.
	 *
	 * @param noSleep is a boolean that determines if the dragon can fall asleep
	 */
	public void setNoSleep(boolean noSleep) {
		this.noSleep = noSleep;
	}

	/**
	 * Checks if the dragon is on the same position as the sword.
	 *
	 * @return true, the dragon is on the same position as the sword
	 */
	public boolean hasSword() {
		return inSword;
	}

	/**
	 * Determines that the dragon is on the same position as the sword.
	 */
	public void inSword() {
		inSword = true;
	}

	/**
	 * Determines that the dragon is not on the same position as the sword.
	 */
	public void outSword() {
		inSword = false;
	}

	/**
	 * Determines that the dragon is sleeping.
	 */
	public void sleep() {
		isSleeping = true;
	}

	/**
	 * Awakens the dragon.
	 */
	public void wake() {
		isSleeping = false;
	}

	/**
	 * Gets the number of turns the dragon will be asleep, or 4 if the dragon is awake.
	 *
	 * @return the number of turns the dragon will be asleep, or 4 if the dragon is awake
	 */
	public int getSleeping() {
		return sleeping;
	}

	/**
	 * Sets the the number of turns the dragon will be asleep. Put 4 to set the dragon awake.
	 *
	 * @param sleeping the number of turns the dragon will be asleep. If it is 4 the 
	 * dragon is awake
	 */
	public void setSleeping(int sleeping) {
		this.sleeping = sleeping;
	}

	/**
	 * Decrements the number of turns the dragon is asleep.
	 */
	public void decrmSleep() {
		this.sleeping = this.sleeping - 1;
		if(this.sleeping == 0) {
			this.sleeping = 4;
			this.wake();
		}
	}

	/** 
	 * @see maze.logic.MovingElem#getElem()
	 */
	public char getElem() {
		char x;

		if (!isAlive()) {
			x = ' ';
		} else if (isSleeping && !inSword) {
			x = 'd';
		} else if (!isSleeping && inSword) {
			x = 'F';
		} else if (isSleeping && inSword) {
			x = 'f';
		} else {
			x = 'D';
		}

		return x;
	}

	/**
	 * Checks if the dragon is at the sword.
	 *
	 * @param s the sword
	 */
	public void isAtSword(Sword s) {
		if (!isAlive())
			return;
		if (s.isPickedUp())
			return;
		if (this.getHPos() == s.getHPos() && this.getWPos() == s.getWPos()) {
			inSword();
			s.setInDrag(true);
		}
		if ((this.getHPos() != s.getHPos() || this.getWPos() != s.getWPos()) && hasSword()) {
			outSword();
			s.setInDrag(false);
		}
	}

	/**
	 * @see maze.logic.MovingElem#move(int, maze.logic.Maze)
	 */
	public boolean move(int dir, Maze maze) {
		if (!this.isNoSleep()) {
			if (!this.isSleeping()) {
				Random r = new Random();
				int sleep = r.nextInt(4);
				if (sleep == 1) {
					this.decrmSleep();
					this.isSleeping();
					return false;
				} else {
					this.dir = dir;
					return super.move(dir, maze);
				}
			}
		} else {
				this.dir = dir;
				return super.move(dir, maze);
		}
		return false;
	}
	
	/**
	 * Move the dragon in a random direction.
	 *
	 * @param m the maze
	 */
	public void moveRandom(Maze m) {
		Random r = new Random();
		int dir = r.nextInt(4) + 1;
		move(dir, m);
	}
	
	/**
	 * Gets the direction on which the dragon moves.
	 *
	 * @return the direction on which the dragon moves
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * Sets the the direction on which the dragon moves.
	 *
	 * @param dir the new direction on which the dragon moves.
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}
}