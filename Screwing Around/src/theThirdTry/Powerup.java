package theThirdTry;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A powerup
 * 
 * @author s-chenrob
 *
 */
public abstract class Powerup {
	double boxX;
	double boxY;
	/**
	 * Because Alex refuses to make boxGame a static variable
	 */
	Game boxGame;
	/**
	 * The image for itself
	 */
	BufferedImage self;
	int tempbox;
	/**
	 * Construct a box at the given coordinates in the game
	 * 
	 * @param game		The game to construct it in
	 * @param enemyX	X coordinate
	 * @param enemyY	Y coordinate
	 */
	public Powerup(Game game, double enemyX, double enemyY) {
		boxGame = game;
		boxX = enemyX;
		boxY = enemyY;
		tempbox = 0;
	}
	/**
	 * Update the box
	 * 
	 * @return		True if the box is outside play area, otherwise false
	 */
	public boolean boxTick() {
		if (tempbox < 50)boxY += (tempbox/5 - 5);
		else boxY += 4;
		tempbox++;
		return (boxY > Game.PLAYAREAHEIGHT);
	}
	
	public void paint(Graphics2D g2d) {
		g2d.drawImage(self, (int)boxX-self.getWidth()/2, (int)boxY-self.getHeight()/2, null);
	}
}
