package maze.gui;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import maze.logic.MazeOptions;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The Class Options.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class Options extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JFormattedTextField textField;
	private JSlider slider;
	private JRadioButton rdbtnDefault;
	private JRadioButton rdbtnRandom;
	private JRadioButton rdbtnStatic;
	private JRadioButton rdbtnMoving;
	private JRadioButton rdbtnMoveAndSleep;
	private JRadioButton rdbtnCreate;
	private JLabel lblNumberOfDragons;
	private JSpinner spinner;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	private MazeOptions mazeopt;
	private JButton btnRight;
	private JButton btnPause;
	private JButton btnEagle;

	/**
	 * Gets the maze options.
	 *
	 * @return the maze options
	 */
	public MazeOptions getMazeOpt() {
		return mazeopt;
	}

	/**
	 * This function is responsible for the GUI of the options menu. It allows the user to
	 * change the maze's options, such as the number and type of dragons, the size of the maze,
	 * 
	 */
	public Options() {

		mazeopt = new MazeOptions();
		// default options
		mazeopt.setSize(10);
		mazeopt.setDragtype(1);
		mazeopt.setIsdefault(true);
		mazeopt.setNdrag(1);
		setTitle("Dragon's Maze Options");
		setBounds(100, 100, 496, 434);
		getContentPane().setLayout(null);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(46, 352, 432, 35);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		// options window cancel button

		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int choice = JOptionPane.showConfirmDialog(getContentPane(),
						"Are you sure you want to discard the changes?",
						"Ignore changes", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		
				// options window ok button
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						int value = (Integer) spinner.getValue();
						mazeopt.setSize(value / 2);
						if (rdbtnDefault.isSelected()) {
							mazeopt.setIsdefault(true);
							mazeopt.setSize(5);
						} else if (rdbtnCreate.isSelected()) {
							mazeopt.setIsdefault(false);
							GameFrame.setCreateMaze(true);
						} else {
							mazeopt.setIsdefault(false);
						}
						if (rdbtnStatic.isSelected()) {
							mazeopt.setDragtype(1);
						} else if (rdbtnMoving.isSelected()) {
							mazeopt.setDragtype(2);
						} else
							mazeopt.setDragtype(3);
						mazeopt.setNdrag(slider.getValue());
						int choice = JOptionPane.showConfirmDialog(getContentPane(),
								"Are you sure you want to save the changes?",
								"Save options", JOptionPane.YES_NO_OPTION);
						if (choice == JOptionPane.YES_OPTION) {
							JOptionPane.showMessageDialog(getContentPane(),
									"Options saved.\n"
											+ "You can start a new game now!",
									"Success", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							GameFrame.setMazeOpt(mazeopt);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		// setting up options window labels and buttons

		// maze types

		JLabel lblMazeType = new JLabel("Maze type:");
		lblMazeType.setBounds(27, 35, 71, 16);
		getContentPane().add(lblMazeType);

		rdbtnDefault = new JRadioButton("Default");
		rdbtnDefault.setSelected(true);
		buttonGroup.add(rdbtnDefault);
		rdbtnDefault.setBounds(160, 31, 69, 25);
		getContentPane().add(rdbtnDefault);

		rdbtnRandom = new JRadioButton("Random");
		buttonGroup.add(rdbtnRandom);
		rdbtnRandom.setBounds(253, 31, 75, 25);
		getContentPane().add(rdbtnRandom);

		rdbtnCreate = new JRadioButton("Create maze");
		buttonGroup.add(rdbtnCreate);
		rdbtnCreate.setBounds(351, 31, 102, 25);
		getContentPane().add(rdbtnCreate);

		// maze size

		JLabel lblMazeSize = new JLabel("Maze size:");
		lblMazeSize.setBounds(27, 84, 88, 24);
		getContentPane().add(lblMazeSize);

		SpinnerNumberModel model1 = new SpinnerNumberModel(10, 10, 30, 1);
		spinner = new JSpinner(model1);
		spinner.setBounds(390, 85, 57, 22);
		getContentPane().add(spinner);

		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = (Integer) spinner.getValue();
				slider.setMinorTickSpacing(value / 5);
				slider.setMajorTickSpacing(value / 2);
				slider.setLabelTable(slider.createStandardLabels(value/2));
				slider.setMaximum(value);
			}
		};

		spinner.addChangeListener(listener);

		// number of dragons slider + textfield + tickings + mazesizechange

		lblNumberOfDragons = new JLabel("Number of dragons:");
		lblNumberOfDragons.setBounds(27, 141, 129, 16);
		getContentPane().add(lblNumberOfDragons);

		textField = new JFormattedTextField();
		textField.setBounds(390, 137, 57, 24);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				String typed = textField.getText();
				if (!typed.matches("\\d+") || typed.length() > 3) {
					return;
				}
				int value = Integer.parseInt(typed);
				slider.setValue(value);
			}
		});
		getContentPane().add(textField);

		ChangeListener listernerSlide = new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				JSlider source = (JSlider) event.getSource();
				textField.setText("" + source.getValue());
			}
		};
		slider = new JSlider();
		slider.addChangeListener(listernerSlide);
		slider.setMinimum(1);
		slider.setMaximum(10);
		slider.setValue(10);
		slider.setMinorTickSpacing(2);
		slider.setMajorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.setPaintTrack(true);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setBounds(160, 111, 200, 52);
		getContentPane().add(slider);

		// dragon types

		JLabel lblDragonType = new JLabel("Dragon type:");
		lblDragonType.setBounds(27, 195, 102, 16);
		getContentPane().add(lblDragonType);

		rdbtnStatic = new JRadioButton("Static");
		rdbtnStatic.setSelected(true);
		buttonGroup_1.add(rdbtnStatic);
		rdbtnStatic.setBounds(160, 191, 127, 25);
		getContentPane().add(rdbtnStatic);

		rdbtnMoving = new JRadioButton("Move");
		buttonGroup_1.add(rdbtnMoving);
		rdbtnMoving.setBounds(160, 221, 127, 25);
		getContentPane().add(rdbtnMoving);

		rdbtnMoveAndSleep = new JRadioButton("Move and Sleep");
		buttonGroup_1.add(rdbtnMoveAndSleep);
		rdbtnMoveAndSleep.setBounds(160, 252, 127, 25);
		getContentPane().add(rdbtnMoveAndSleep);

		// set keys

		JLabel lblSetKeys = new JLabel("Set Keys:");
		lblSetKeys.setBounds(27, 281, 56, 16);
		getContentPane().add(lblSetKeys);

		JButton btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				dialog.setResizable(false);
				dialog.setSize(200, 100);
				JLabel label = new JLabel("              Please press a key.");
				dialog.setLocationRelativeTo(getContentPane());
				label.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {
						int code = e.getKeyCode();
						GameFrame.setKeyUP(code);
						SwingUtilities.getWindowAncestor(e.getComponent())
								.dispose();
					}
				});
				label.setFocusable(true);
				dialog.getContentPane().add(label);
				dialog.setVisible(true);
			}
		});
		btnUp.setBounds(27, 317, 71, 25);
		getContentPane().add(btnUp);

		JButton btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				dialog.setResizable(false);
				dialog.setSize(200, 100);
				JLabel label = new JLabel("              Please press a key.");
				dialog.setLocationRelativeTo(getContentPane());
				label.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {
						GameFrame.setKeyLEFT(e.getKeyCode());
						SwingUtilities.getWindowAncestor(e.getComponent())
								.dispose();
					}
				});
				label.setFocusable(true);
				dialog.getContentPane().add(label);
				dialog.setVisible(true);
			}
		});
		btnLeft.setBounds(98, 317, 71, 25);
		getContentPane().add(btnLeft);

		JButton btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				dialog.setResizable(false);
				dialog.setSize(200, 100);
				JLabel label = new JLabel("              Please press a key.");
				dialog.setLocationRelativeTo(getContentPane());
				label.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {
						GameFrame.setKeyDOWN(e.getKeyCode());
						SwingUtilities.getWindowAncestor(e.getComponent())
								.dispose();
					}
				});
				label.setFocusable(true);
				dialog.getContentPane().add(label);
				dialog.setVisible(true);
			}
		});
		btnDown.setBounds(169, 317, 71, 25);
		getContentPane().add(btnDown);

		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				dialog.setResizable(false);
				dialog.setSize(200, 100);
				JLabel label = new JLabel("              Please press a key.");
				dialog.setLocationRelativeTo(getContentPane());
				label.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {
						GameFrame.setKeyRIGHT(e.getKeyCode());
						SwingUtilities.getWindowAncestor(e.getComponent())
								.dispose();
					}
				});
				label.setFocusable(true);
				dialog.getContentPane().add(label);
				dialog.setVisible(true);
			}
		});
		btnRight.setBounds(240, 317, 71, 25);
		getContentPane().add(btnRight);

		btnPause = new JButton("Pause");
		btnPause.setBounds(382, 317, 71, 25);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				dialog.setResizable(false);
				dialog.setSize(200, 100);
				JLabel label = new JLabel("              Please press a key.");
				dialog.setLocationRelativeTo(getContentPane());
				label.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {
						GameFrame.setKeyPAUSE(e.getKeyCode());
						SwingUtilities.getWindowAncestor(e.getComponent())
								.dispose();
					}
				});
				label.setFocusable(true);
				dialog.getContentPane().add(label);
				dialog.setVisible(true);
			}
		});
		getContentPane().add(btnPause);

		btnEagle = new JButton("Eagle");
		btnEagle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				dialog.setResizable(false);
				dialog.setSize(200, 100);
				JLabel label = new JLabel("              Please press a key.");
				dialog.setLocationRelativeTo(getContentPane());
				label.addKeyListener(new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {
						GameFrame.setKeyEAGLE(e.getKeyCode());
						SwingUtilities.getWindowAncestor(e.getComponent())
								.dispose();
					}
				});
				label.setFocusable(true);
				dialog.getContentPane().add(label);
				dialog.setVisible(true);
			}
		});
		btnEagle.setBounds(311, 317, 71, 25);
		getContentPane().add(btnEagle);

	}
}
