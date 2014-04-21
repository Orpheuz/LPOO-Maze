package maze.logic;

import java.io.Serializable;
import java.util.Random;

/**
 * The Class MazeLogic. This class is responsible for the logic of the maze and most relations
 * between it's elements.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class MazeLogic implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Hero hero;
	private Eagle eagle;
	private Sword sword;
	private Dragon[] dragons;

	private Elements[] elems;

	private Maze maze;

	private boolean exit = false;
	private boolean def;
	private int numdra;
	private int size;
	private int dragtype;
	private int[] exitPos;
	private boolean exitOpen = false;

	private MazeOptions mazeopt;
	
	Random rand = new Random(System.currentTimeMillis());
	

	/**
	 * Instantiates a new maze logic.
	 */
	public MazeLogic() {
		mazeopt = new MazeOptions();
	}
	
	/**
	 * Gets the hero.
	 *
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Sets the hero as the new hero.
	 *
	 * @param hero the new hero
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * Gets the eagle.
	 *
	 * @return the eagle
	 */
	public Eagle getEagle() {
		return eagle;
	}

	/**
	 * Sets the eagle as the new eagle.
	 *
	 * @param eagle the new eagle
	 */
	public void setEagle(Eagle eagle) {
		this.eagle = eagle;
	}

	/**
	 * Gets the sword.
	 *
	 * @return the sword
	 */
	public Sword getSword() {
		return sword;
	}

	/**
	 * Sets the sword as the new sword.
	 *
	 * @param sword the new sword
	 */
	public void setSword(Sword sword) {
		this.sword = sword;
	}

	/**
	 * Gets the dragons.
	 *
	 * @return the dragons
	 */
	public Dragon[] getDragons() {
		return dragons;
	}

	/**
	 * Sets the dragons as the new dragons.
	 *
	 * @param dragons the new dragons
	 */
	public void setDragons(Dragon[] dragons) {
		this.dragons = dragons;
	}
	
	/**
	 * Gets the options of the maze.
	 *
	 * @return the options of the maze
	 */
	public MazeOptions getMazeopt() {
		return mazeopt;
	}

	/**
	 * Sets the options for the maze.
	 *
	 * @param mazeopt the new options for the maze	
	 */
	public void setMazeopt(MazeOptions mazeopt) {
		this.mazeopt = mazeopt;
	}
	
	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	/**
	 * Checks if the exit is open, or if it blocked.
	 *
	 * @return true, if the exit is open
	 */
	public boolean isExitOpen() {
		return exitOpen;
	}

	/**
	 * Allows the user to set the exit as open or blocked
	 *
	 * @param exitOpen determines if the exit is open or blocked
	 */
	public void setExitOpen(boolean exitOpen) {
		this.exitOpen = exitOpen;
	}
	
	/**
	 * Initiates the dragons with the chosen type for them.
	 */
	public void initDragType() {
		dragtype = mazeopt.getDragtype();
		for (Dragon d : dragons) {
			boolean t = true;
			if (dragtype == 2)
				t = true;
			else if (dragtype == 3)
				t = false;
			d.setNoSleep(t);
		}
		exitPos = maze.getExitPos();
	}
	
	/**
	 * Shows if the hero is at the exit.
	 *
	 * @return whether the hero is at the exit
	 */
	public boolean getExitState() {
		return exit;
	}
	
	public void setExit(boolean exit) {
		this.exit = exit;
	}

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public Elements[] getElems() {
		return elems;
	}
	
	/**
	 * Changes the elements.
	 *
	 * @param elems the new elements
	 */
	public void setElems(Elements[] elems) {
		this.elems = elems;
	}
	
	/**
	 * Creates the maze.
	 */
	public void createMaze() {
		
		def = mazeopt.isIsdefault();
		size = mazeopt.getSize();
		if(def) {
			maze = new Maze();
		}
		else {
			
			maze = new Maze(size);
		}
		initHeroEagle();
		initDragSword();
		initDragType();
	}
	
	/**
	 * Starts the maze created by the user.
	 */
	public void startMazeC() {
		
		def = false;
		size = mazeopt.getSize();
		dragtype = mazeopt.getDragtype();
		initDragType();
		exitPos = maze.getExitPos();
	}
	
	/**
	 * Creates the outside walls of the maze.
	 *
	 * @param size the size of the maze
	 */
	public void createMazeBorder(int size) {
		maze = new Maze();
		char[][] mazetmp = new char[(mazeopt.getSize() * 2) + 1][(mazeopt.getSize() * 2) + 1];
		for (int i = 0; i < (mazeopt.getSize() * 2) + 1; i++) {
			for (int j = 0; j < (mazeopt.getSize() * 2) + 1; j++) {
				if(i == 0) {
					mazetmp[i][j] = MazeBuild.MAZE_WALL;
				}
				else if(j == 0) {
					mazetmp[i][j] = MazeBuild.MAZE_WALL;
				}
				else if(i == (mazeopt.getSize() * 2)) {
					mazetmp[i][j] = MazeBuild.MAZE_WALL;
				}
				else if(j == (mazeopt.getSize() * 2)) {
					mazetmp[i][j] = MazeBuild.MAZE_WALL;
				}
				else mazetmp[i][j] = MazeBuild.MAZE_PATH;
			}
		}
		maze.setMaze(mazetmp);
	}
	
	/**
	 * Resets the maze.
	 */
	public void resetMaze() {
		maze.setMaze(null);
	}
	
	private void initHeroEagle() {
		if(!def) {
			int[] pos = CreateHero();
			hero = new Hero(pos);
		}
		else {
			int[] defaultPos = {1, 1};
			hero = new Hero(defaultPos);
		}
		eagle = new Eagle(hero.getPos());
	}
	
	private void initDragSword() {
		if (!def) {
			numdra = mazeopt.getNdrag();
			int[] Spos = CreateSword();
			sword = new Sword(Spos);
			dragons = new Dragon[numdra];
			for (int i = 0; i < dragons.length; i++) {
				int[] pos = CreateDragons();
				dragons[i] = new Dragon(pos);
			}
		} else {
			numdra = 1;
			int[] Dpos = { 3, 1 }, Spos = { 4, 1 };
			dragons = new Dragon[numdra];
			dragons[0] = new Dragon(Dpos);
			sword = new Sword(Spos);

		}
		createElems();
	}

	private int[] CreateHero() {
		int[] pos = new int[2];
		char[][] mazetmp = maze.getMaze();
		Random rand = new Random();
		int start_row_index = rand.nextInt(size - 1) * 2 + 1;
		int start_col_index = rand.nextInt(size - 1) * 2 + 1;
		while (mazetmp[start_row_index][start_col_index] != ' ')  {
			rand = new Random();
			start_row_index = rand.nextInt(size - 1) * 2 + 1;
			start_col_index = rand.nextInt(size - 1) * 2 + 1;
		}
		pos[0] = start_row_index;
		pos[1] = start_col_index;
		return pos;
	}

	private boolean checkDragsPos(int[] pos) {
		if (!(dragons.length == 0)) {
			for (Dragon d : dragons) {
				if (d instanceof Dragon) {
					if (d.getWPos() == pos[0] && d.getHPos() == pos[1]) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean dragonsAreDead() {
		for (Dragon d : dragons) {
			if (d instanceof Dragon) {
				if(d.isAlive()) {
					return false;
				}
			}
		}
		return true;
	}

	private int[] CreateDragons() {
		int[] pos = new int[2];
		char[][] mazetmp = maze.getMaze();
		while (true){
			int start_row_index = rand.nextInt(size - 1) * 2 + 1;
			int start_col_index = rand.nextInt(size - 1) * 2 + 1;
			pos[0] = start_row_index;
			pos[1] = start_col_index;
			if ((mazetmp[start_row_index][start_col_index] == ' ') && checkHeroDS(pos) && checkDragsPos(pos)){
				break;
			}
		}
		return pos;
	}

	private int[] CreateSword() {
		int[] pos = new int[2];
		char[][] mazetmp = maze.getMaze();
		while (true){
			int start_row_index = rand.nextInt(size - 1) * 2 + 1;
			int start_col_index = rand.nextInt(size - 1) * 2 + 1;
			pos[0] = start_row_index;
			pos[1] = start_col_index;
			if ((mazetmp[start_row_index][start_col_index] == ' ') && checkHeroDS(pos)) {
				break;
			}
		}
		return pos;
	}
	
	private boolean checkHeroDS(int[] pos) {
		if(hero.getWPos() == pos[0] && hero.getHPos() == pos[1]) {
			return false;
		}
		else if (Math.abs(hero.getHPos() - pos[1]) <= 1 && hero.getWPos() - pos[0] == 0) {
			return false;
		}
		else if ((hero.getHPos() - pos[1] == 0) && Math.abs(hero.getWPos() - pos[0]) <= 1) {
			return false;
		}
		else return true;
	}
	
	/**
	 * Gets the size of the maze.
	 *
	 * @return the size of the maze
	 */
	public int getMazeSize() {
		return mazeopt.getSize()*2;
	}

	/**
	 * Gets the maze create.
	 *
	 * @return the maze create
	 */
	public char[][] getMazeCreate() {
		return maze.getCMaze();
	}
	
	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public char[][] getMaze() {
		char[][] mazetmp = maze.getMaze();
		for (Elements e : elems) {
			if (e instanceof MovingElem) {
				if (((MovingElem) e).isAlive() || (e instanceof Hero)) {
					char elem_char = e.getElem();
					if (elem_char != ' ') {
						mazetmp[e.getWPos()][e.getHPos()] = elem_char;
					}
				}
			} else {
				char elem_char = e.getElem();
				if (elem_char != ' ') {
					mazetmp[e.getWPos()][e.getHPos()] = elem_char;
				}
			}
		}
		return mazetmp;
	}
	
	/**
	 * Gets an empty maze.
	 *
	 * @return the empty maze
	 */
	public char[][] getEmptyMaze() {
		char[][] mazetmp = maze.getMaze();
		return mazetmp;
	}

	private void createElems() {
		int nElems = dragons.length + 3;
		elems = new Elements[nElems];
		elems[0] = hero;
		elems[1] = sword;
		elems[2] = eagle;
		for (int i = 3; i < elems.length; i++) {
			elems[i] = dragons[i-3];
		}
	}

	/**
	 * Shows if the hero is at the exit.
	 *
	 * @return true, if the hero is at the exit
	 */
	public boolean heroAtExit() {
		if (hero.getWPos() == exitPos[0] && hero.getHPos() == exitPos[1])
			return true;
		else
			return false;
	}

	/** 
	 * Shows if the hero died.
	 *
	 * @return true, if the hero is dead
	 */
	public boolean heroDied() {
		if (!hero.isAlive())
			return true;
		else
			return false;
	}
	
	/**
	 * Moves the dragons.
	 */
	public void moveDragons() {
		if (dragtype == 1) {
			for (Dragon d : dragons) {
				if (hero.isNearDragon(d)) {
					if (hero.hasSword())
						System.out.println("Mataste o dragão");
					else
						System.out.println("Foste morto pelo dragão");
				}
				if (eagle.EagleatDrag(d, sword))
					System.out.println("Águia morta pelo dragão");

			}
		}
		if (dragtype == 2) {
			for (Dragon d : dragons) {
				if (d.isAlive()) {
					d.moveRandom(maze);
					d.isAtSword(sword);
				}
				if (hero.isNearDragon(d)) {
					if (hero.hasSword())
						System.out.println("Mataste o dragão");
					else
						System.out.println("Foste morto pelo dragão");
				}
				if (eagle.EagleatDrag(d, sword))
					System.out.println("Águia morta pelo dragão");
			}
		}
		if (dragtype == 3) {
			for (Dragon d : dragons) {
				if (d.isAlive() && !d.isSleeping()) {
					d.moveRandom(maze);
					d.isAtSword(sword);
				} else if (d.getSleeping() != 4) {
					d.decrmSleep();
				}
				if (hero.isNearDragon(d)) {
					if (hero.hasSword())
						System.out.println("Mataste o dragão");
					else
						System.out.println("Foste morto pelo dragão");
				}
				if (eagle.EagleatDrag(d, sword))
					System.out.println("Águia morta pelo dragão");
			}
		}
	}

	/**
	 * Moves the hero.
	 *
	 * @param movement the direction
	 */
	public void moveHero(int movement) {
		hero.move(movement, maze);
		if(dragonsAreDead() && hero.hasSword() && !isExitOpen()) {
			maze.setPosElem(maze.getExitPos(), ' ');
			this.setExitOpen(true);
		}
		if (!sword.isPickedUp()) {
			if (hero.pickedUpSword(sword)) {
				System.out.println("Apanhaste a espada");
			}
		}
		if (eagle.isAlive()) {
			if (hero.getEagle()) {
				if (eagle.hasSword()) {
					eagle.retrieveSword(sword);
					if(eagle.isflying()) {
						System.out.println("Águia a trazer a espada");
					}
				} else {
					eagle.EagleGetSword(sword);
				}
			}
			if (eagle.isAtHero() && eagle.isAlive())
				eagle.move(movement, maze);

			if (eagle.getWPos() == hero.getWPos() && eagle.getHPos() == hero.getHPos() && 
					eagle.hasSword() && eagle.isAlive() && !eagle.isflying()) {
				hero.catchEagle(eagle);
			}
		}
		if (heroAtExit()) {
			this.exit = true;
		}
	}
}
