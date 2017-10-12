package theThirdTry;

import java.awt.geom.Point2D;
/**
 * A bigger classic "Power" box that gives 5 power
 * 
 * @author s-chenrob
 *
 */
public class Box_God extends Powerup {
	/**
	 * Pass arguments to super constructor
	 * 
	 * @param game		Game
	 * @param enemyX	X coord
	 * @param enemyY	Y coord
	 */
	public Box_God(Game game, double enemyX, double enemyY) {
		super(game, enemyX, enemyY);
		self = Preloads.GODBox;
	}
	/**
	 * Gives the player a power boost if within a certain radius
	 */
	@Override
	public boolean boxTick() {
		if (Point2D.distance(boxX, boxY, boxGame.thePlayer.getX(), boxGame.thePlayer.getY()) < 32) {
			boxGame.thePlayer.incrementPower(60);
			boxGame.thePlayer.incrementHealth(10);
			boxGame.thePlayer.incrementSpecial(10);
			return true;
		}
		return super.boxTick();
	}
}
