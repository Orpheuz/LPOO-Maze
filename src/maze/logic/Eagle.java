package maze.logic;



/**
 * The Class Eagle. This class is responsible for the eagles.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Eagle extends MovingElem {

	private static final long serialVersionUID = 1L;
	static int pos_counter = 0;
	private boolean alive = true;
	private boolean atHero = true;
	private boolean fly = false;
	private boolean sword = false;
	private boolean coming = false;
	private int[] heroCoords = new int[2];
	
	/**
	 * Instantiates a new eagle.
	 *
	 * @param pos the position of the eagle
	 */
	public Eagle(int[] pos) {
		super(pos);
	}
	
	/** 
	 * @see maze.logic.MovingElem#isAlive()
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/**
	 * Checks if the eagle is with the hero.
	 *
	 * @return true, if the eagle is with the hero
	 */
	public boolean isAtHero()
	{
		return atHero;
	}
	
	/**
	 * Checks if the eagle is flying.
	 *
	 * @return true, if the eagle is flying
	 */
	public boolean isflying()
	{
		return fly;
	}
	
	/**
	 * Checks if the eagle has the sword.
	 *
	 * @return true, if the eagle has the sword
	 */
	public boolean hasSword()
	{
		return sword;
	}
	
	/**
	 * Checks if the eagle is coming to the the location where it was sent from.
	 *
	 * @return true, if the eagle is coming to the location where it was sent from
	 */
	public boolean isComing()
	{
		return coming;
	}

	/**
	 * Checks if the eagle can get the sword
	 *
	 * @return true, if successful
	 */
	public boolean canGetSword() 
	{
		return alive && !fly && sword;
	}
	
	/**
	 * Sets the eagle as dead
	 *
	 * @param s the sword that the eagle had
	 */
	public void died(Sword s) {
		alive = false;
		if (this.hasSword()) {
			sword = false;
			s.releaseSword(this.getPos());
			s.setAlive(true);
		}
		setAlive(false);
	}
	
	/**
	 * Sets the eagle as already having been used
	 */
	public void AlreadyUsed() {
		fly = false;
		atHero = true;
		coming = false;
		alive = false;
		sword = false;
	}
	
	/** 
	 * @see maze.logic.MovingElem#getElem()
	 */
	public char getElem() {
		if (atHero || !alive) {
			return ' ';
		} else {
			return 'B';
		}
	}
	
	/**
	 * Checks if the dragon eats the eagle
	 *
	 * @param dra the dragon
	 * @param s the sword
	 * @return true, if the eagle is killed
	 */
	public boolean EagleatDrag(Dragon dra, Sword s) {
		if (!dra.isAlive() && !this.isAlive() && !isAtHero())
			return false;

		if (Math.abs(s.getHPos() - dra.getHPos()) <= 1 && s.getWPos() - dra.getWPos() == 0
			|| ((s.getHPos() - dra.getHPos() == 0) && Math.abs(s.getWPos() - dra.getWPos()) <= 1)) {
			if (!dra.isSleeping() && !this.isflying() && !this.isAtHero()) {
				this.died(s);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Moves the eagle in the direction of the sword.
	 *
	 * @param s the sword the eagle is after
	 */
	public void EagleGetSword(Sword s) {
		if (atHero && !s.isPickedUp() && alive) {
			atHero = false;
			coming = false;
			heroCoords = this.pos.clone();
			fly = true;
			return;
		} else if (!coming) {
			int[] EaglePos = moveStraight(this.getWPos(), this.getHPos(), s.getWPos(), s.getHPos());
			this.setWPos(EaglePos[0] + this.getWPos());
			this.setHPos(EaglePos[1] + this.getHPos());
			if (this.getWPos() == s.getWPos()
					&& this.getHPos() == s.getHPos()) {
				coming = true;
				fly = false;
				sword = true;
				s.pickSword();
			}
		}
	}
	
	/**
	 * Moves the eagle with the sword in the direction of the hero.
	 *
	 * @param s the sword the eagle has
	 */
	public void retrieveSword(Sword s) {
		fly = true;
		int[] EaglePos = moveStraight (this.getWPos(), this.getHPos(), heroCoords[0], heroCoords[1]);
		this.setWPos(EaglePos[0] + this.getWPos());
		this.setHPos(EaglePos[1] + this.getHPos());
		s.setWPos(EaglePos[0] + this.getWPos());
		s.setHPos(EaglePos[1] + this.getHPos());
		if (this.getWPos() == heroCoords[0] && this.getHPos() == heroCoords[1]) {
			coming = false;
			fly = false;
			sword = true;
		}
	}
	
	 /**
	 * This is the Bresenham algorithm.
	 * Makes the eagle move towards the eagle, always making a move in proportion to where the
	 * sword is. It will move first in the x axis if the difference between the eagle's x 
	 * coordinate and the sword's x coordinate is greater then the difference between the
	 * eagle's y coordinate and the sword's y coordinate and vice. It also moves in a 
	 * proportional way. For example, if the difference was 7 in the x axis, and 2 on the 
	 * y axis, the eagle would move in the x axis first and 3 times, then once in the y axis, 
	 * then 3 again on the x axis, once in the y axis and one final time on the x axis.
	 *
	 * @param xInit the initial x coordinate
	 * @param yInit the initial y coordinate
	 * @param xFinal the x coordinate of the destination
	 * @param yFinal the y coordinate of the destination
	 * @return the eagle's new position
	 */
	public int[] moveStraight(int xInit, int yInit, int xFinal, int yFinal) {
			int[] newPos = new int[2];
			int deltaX = xInit - xFinal;
			int deltaY = yInit - yFinal;
			if (xFinal - xInit == 0 && yFinal - yInit == 0) {
				return newPos;
			} else if (xFinal - xInit == 0) {
				if (deltaY < 0) {
					newPos[1] = 1;
				} else {
					newPos[1] = -1;
				}
			} else if (yFinal - yInit == 0) {
				if (deltaX < 0) {
					newPos[0] = 1;
				} else {
					newPos[0] = -1;
				}
			} else if (Math.abs(deltaX) > Math.abs(deltaY)) {
				if (pos_counter <= (Math.abs(deltaX)) / (Math.abs(deltaY))) {
					pos_counter++;
					if (deltaX < 0) {
						newPos[0] = 1;
					} else {
						newPos[0] = -1;
					}
				} else {
					pos_counter = 0;
					if (deltaY < 0) {
						newPos[1] = 1;
					} else {
						newPos[1] = -1;
					}
				}
			} else {
				if (pos_counter <= (Math.abs(deltaY)) / (Math.abs(deltaX))) {
					pos_counter++;
					if (deltaY < 0) {
						newPos[1] = 1;
					} else {
						newPos[1] = -1;
					}
				} else {
					pos_counter = 0;
					if (deltaX < 0) {
						newPos[0] = 1;
					} else {
						newPos[0] = -1;
					}
				}
			}
			return newPos;
		}
	

	
}
