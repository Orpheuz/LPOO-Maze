package maze.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import maze.logic.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateMaze.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class CreateMaze extends JPanel {

	private boolean heroPlaced = false, exitPlaced = false,
			swordPlaced = false, foundTheExit;
	private Hero hero;
	private Sword sword;
	private Eagle eagle;
	private Dragon[] dragons;
	private Elements[] elems;
	private boolean[][] visited;
	private char currentElement = 'a';
	private MazeLogic mazel;
	private int[] cell = { 0, 0 };
	private int tileSizeW, tileSizeH;
	private int borderX, borderY;

	private static final long serialVersionUID = 1L;

	/**
	 * Creates the maze panel.
	 * 
	 * @param mazel
	 *            the maze logic
	 */

	public CreateMaze(MazeLogic mazel) {
		setLayout(null);
		this.mazel = mazel;
		visited = new boolean[mazel.getMazeopt().getSize() * 2 + 1][mazel
				.getMazeopt().getSize() * 2 + 1];
		MyMouseAdapter mouseAdapter = new MyMouseAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		addButtons();
	}

	/**
	 * Gets the maze logic.
	 * 
	 * @return the maze logic
	 */
	public MazeLogic getMazel() {
		return mazel;
	}

	/**
	 * Sets the maze logic.
	 * 
	 * @param mazel
	 *            the new maze logic
	 */
	public void setMazel(MazeLogic mazel) {
		this.mazel = mazel;
	}

	private void createElems() {
		int nElems = mazel.getMazeopt().getNdrag() + 3;
		eagle = new Eagle(hero.getPos());
		elems = new Elements[nElems];
		elems[0] = hero;
		elems[1] = sword;
		elems[2] = eagle;
		for (int i = 3; i < elems.length; i++) {

			elems[i] = dragons[i - 3];
		}
	}

	private boolean dragsPlaced() {
		if (dragons == null) {
			dragons = new Dragon[mazel.getMazeopt().getNdrag()];
		}
		for (int i = 0; i < dragons.length; i++) {
			if (dragons[i] == null) {
				return false;
			}
		}
		return true;
	}

	private void addButtons() {

		JButton btnTree = new JButton("");
		btnTree.setBounds(0, 13, 60, 60);
		btnTree.setContentAreaFilled(false);
		btnTree.setFocusPainted(false);
		btnTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentElement = MazeBuild.MAZE_WALL;
			}
		});
		btnTree.setIcon(new ImageIcon(this.getClass().getResource(
				"resources/MTree.png")));
		add(btnTree);

		JButton btnHero = new JButton("");
		btnHero.setBounds(72, 13, 60, 60);
		btnHero.setContentAreaFilled(false);
		btnHero.setFocusPainted(false);
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentElement = MazeBuild.MAZE_HERO;
			}
		});
		btnHero.setIcon(new ImageIcon(this.getClass().getResource(
				"resources/HeroS.png")));
		add(btnHero);

		JButton btnDragon = new JButton("");
		btnDragon.setBounds(144, 13, 60, 60);
		btnDragon.setContentAreaFilled(false);
		btnDragon.setFocusPainted(false);
		btnDragon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentElement = MazeBuild.MAZE_DRAGON;
			}
		});
		btnDragon.setIcon(new ImageIcon(this.getClass().getResource(
				"resources/SDragon.png")));
		add(btnDragon);

		JButton btnSword = new JButton("");
		btnSword.setBounds(216, 13, 60, 60);
		btnSword.setContentAreaFilled(false);
		btnSword.setFocusPainted(false);
		btnSword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentElement = MazeBuild.MAZE_SWORD;
			}
		});
		btnSword.setIcon(new ImageIcon(this.getClass().getResource(
				"resources/Sword.png")));
		add(btnSword);

		JButton btnExit = new JButton("");
		btnExit.setBounds(288, 13, 60, 60);
		btnExit.setContentAreaFilled(false);
		btnExit.setFocusPainted(false);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentElement = MazeBuild.MAZE_EXIT;
			}
		});
		btnExit.setIcon(new ImageIcon(this.getClass().getResource(
				"resources/Exit.png")));
		add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(467, 13, 95, 60);
		btnClear.setContentAreaFilled(false);
		btnClear.setFocusPainted(false);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mazel.resetMaze();
				mazel.createMazeBorder(mazel.getMazeopt().getSize());
				heroPlaced = false;
				swordPlaced = false;
				exitPlaced = false;
				dragons = new Dragon[mazel.getMazeopt().getNdrag()];
				repaint();
			}
		});
		add(btnClear);

		JButton btnFinish = new JButton("Finish");
		btnFinish.setBounds(574, 13, 105, 60);
		btnFinish.setContentAreaFilled(false);
		btnFinish.setFocusPainted(false);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (heroPlaced && swordPlaced && exitPlaced && dragsPlaced()) {
					if (findExit(hero.getWPos(), hero.getHPos())) {
						createElems();
						if (!overlayElems()) {
							mazel.setElems(elems);
							mazel.setHero(hero);
							mazel.setEagle(eagle);
							mazel.setSword(sword);
							mazel.setDragons(dragons);
							mazel.startMazeC();
							GameFrame.setMazeL(mazel);
							GameFrame.setCreateMaze(false);
							setVisible(false);
						}
					}
				}
			}
		});
		add(btnFinish);

		JButton btnRandom = new JButton("Random");
		btnRandom.setBounds(360, 13, 95, 60);
		btnRandom.setContentAreaFilled(false);
		btnRandom.setFocusPainted(false);
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mazel.resetMaze();
				mazel.createMaze();
				exitPlaced = true;
				repaint();
			}
		});
		add(btnRandom);
	}

	char getMazeValue(int x, int y) {
		return mazel.getMazeCreate()[y][x];
	}

	int goRight(int x, int y) {
		if (x == (mazel.getMazeCreate().length - 1))
			return -1;
		return (getMazeValue(x + 1, y));
	}

	int goLeft(int x, int y) {
		if (x == 0)
			return -1;
		return (getMazeValue(x - 1, y));
	}

	int goUp(int x, int y) {
		if (y == 0)
			return -1;
		return (getMazeValue(x, y - 1));
	}

	int goDown(int x, int y) {
		if (y == (mazel.getMazeCreate().length - 1))
			return -1;
		return (getMazeValue(x, y + 1));
	}

	private boolean findExit(int x, int y) {
		if (getMazeValue(x, y) == MazeBuild.MAZE_EXIT) // found exit
		{
			foundTheExit = true;
			return true;
		} else {
			visited[y][x] = true; // mark as visited

			// goes to the right
			if (goRight(x, y) != -1 && goRight(x, y) != MazeBuild.MAZE_WALL
					&& visited[y][x + 1] == false)
				findExit(x + 1, y);

			if (foundTheExit)
				return true;

			// goes to the left
			if (goLeft(x, y) != -1 && goLeft(x, y) != MazeBuild.MAZE_WALL
					&& visited[y][x - 1] == false)
				findExit(x - 1, y);

			if (foundTheExit)
				return true;

			// goes up
			if (goUp(x, y) != -1 && goUp(x, y) != MazeBuild.MAZE_WALL
					&& visited[y - 1][x] == false)
				findExit(x, y - 1);

			if (foundTheExit)
				return true;

			// goes down
			if (goDown(x, y) != -1 && goDown(x, y) != MazeBuild.MAZE_WALL
					&& visited[y + 1][x] == false)
				findExit(x, y + 1);

			if (foundTheExit)
				return true;
			return false;
		}
	}

	private boolean overlayElems() {

		for (Elements e : elems) {
			if (!(e instanceof Eagle)) {
				for (Elements el : elems) {
					if (!(el instanceof Eagle)) {
						if (e != el) {
							if (e.getWPos() == el.getWPos()
									&& e.getHPos() == el.getHPos()) {
								return true;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < mazel.getMazeCreate().length; i++) {
			for (int j = 0; j < mazel.getMazeCreate()[i].length; j++) {
				if (getMazeValue(i, j) == 'x') {
					for (Elements e : elems) {
						if (!(e instanceof Eagle)) {
							if (e.getWPos() == j && e.getHPos() == i) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private char getMazeElem(int[] cell) {
		return mazel.getMazeCreate()[cell[1]][cell[0]];
	}

	/**
	 * Paint component.
	 *
	 * @param g the graphics
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		calcTileSize();
		Image img;
		ImageLoader i1 = new ImageLoader();
		img = i1.getImage(' ', 4);
		char[][] maze = mazel.getMazeCreate();
		g.drawImage(img, 0, 100, this.getWidth(), this.getHeight(), 0, 0,
				img.getWidth(null), img.getHeight(null), this);
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (maze[i][j] != ' '
						&& (maze[i][j] == 'S' || maze[i][j] == 'x')) {
					img = i1.getImage(maze[i][j], 3);
					g.drawImage(img, borderX + (j * tileSizeW), borderY
							+ (i * tileSizeH), borderX + ((j + 1) * tileSizeW),
							borderY + ((i + 1) * tileSizeH), 0, 0,
							img.getWidth(null), img.getHeight(null), this);
				}
			}
		}
		if (heroPlaced) {
			img = i1.getImage(hero.getElem(), 3);
			g.drawImage(img, borderX + hero.getHPos() * tileSizeW + tileSizeW
					/ 3, borderY + hero.getWPos() * tileSizeH + tileSizeH / 5,
					borderX + (hero.getHPos() + 1) * tileSizeW - tileSizeW / 3,
					borderY + (hero.getWPos() + 1) * tileSizeH - tileSizeH / 5,
					0, 0, img.getWidth(null), img.getHeight(null), this);
		}
		if (swordPlaced) {

			img = i1.getImage(sword.getElem(), 4);
			g.drawImage(
					img,
					borderX + sword.getHPos() * tileSizeW + tileSizeW / 4,
					borderY + sword.getWPos() * tileSizeH + tileSizeH / 4,
					borderX + (sword.getHPos() + 1) * tileSizeW - tileSizeW / 4,
					borderY + (sword.getWPos() + 1) * tileSizeH - tileSizeH / 4,
					0, 0, img.getWidth(null), img.getHeight(null), this);
		}
		if (dragons != null) {
			for (Dragon d : dragons) {
				if (d != null) {
					img = i1.getImage(d.getElem(), 4);
					g.drawImage(img, borderX + d.getHPos() * tileSizeW, borderY
							+ d.getWPos() * tileSizeH, borderX
							+ (d.getHPos() + 1) * tileSizeW,
							borderY + (d.getWPos() + 1) * tileSizeH, 0, 0,
							img.getWidth(null), img.getHeight(null), this);
				}
			}
		}
	}

	/**
	 * Calculates the size of the tiles and adjusts the images to fit the screen
	 * depending on the size of the maze.
	 */
	public void calcTileSize() {
		if ((mazel.getMazeSize()) >= 10 && (mazel.getMazeSize()) < 16) {
			double d = 6 - ((mazel.getMazeSize()) - 10);
			this.borderX = 5 + ((mazel.getMazeSize()) - 9);
			this.borderY = 2 * ((mazel.getMazeSize()) - 9) + 100;
			this.tileSizeW = (int) (this.getWidth() / ((mazel.getMazeSize())) - d);
			this.tileSizeH = (int) ((this.getHeight() - 100)
					/ (mazel.getMazeSize()) - d);
		} else if ((mazel.getMazeSize()) >= 16 && (mazel.getMazeSize()) <= 22) {
			double d = 3 - ((mazel.getMazeSize()) - 16) * 0.3;
			this.borderX = 10 + ((mazel.getMazeSize()) - 15);
			this.borderY = 2 * ((mazel.getMazeSize()) - 15) + 100;
			this.tileSizeW = (int) (this.getWidth() / ((mazel.getMazeSize())) - d);
			this.tileSizeH = (int) ((this.getHeight() - 100)
					/ (mazel.getMazeSize()) - d);
		} else {
			double d = 1.5 - ((mazel.getMazeSize()) - 23) * 0.3;
			this.borderX = 5 - 2 * ((mazel.getMazeSize()) - 22);
			this.borderY = 18 - 2 * ((mazel.getMazeSize()) - 22) + 100;
			this.tileSizeW = (int) (this.getWidth() / ((mazel.getMazeSize())) - d);
			this.tileSizeH = (int) ((this.getHeight() - 100)
					/ (mazel.getMazeSize()) - d);
		}
	}

	private boolean comparePos(int[] pos, int[] cell) {
		if (pos[0] == cell[1] && pos[1] == cell[0])
			return true;
		else
			return false;
	}

	private boolean isExteriorWall(int[] cell) {
		if (((cell[0] == 0 || cell[1] == (mazel.getMazeCreate().length - 1)) || (cell[1] == 0 || cell[0] == (mazel
				.getMazeCreate().length - 1)))
				&& !(cell[0] == (mazel.getMazeCreate().length - 1) && cell[1] == (mazel
						.getMazeCreate().length - 1))
				&& !(cell[0] == 0 && cell[1] == 0)
				&& !(cell[0] == 0 && cell[1] == (mazel.getMazeCreate().length - 1))
				&& !(cell[1] == 0 && cell[0] == (mazel.getMazeCreate().length - 1)))
			return true;
		return false;
	}

	private class MyMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (e.getY() > 100) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					if (currentElement != 'a') {
						if (currentElement == MazeBuild.MAZE_WALL
								&& getMazeElem(cell) == ' ') {
							mazel.getMazeCreate()[cell[1]][cell[0]] = 'x';

						} else if (currentElement == MazeBuild.MAZE_SWORD
								&& getMazeElem(cell) == ' ') {
							if (!swordPlaced) {
								int[] pos = new int[2];
								pos[0] = cell[1];
								pos[1] = cell[0];
								sword = new Sword(pos);
								swordPlaced = true;
							}
						} else if (currentElement == MazeBuild.MAZE_HERO
								&& getMazeElem(cell) == ' ') {
							if (!heroPlaced) {
								int[] pos = new int[2];
								pos[0] = cell[1];
								pos[1] = cell[0];
								hero = new Hero(pos);
								heroPlaced = true;
							}
						} else if (currentElement == MazeBuild.MAZE_EXIT) {
							if (!exitPlaced && isExteriorWall(cell)) {
								mazel.getMazeCreate()[cell[1]][cell[0]] = 'S';
								exitPlaced = true;
							}
						} else if (!dragsPlaced()) {
							if (currentElement == MazeBuild.MAZE_DRAGON
									&& getMazeElem(cell) == ' ') {
								int[] pos = new int[2];
								pos[0] = cell[1];
								pos[1] = cell[0];
								Dragon drag = new Dragon(pos);
								for (int i = 0; i < dragons.length; i++) {
									if (dragons[i] == null) {
										dragons[i] = drag;
										break;
									}
								}
							}
						}
					}
					repaint();
					break;
				case MouseEvent.BUTTON3:

					if (getMazeElem(cell) == MazeBuild.MAZE_WALL)
						mazel.getMazeCreate()[cell[1]][cell[0]] = ' ';
					if (swordPlaced) {
						if (comparePos(cell, sword.getPos())) {
							swordPlaced = false;
							mazel.getMazeCreate()[cell[1]][cell[0]] = ' ';
						}
					}
					if (heroPlaced) {
						if (comparePos(cell, hero.getPos())) {
							heroPlaced = false;
							mazel.getMazeCreate()[cell[1]][cell[0]] = ' ';
						}
					}
					if (getMazeElem(cell) == MazeBuild.MAZE_EXIT) {
						mazel.getMazeCreate()[cell[1]][cell[0]] = ' ';
						exitPlaced = false;
					}
					if (dragons != null) {
						for (int i = 0; i < dragons.length; i++) {
							if (dragons[i] != null) {
								if (comparePos(cell, dragons[i].getPos())) {
									dragons[i] = null;
								}
							}
						}
					}
					repaint();
					break;
				}
			}
		}
					
		public void mouseMoved(MouseEvent e) {

			if ((e.getX() > 0 && e.getX() < getWidth())
					&& (e.getY() > 0 && e.getY() < getHeight())) {

				if (mazel.getMazeSize() >= 20) {
					int x = (int) ((e.getX() - (getWidth() - tileSizeW
							* (mazel.getMazeSize() + 1)) / 2.0) / tileSizeW);
					int y = (int) ((e.getY() - getHeight() * 0.02 - (tileSizeH) - (getHeight() - tileSizeH
							* (mazel.getMazeSize() + 1)) / 2.0) / tileSizeH);

					setWPos(x);
					setHPos(y);
				}

				else {
					int x = (int) ((e.getX() - (getWidth() - tileSizeW
							* (mazel.getMazeSize() + 1)) / 2.0) / tileSizeW);
					int y = (int) ((e.getY() - getHeight() * 0.015
							- (tileSizeH / 2) - (getHeight() - tileSizeH
							* (mazel.getMazeSize() + 1)) / 2.0) / tileSizeH);

					setWPos(x);
					setHPos(y);
				}
				repaint();
			}
		}
	}

	/**
	 * Gets the cell y position.
	 * 
	 * @return the cell y position
	 */
	public int getCellWPos() {
		return cell[0];
	}

	/**
	 * Gets the cell x position.
	 * 
	 * @return the cell x position
	 */
	public int getCellHPos() {
		return cell[1];
	}

	/**
	 * Sets the y position.
	 * 
	 * @param Wpos
	 *            the new y position
	 */
	public void setWPos(int Wpos) {
		this.cell[0] = Wpos;
	}

	/**
	 * Sets the x position.
	 * 
	 * @param Hpos
	 *            the new x position
	 */
	public void setHPos(int Hpos) {
		this.cell[1] = Hpos;
	}
}
