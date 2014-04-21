package maze.logic;


/**
 * The Class MovingElem. This class is responsible for the movement of the elements.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public abstract class MovingElem extends Elements {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean alive = true;
	
	/** The Constant UP, that determines that the user moved the hero up. */
	public static final int UP = 1;
	
	/** The Constant LEFT, that determines that the user moved the hero left. */
	public static final int LEFT = 2;
	
	/** The Constant DOWN, that determines that the user moved the hero down. */
	public static final int DOWN = 3;
	
	/** The Constant RIGHT, that determines that the user moved the hero right. */
	public static final int RIGHT = 4;
	
	/**
	 * Instantiates a new moving element.
	 *
	 * @param pos the position of the element
	 */
	public MovingElem(int[] pos){
		super(pos);
	}

	/**
	 * The function that allows elements to move
	 *
	 * @param dir the direction on which the element moves
	 * @param maze the maze on which the element moves
	 * @return true, if successful
	 */
	public boolean move(int dir, Maze maze) {
		int[] next_pos = pos.clone();

		switch (dir) {
		case UP:
			next_pos[0]--;
			break;
		case LEFT:
			next_pos[1]--;
			break;
		case DOWN:
			next_pos[0]++;
			break;
		case RIGHT:
			next_pos[1]++;
			break;
		default:
			return false;
		}

		char maze_elem = maze.getPosElem(next_pos);

		if (maze_elem == MazeBuild.MAZE_PATH) {
			pos = next_pos.clone();
			return true;
		}
		return false;
	}

	/** 
	 * @see maze.logic.Elements#isAlive()
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Sets the element as dead
	 */
	public void died() {
		alive = false;
	}
	
	/** 
	 * @see maze.logic.Elements#getElem()
	 */
	public abstract char getElem();
}
