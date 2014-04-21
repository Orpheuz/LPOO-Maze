package maze.logic;


/**
 * The Class Hero. This class is responsible for the hero.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Hero extends MovingElem {
	
	private static final long serialVersionUID = 1L;
	
	/** The Constant EAGLE which is used to send the eagle to get the sword. */
	public static final int EAGLE = 5;
	private boolean sword = false;
	private boolean eagle = false;
	
	/**
	 * Instantiates a new hero.
	 *
	 * @param pos the position where the hero is created
	 */
	public Hero(int[] pos) {
		super(pos);
	}

	/**
	 * The hero finds the sword and equips it
	 */
	public void foundSword() {
		sword = true;
	}

	/**
	 * Checks if the hero has the sword.
	 *
	 * @return true, if the hero has the sword
	 */
	public boolean hasSword() {
		return sword;
	}

	/**
	 * Checks if the hero is near a dragon.
	 *
	 * @param dra the dragons
	 * @return true, if the hero is near a dragon
	 */
	public boolean isNearDragon(Dragon dra) {
		if (!dra.isAlive()) {
			return false;
		}
		if (Math.abs(getHPos() - dra.getHPos()) <= 1 && getWPos() - dra.getWPos() == 0
				|| (getHPos() - dra.getHPos() == 0) && Math.abs(getWPos() - dra.getWPos()) <= 1){
			if (!dra.isSleeping() && !this.hasSword()) {
				this.died();
				this.setAlive(false);
				return true;
			}
			if (hasSword()) {
				dra.died();
				dra.setAlive(false);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the hero picked the sword up
	 *
	 * @param s the sword
	 * @return true, if the hero picked the sword up
	 */
	public boolean pickedUpSword(Sword s) {
		if (!isAlive()) {
			return false;
		}
		if (s.getHPos() == getHPos() && s.getWPos() == getWPos()) {
			if (!s.isPickedUp()) {
				s.pickSword();
				this.foundSword();
				return true;
			}

		}
		return false;
	}

	/** 
	 * @see maze.logic.MovingElem#getElem()
	 */
	public char getElem() {
		if (this.hasSword()) {
			return 'A';
		} else
			return 'H';
	}

	/** 
	 * @see maze.logic.MovingElem#move(int, maze.logic.Maze)
	 */
	public boolean move(int dir, Maze m) {
		if (dir == EAGLE && !this.hasSword()) {
			this.sendEagle();
			return true;
		} else
			return super.move(dir, m);
	}
	
	/**
	 * Checks if the eagle is available
	 *
	 * @return whether the eagle is available
	 */
	public boolean getEagle() {
		return eagle;
	}

	/**
	 * Send the eagle to go get the sword.
	 */
	public void sendEagle() {
		eagle = true;
	}

	/**
	 * Picks the eagle with the sword up
	 *
	 * @param e the eagle
	 */
	public void catchEagle(Eagle e) {
		eagle = false;
		if (e.hasSword()) {
			this.foundSword();
			e.AlreadyUsed();
		}
	}

}
