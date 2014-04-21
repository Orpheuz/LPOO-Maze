package maze.gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * The Class MenuPanel.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class MenuPanel extends JPanel {


	
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new menu panel.
	 */
	public MenuPanel() {

	}
	
	/**
	 *  
	 * @param g the graphics
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		ImageLoader i1 = new ImageLoader();
		Image img;
		img = i1.getImage('M', 4);
		g.drawImage(img, 0, 0, this);
	}

}
