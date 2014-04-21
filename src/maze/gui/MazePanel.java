package maze.gui;

import java.awt.Graphics;
import java.awt.Image;

import maze.logic.*;
import javax.swing.JPanel;

/**
 * The Class MazePanel.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class MazePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int dir = 4;
	private MazeLogic mazel;
	private int tileSizeW, tileSizeH;
	private int borderX, borderY;

	/**
	 * Gets the width of the tiles.
	 *
	 * @return the size of the width
	 */
	public int getTileSizeW() {
		return tileSizeW;
	}

	/**
	 * Gets the height of the tiles.
	 *
	 * @return the size of the height
	 */
	public int getTileSizeH() {
		return tileSizeH;
	}

	/**
	 * Instantiates a new maze panel.
	 *
	 * @param mazel the maze logic
	 */
	public MazePanel(MazeLogic mazel) {
		this.mazel = mazel;
	}

	/**
	 * Allows the user to change the maze logic of the maze panel.
	 *
	 * @param mazel the new maze logic
	 */
	public void setMazel(MazeLogic mazel) {
		this.mazel = mazel;
	}

	/**
	 * Calculates the size of the tiles, and adjusts itself to fit the screen depending on the
	 * size the user chose for the maze.
	 */
	public void calcTileSize() {
		if (mazel.getMazeopt().isIsdefault()) {
			double d = 0;
			this.borderX = 2 * (mazel.getMazeSize() - 9);
			this.borderY = 2 * (mazel.getMazeSize() - 9);
			this.tileSizeW = (int) ((this.getWidth() / mazel.getMazeSize()) - d);
			this.tileSizeH = (int) ((this.getHeight() / mazel.getMazeSize()) - d);
		} else if (mazel.getMazeSize() >= 10 && mazel.getMazeSize() < 16
				&& !mazel.getMazeopt().isIsdefault()) {
			double d = 8 - (mazel.getMazeSize() - 10);
			this.borderX = 6 + (mazel.getMazeSize() - 9);
			this.borderY = 2 * (mazel.getMazeSize() - 9);
			this.tileSizeW = (int) ((this.getWidth() / mazel.getMazeSize()) - d);
			this.tileSizeH = (int) ((this.getHeight() / mazel.getMazeSize()) - d);
		} else if (mazel.getMazeSize() >= 16 && mazel.getMazeSize() <= 22) {
			double d = 4 - (mazel.getMazeSize() - 16) * 0.3;
			this.borderX = 15 + (mazel.getMazeSize() - 15);
			this.borderY = 2 * (mazel.getMazeSize() - 15);
			this.tileSizeW = (int) ((this.getWidth() / mazel.getMazeSize()) - d);
			this.tileSizeH = (int) ((this.getHeight() / mazel.getMazeSize()) - d);
		} else {
			double d = 3 - (mazel.getMazeSize() - 23) * 0.3;
			this.borderX = 28 - 2 * (mazel.getMazeSize() - 22);
			this.borderY = 25 - 2 * (mazel.getMazeSize() - 22);
			if (mazel.getMazeSize() == 30) {
				d = (1.5);
			}
			this.tileSizeW = (int) ((this.getWidth() / mazel.getMazeSize()) - d);
			this.tileSizeH = (int) ((this.getHeight() / mazel.getMazeSize()) - d);
		}

	}

	/**
	 *  
	 *
	 * @param g the graphics
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		calcTileSize();
		Image img;
		Elements[] elem = mazel.getElems();
		ImageLoader i1 = new ImageLoader();
		if (GameFrame.gameHasStarted()) {
			char[][] maze = mazel.getEmptyMaze();
			img = i1.getImage(' ', getDir());
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
					img.getWidth(null), img.getHeight(null), this);
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze.length; j++) {
					if (maze[i][j] != ' ') {
						img = i1.getImage(maze[i][j], getDir());
						g.drawImage(img, borderX + (j * tileSizeW), borderY
								+ (i * tileSizeH), borderX
								+ ((j + 1) * tileSizeW), borderY
								+ ((i + 1) * tileSizeH), 0, 0,
								img.getWidth(null), img.getHeight(null), this);
					}
				}
			}
			for (Elements e : elem) {
				if (e.isAlive() || (e instanceof Hero)) {
					if (e instanceof Eagle) {
						if (!((Eagle) e).isAtHero()) {
							if (((Eagle) e).hasSword()) {
								img = i1.getImage('c', getDir());
								g.drawImage(
										img,
										borderX
												+ (e.getHPos() * tileSizeW + tileSizeW / 4),
										borderY
												+ (e.getWPos() * tileSizeH + tileSizeH / 4),
										borderX
												+ ((e.getHPos() + 1)
														* tileSizeW - tileSizeW / 4),
										borderY
												+ ((e.getWPos() + 1)
														* tileSizeH - tileSizeH / 4),
										0, 0, img.getWidth(null),
										img.getHeight(null), this);
							} else {
								img = i1.getImage(e.getElem(), getDir());
								g.drawImage(
										img,
										borderX
												+ (e.getHPos() * tileSizeW + tileSizeW / 4),
										borderY
												+ (e.getWPos() * tileSizeH + tileSizeH / 4),
										borderX
												+ ((e.getHPos() + 1)
														* tileSizeW - tileSizeW / 4),
										borderY
												+ ((e.getWPos() + 1)
														* tileSizeH - tileSizeH / 4),
										0, 0, img.getWidth(null),
										img.getHeight(null), this);
							}
						}

					} else if (e instanceof Hero) {
						if (!e.isAlive()) {
							img = i1.getImage('h', getDir());
							g.drawImage(img, borderX + e.getHPos() * tileSizeW
									+ tileSizeW / 3, borderY + e.getWPos()
									* tileSizeH + tileSizeH / 5,
									borderX + (e.getHPos() + 1) * tileSizeW
											- tileSizeW / 3, borderY
											+ (e.getWPos() + 1) * tileSizeH
											- tileSizeH / 5, 0, 0,
									img.getWidth(null), img.getHeight(null), this);
						} else {
							img = i1.getImage(e.getElem(), getDir());
							g.drawImage(img, borderX + e.getHPos() * tileSizeW
									+ tileSizeW / 3, borderY + e.getWPos()
									* tileSizeH + tileSizeH / 5,
									borderX + (e.getHPos() + 1) * tileSizeW
											- tileSizeW / 3, borderY
											+ (e.getWPos() + 1) * tileSizeH
											- tileSizeH / 5, 0, 0,
									img.getWidth(null), img.getHeight(null), this);
						}

					} else if (e instanceof Sword) {
						if (!((Sword) e).isInDrag()) {
							img = i1.getImage(e.getElem(), getDir());
							g.drawImage(img, borderX + e.getHPos() * tileSizeW
									+ tileSizeW / 4, borderY + e.getWPos()
									* tileSizeH + tileSizeH / 4,
									borderX + (e.getHPos() + 1) * tileSizeW
											- tileSizeW / 4,
									borderY + (e.getWPos() + 1) * tileSizeH
											- tileSizeH / 4, 0, 0,
									img.getWidth(null), img.getHeight(null),
									this);
						}

					} else if (e instanceof Dragon) {
						img = i1.getImage(e.getElem(), ((Dragon) e).getDir());
						g.drawImage(img, borderX + e.getHPos() * tileSizeW,
								borderY + e.getWPos() * tileSizeH,
								borderX + (e.getHPos() + 1) * tileSizeW,
								borderY + (e.getWPos() + 1) * tileSizeH, 0, 0,
								img.getWidth(null), img.getHeight(null), this);
					} else {
						img = i1.getImage(e.getElem(), getDir());
						g.drawImage(img, borderX + e.getHPos() * tileSizeW,
								borderY + e.getWPos() * tileSizeH,
								borderX + (e.getHPos() + 1) * tileSizeW,
								borderY + (e.getWPos() + 1) * tileSizeH, 0, 0,
								img.getWidth(null), img.getHeight(null), this);
					}
				}
			}
		}
	}

	/**
	 * Gets the direction the image should be facing.
	 *
	 * @return the direction the image should be facing
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * Sets the direction the image should be facing.
	 *
	 * @param dir the new direction the image should be facing
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}
}
