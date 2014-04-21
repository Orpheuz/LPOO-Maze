package maze.gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * The Class InGameMenu.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class InGameMenu extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new in game menu.
	 */
	public InGameMenu() {
		setLayout(null);
	}
	
	/**
	 *  
	 *
	 * @param g the graphics
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		ImageLoader i1 = new ImageLoader();
		Image img;
		img = i1.getImage('m', 4);
		g.drawImage(img, 0, 0, this);
	}

}