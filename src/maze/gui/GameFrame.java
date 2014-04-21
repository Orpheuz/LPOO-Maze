package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.CardLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import maze.logic.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The Class GameFrame.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel inGameMenu;
	private JPanel menuPanel;
	private Options options;
	private static MazeLogic mazel;
	private CreateMaze mazeC;
	private MazePanel mazePanel;
	private JButton newgameB;
	private JButton optB;
	private JButton exitB;
	private JButton savegameB1, savegameB2, savegameB3;
	private static boolean startgame = false;
	private static boolean createMaze = false;
	private static boolean customMaze = false;
	private static boolean eagleGotSword = false;
	private static boolean eagleDied = false;
	private static final String savedGamesFolder = System
			.getProperty("user.dir") + "/Saved Games/";
	private static final String extension = ".mazefile";
	private static final String savegame = "savegame";
	private static int keyUP = KeyEvent.VK_W;
	private static int keyLEFT = KeyEvent.VK_A;
	private static int keyDOWN = KeyEvent.VK_S;
	private static int keyRIGHT = KeyEvent.VK_D;
	private static int keyEAGLE = KeyEvent.VK_E;
	private static int keyPAUSE = KeyEvent.VK_ESCAPE;

	/**
	 * The main method.
	 *
	 * @param args arguments for the command-line
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					try {
						for (LookAndFeelInfo info : UIManager
								.getInstalledLookAndFeels()) {
							if ("Nimbus".equals(info.getName())) {
								UIManager.setLookAndFeel(info.getClassName());
								break;
							}
						}
					} catch (Exception e) {

					}
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					ImageLoader i1 = new ImageLoader();
					Image i = i1.getImage('D', 3);
					frame.setIconImage(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Instantiates a new game frame.
	 */
	public GameFrame() {
		setTitle("Dragon's Maze");
		setResizable(false);
		setContentPane(getContentPane());
		getContentPane().setLayout(new CardLayout(0, 0));
		setSize(800, 600);
		mazel = new MazeLogic();
		mazePanel = new MazePanel(mazel);
		menuPanel = new MenuPanel();
		inGameMenu = new InGameMenu();
		mazeC = new CreateMaze(mazel);
		menuPanel.setLayout(null);
		menuPanel.setSize(800, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
		getContentPane().add(menuPanel, "menupanel");
		getContentPane().add(inGameMenu, "ingamemenu");
		getContentPane().add(mazePanel, "mazepanel");
		getContentPane().add(mazeC, "createmaze");
		addMenuButtons();
		Action();

	}

	private void startGame(MazeLogic mazel) {

		startgame = true;
		mazePanel.setMazel(mazel);
		mazePanel.setFocusable(true);
		mazel.createMaze();
		adjustWindow();
		showPanel("mazepanel");
	}

	private void createGame(MazeLogic mazel) {

		createMaze = true;
		customMaze = true;
		mazeC.setMazel(mazel);
		mazeC.setFocusable(true);
		mazel.createMazeBorder(mazel.getMazeopt().getSize());
		if (mazel.getMazeSize() >= 10 && mazel.getMazeSize() < 16) {
			setSize(700, 800);
		} else if (mazel.getMazeSize() >= 16 && mazel.getMazeSize() <= 22) {
			setSize(800, 900);
		} else {
			setSize(900, 1000);
		}
		showPanel("createmaze");
	}

	/**
	 * Updates the load game buttons with the saved games.
	 */
	public void updateLoadButtons() {
		if (fileExists("1")) {
			savegameB1
					.setText(("<html><center><p>Load<p/><br />Game 1<center/></html>"));
		} else
			savegameB1.setText("Empty slot");

		if (fileExists("2")) {
			savegameB2
					.setText(("<html><center><p>Load<p/><br />Game 2<center/></html>"));
		} else
			savegameB2.setText("Empty slot");

		if (fileExists("3")) {
			savegameB3
					.setText(("<html><center><p>Load<p/><br />Game 3<center/></html>"));
		} else
			savegameB3.setText("Empty slot");
	}

	/**
	 * Adds the menu buttons.
	 */
	public void addMenuButtons() {
		newgameB = new JButton("");
		newgameB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eagleDied = false;
				eagleGotSword = false;
				if (createMaze) {
					createGame(mazel);
				} else if  (customMaze){
					startgame = true;
					mazePanel.setMazel(mazel);
					mazePanel.setFocusable(true);
					adjustWindow();
					showPanel("mazepanel");
					
				} else {
					startGame(mazel);
				}
			}
		});
		newgameB.setBounds(600, 170, 186, 47);
		newgameB.setOpaque(false);
		newgameB.setContentAreaFilled(false);
		newgameB.setBorderPainted(false);
		menuPanel.add(newgameB);

		optB = new JButton("");
		optB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (options == null) {
					options = new Options();
				}
				options.setModal(true);
				options.setLocationRelativeTo(getContentPane());
				options.setVisible(true);
			}
		});
		optB.setBounds(594, 279, 186, 47);
		optB.setOpaque(false);
		optB.setContentAreaFilled(false);
		optB.setBorderPainted(false);
		menuPanel.add(optB);

		exitB = new JButton("");
		exitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitB.setOpaque(false);
		exitB.setContentAreaFilled(false);
		exitB.setBorderPainted(false);
		exitB.setBounds(594, 392, 186, 47);
		menuPanel.add(exitB);

		// save game buttons

		savegameB1 = new JButton("");
		savegameB1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileExists("1")) {
					loadGame("1");
				}
			}
		});

		savegameB1.setBounds(78, 378, 101, 101);
		savegameB1.setOpaque(true);
		savegameB1.setContentAreaFilled(false);
		savegameB1.setFocusPainted(false);
		menuPanel.add(savegameB1);

		savegameB2 = new JButton("");
		savegameB2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileExists("2")) {
					loadGame("2");
				}
			}
		});

		savegameB2.setBounds(201, 378, 101, 101);
		savegameB2.setOpaque(true);
		savegameB2.setContentAreaFilled(false);
		savegameB2.setFocusPainted(false);
		menuPanel.add(savegameB2);

		savegameB3 = new JButton("");
		savegameB3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileExists("3")) {
					loadGame("3");
				}
			}
		});

		savegameB3.setBounds(323, 378, 101, 101);
		savegameB3.setOpaque(true);
		savegameB3.setContentAreaFilled(false);
		savegameB3.setFocusPainted(false);
		menuPanel.add(savegameB3);

		// ingamemenu buttons

		JButton btnResume = new JButton();
		btnResume.setBounds(600, 170, 186, 47);
		btnResume.setOpaque(false);
		btnResume.setContentAreaFilled(false);
		btnResume.setBorderPainted(false);
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mazel.getMazeSize() >= 10 && mazel.getMazeSize() < 16) {
					setSize(800, 800);
				} else if (mazel.getMazeSize() >= 16
						&& mazel.getMazeSize() <= 22) {
					setSize(900, 900);
				} else {
					setSize(1000, 1000);
				}
				showPanel("mazepanel");
			}
		});
		inGameMenu.add(btnResume);

		JButton btnSaveGame = new JButton();
		btnSaveGame.setBounds(589, 287, 186, 47);
		btnSaveGame.setOpaque(false);
		btnSaveGame.setContentAreaFilled(false);
		btnSaveGame.setBorderPainted(false);
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});
		inGameMenu.add(btnSaveGame);

		JButton btnQuit = new JButton();
		btnQuit.setBounds(583, 402, 186, 47);
		btnQuit.setOpaque(false);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(getContentPane(),
						"You will lose the game if you didn't save it!",
						"Are you sure you want to exit?",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					showPanel("menupanel");
				}
			}
		});
		inGameMenu.add(btnQuit);
		updateLoadButtons();
	}

	/**
	 * Changes the maze's images depending on the user's moves.
	 */
	public void Action() {
		mazePanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == keyUP) {
					mazel.moveHero(MovingElem.UP);
					mazePanel.setDir(MovingElem.UP);
					mazel.moveDragons();
				} else if (code == keyLEFT) {
					mazel.moveHero(MovingElem.LEFT);
					mazePanel.setDir(MovingElem.LEFT);
					mazel.moveDragons();
				} else if (code == keyDOWN) {
					mazel.moveHero(MovingElem.DOWN);
					mazePanel.setDir(MovingElem.DOWN);
					mazel.moveDragons();
				} else if (code == keyRIGHT) {
					mazel.moveHero(MovingElem.RIGHT);
					mazePanel.setDir(MovingElem.RIGHT);
					mazel.moveDragons();
				} else if (code == keyEAGLE) {
					mazel.moveHero(Hero.EAGLE);
				} else if (code == keyPAUSE) {
					showPanel("ingamemenu");
					setSize(800, 600);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					setLocation(dim.width / 2 - getSize().width / 2, dim.height
							/ 2 - getSize().height / 2);
				}
				repaint();
				if(!mazel.getEagle().isAlive() && !mazel.getEagle().isAtHero() && !eagleDied) {
					JOptionPane.showMessageDialog(getContentPane(), "A dragon killed your eagle!!");
					eagleDied = true;
				}
				if(mazel.getEagle().hasSword() && !eagleGotSword) {
					JOptionPane.showMessageDialog(getContentPane(), "Your eagle picked up the sword!!");
					eagleGotSword = true;
				}
				if (mazel.getExitState()) {
					mazel.setExit(false);
					JOptionPane.showMessageDialog(getContentPane(), "You win!");
					if(customMaze) {
						customMaze = false;
					}
					showPanel("menupanel");
					setSize(800, 600);
				}
				if (mazel.heroDied()) {
					JOptionPane
							.showMessageDialog(getContentPane(), "You lose!");;
					if(customMaze) {
						customMaze = false;
					}
					showPanel("menupanel");
					setSize(800, 600);
				}

			}
		});
		mazePanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent cEvt) {
				Component src = (Component) cEvt.getSource();
				src.requestFocusInWindow();
			}

		});
		mazeC.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent cEvt) {
				Component src = (Component) cEvt.getSource();
				src.requestFocusInWindow();
			}

		});
		//check when create maze visibility is set to false
		mazeC.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				setSize(800, 600);
			}
		});
	}

	/**
	 * Shows if the game has started.
	 *
	 * @return true, if the game has started
	 */
	public static boolean gameHasStarted() {
		return startgame;
	}

	/**
	 * Shows the panel.
	 *
	 * @param newPanel the new panel to be shown
	 */
	public void showPanel(String newPanel) {
		CardLayout cl = (CardLayout) (getContentPane().getLayout());
		cl.show(getContentPane(), "" + newPanel);
		getContentPane().revalidate();
		getContentPane().repaint();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
		if (newPanel == "menupanel") {
			updateLoadButtons();
		}
	}

	/**
	 * Sets the maze logic.
	 *
	 * @param mazel the new maze logic
	 */
	public static void setMazeL(MazeLogic mazel) {
		GameFrame.mazel = mazel;
	}

	/**
	 * Sets the maze options.
	 *
	 * @param mazeopt the new maze options
	 */
	public static void setMazeOpt(MazeOptions mazeopt) {
		mazel.setMazeopt(mazeopt);
	}

	private boolean fileExists(String number) {
		return new File(savedGamesFolder + savegame + number + extension)
				.isFile();
	}

	private void adjustWindow() {

		if (mazel.getMazeSize() >= 10 && mazel.getMazeSize() < 16) {
			setSize(800, 800);
		} else if (mazel.getMazeSize() >= 16 && mazel.getMazeSize() <= 22) {
			setSize(900, 900);
		} else {
			setSize(1000, 1000);
		}
	}

	private void loadGame(String number) {
		ObjectInputStream is = null;

		// load file options
		try {
			is = new ObjectInputStream(new FileInputStream(savedGamesFolder
					+ savegame + number + extension));
			mazel = (MazeLogic) is.readObject();
			startgame = true;
			mazePanel.setFocusable(true);
			mazePanel.setMazel(mazel);
			adjustWindow();
			showPanel("mazepanel");
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private void saveGame() {

		File savesFolder = new File(savedGamesFolder);
		if (!savesFolder.exists())
			savesFolder.mkdir();

		ObjectOutputStream file = null;
		try {
			file = new ObjectOutputStream(new FileOutputStream(savedGamesFolder
					+ "savegame" + getFileNum() + extension));
			file.writeObject(mazel);
			file.close();
			JOptionPane.showMessageDialog(null, "Game successfully saved.");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"An error occured while saving the game.");
		}
	}

	/**
	 * Gets the saved game file number.
	 *
	 * @return the saved game file number
	 */
	public String getFileNum() {
		int num = 0;

		for (int i = 1; i <= 3; i++) {
			if (fileExists("" + i)) {
				num++;
			}
		}
		if (num == 3) {
			File file3 = new File(savedGamesFolder + "savegame" + "3"
					+ extension);
			file3.delete();
			File file2 = new File(savedGamesFolder + "savegame" + "2"
					+ extension);
			file2.renameTo(file3);
			File file1 = new File(savedGamesFolder + "savegame" + "1"
					+ extension);
			file1.renameTo(file2);
			return ("" + 1);
		} else {
			num++;
			return ("" + num);
		}
	}

	/**
	 * Assigns the up function to a chosen key.
	 *
	 * @param keyUP the new up key
	 */
	public static void setKeyUP(int keyUP) {
		GameFrame.keyUP = keyUP;
	}

	/**
	 * Sets the left function to a chosen key.
	 *
	 * @param keyLEFT the new left key
	 */
	public static void setKeyLEFT(int keyLEFT) {
		GameFrame.keyLEFT = keyLEFT;
	}

	/**
	 * Sets the down function to a chosen key.
	 *
	 * @param keyDOWN the new down key
	 */
	public static void setKeyDOWN(int keyDOWN) {
		GameFrame.keyDOWN = keyDOWN;
	}

	/**
	 * Sets the right function to a chosen key.
	 *
	 * @param keyRIGHT the new right key
	 */
	public static void setKeyRIGHT(int keyRIGHT) {
		GameFrame.keyRIGHT = keyRIGHT;
	}

	/**
	 * Sets the eagle function to a chosen key.
	 *
	 * @param keyEAGLE the new eagle key
	 */
	public static void setKeyEAGLE(int keyEAGLE) {
		GameFrame.keyEAGLE = keyEAGLE;
	}

	/**
	 * Sets the pause function to a chosen key.
	 *
	 * @param keyPAUSE the new pause key
	 */
	public static void setKeyPAUSE(int keyPAUSE) {
		GameFrame.keyPAUSE = keyPAUSE;
	}

	/**
	 * Checks if the maze is created.
	 *
	 * @return true, if the maze is created
	 */
	public static boolean isCreateMaze() {
		return createMaze;
	}

	/**
	 * Allows the user to choose if the maze is created.
	 *
	 * @param createMaze true if the maze is created, false otherwise
	 */
	public static void setCreateMaze(boolean createMaze) {
		GameFrame.createMaze = createMaze;
	}
}
