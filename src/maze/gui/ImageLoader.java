package maze.gui;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The Class ImageLoader.
 * @author Vítor Teixeira and David Azevedo
 * @version 1.0
 */
public class ImageLoader {
	
	ImageIcon imgX = null;
	ImageIcon imgG = null;
	ImageIcon imgh = null;
	ImageIcon imgHW = null;
	ImageIcon imgHA = null;
	ImageIcon imgHS = null;
	ImageIcon imgHD = null;
	ImageIcon imgHWs = null;
	ImageIcon imgHSs = null;
	ImageIcon imgHAs = null;
	ImageIcon imgHDs = null;
	ImageIcon imgDW = null;
	ImageIcon imgDA = null;
	ImageIcon imgDS = null;
	ImageIcon imgDD = null;
	ImageIcon imgDsW = null; 
	ImageIcon imgDsA = null; 
	ImageIcon imgDsS = null; 
	ImageIcon imgDsD = null; 
	ImageIcon imgdW = null;
	ImageIcon imgdA = null;
	ImageIcon imgdS = null;
	ImageIcon imgdD = null;
	ImageIcon imgdsW = null;
	ImageIcon imgdsA = null;
	ImageIcon imgdsS = null;
	ImageIcon imgdsD = null;
	ImageIcon imgE = null;
	ImageIcon imgEs = null;
	ImageIcon imgS = null;
	ImageIcon imgEx = null;
	ImageIcon menu = null;
	ImageIcon inGameMenu = null;
	
	/**
	 * Instantiates a new image loader, where each line corresponds to a different image.
	 */
	public ImageLoader() {
		imgX = new ImageIcon(this.getClass().getResource("resources/MTree.png"));
		imgG = new ImageIcon(this.getClass().getResource("resources/Mground.png"));
		imgh = new ImageIcon(this.getClass().getResource("resources/heroDead.png"));
		imgHW = new ImageIcon(this.getClass().getResource("resources/heroW.png"));
		imgHA = new ImageIcon(this.getClass().getResource("resources/heroA.png"));
		imgHS = new ImageIcon(this.getClass().getResource("resources/heroS.png"));
		imgHD = new ImageIcon(this.getClass().getResource("resources/heroD.png"));
		imgHWs = new ImageIcon(this.getClass().getResource("resources/heroWS.png"));
		imgHAs = new ImageIcon(this.getClass().getResource("resources/heroAS.png"));
		imgHSs = new ImageIcon(this.getClass().getResource("resources/heroSS.png"));
		imgHDs = new ImageIcon(this.getClass().getResource("resources/heroDS.png"));
		imgDW = new ImageIcon(this.getClass().getResource("resources/Wdragon.png"));
		imgDA = new ImageIcon(this.getClass().getResource("resources/Adragon.png"));
		imgDS = new ImageIcon(this.getClass().getResource("resources/Sdragon.png"));
		imgDD = new ImageIcon(this.getClass().getResource("resources/Ddragon.png"));
		imgDsW = new ImageIcon(this.getClass().getResource("resources/Wdragon.png"));
		imgDsA = new ImageIcon(this.getClass().getResource("resources/Adragonsw.png"));
		imgDsS = new ImageIcon(this.getClass().getResource("resources/Sdragonsw.png"));
		imgDsD = new ImageIcon(this.getClass().getResource("resources/Ddragonsw.png"));
		imgdW = new ImageIcon(this.getClass().getResource("resources/WdragonS.png"));
		imgdA = new ImageIcon(this.getClass().getResource("resources/AdragonS.png"));
		imgdS = new ImageIcon(this.getClass().getResource("resources/SdragonS.png"));
		imgdD = new ImageIcon(this.getClass().getResource("resources/DdragonS.png"));
		imgdsW = new ImageIcon(this.getClass().getResource("resources/WdragonS.png"));
		imgdsA = new ImageIcon(this.getClass().getResource("resources/AdragonSsw.png"));
		imgdsS = new ImageIcon(this.getClass().getResource("resources/SdragonSsw.png"));
		imgdsD = new ImageIcon(this.getClass().getResource("resources/DdragonSsw.png"));
		imgE = new ImageIcon(this.getClass().getResource("resources/eagle.png"));
		imgEs = new ImageIcon(this.getClass().getResource("resources/eagleS.png"));
		imgS = new ImageIcon(this.getClass().getResource("resources/sword.png"));
		imgEx = new ImageIcon(this.getClass().getResource("resources/exit.png"));
		menu = new ImageIcon(this.getClass().getResource("resources/gameMenu.png"));
		inGameMenu = new ImageIcon(this.getClass().getResource("resources/inGameMenu.png"));
	}
	
	/**
	 * Gets the image.
	 *
	 * @param c is the character that's present on the maze
	 * @param dir the direction the hero or dragon is facing
	 * @return the proper image for that char and direction
	 */
	public Image getImage(char c, int dir) {
		
		Image img = null;
		
		switch (c) {
		case 'x':
			img = imgX.getImage();
			break;
		case ' ':
			img = imgG.getImage();
			break;
		case 'h':
			img = imgh.getImage();
			break;
		case 'H':
			if(dir == 1)
				img = imgHW.getImage();
			else if(dir == 2)
				img = imgHA.getImage();
			else if(dir == 3)
				img = imgHS.getImage();
			else if(dir == 4)
				img = imgHD.getImage();
			break;
		case 'A':
			if(dir == 1)
				img = imgHWs.getImage();
			else if(dir == 2)
				img = imgHAs.getImage();
			else if(dir == 3)
				img = imgHSs.getImage();
			else if(dir == 4)
				img = imgHDs.getImage();
			break;
		case 'D':
			if(dir == 1)
				img = imgDW.getImage();
			else if(dir == 2)
				img = imgDA.getImage();
			else if(dir == 3)
				img = imgDS.getImage();
			else if(dir == 4)
				img = imgDD.getImage();
			break;
		case 'd':
			if(dir == 1)
				img = imgdW.getImage();
			else if(dir == 2)
				img = imgdA.getImage();
			else if(dir == 3)
				img = imgdS.getImage();
			else if(dir == 4)
				img = imgdD.getImage();
			break;
		case 'F':
			if(dir == 1)
				img = imgDsW.getImage();
			else if(dir == 2)
				img = imgDsA.getImage();
			else if(dir == 3)
				img = imgDsS.getImage();
			else if(dir == 4)
				img = imgDsD.getImage();
			break;
		case 'f':
			if(dir == 1)
				img = imgdsW.getImage();
			else if(dir == 2)
				img = imgdsA.getImage();
			else if(dir == 3)
				img = imgdsS.getImage();
			else if(dir == 4)
				img = imgdsD.getImage();
			break;
		case 'E':
			img = imgS.getImage();
			break;
		case 'S':
			img = imgEx.getImage();
			break;
		case 'B':
			img = imgE.getImage();
			break;
		case 'M':
			img = menu.getImage();
			break;
		case 'm':
			img = inGameMenu.getImage();
			break;
		case 'c':
			img = imgEs.getImage();
			break;
		default:
			break;
		}
		return img;
	}
}
