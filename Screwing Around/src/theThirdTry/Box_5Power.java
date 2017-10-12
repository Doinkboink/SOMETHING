package theThirdTry;

import java.awt.geom.Point2D;
/**
 * A bigger classic "Power" box that gives 5 power
 * 
 * @author s-chenrob
 *
 */
public class Box_5Power extends Powerup {
	/**
	 * Pass arguments to super constructor
	 * 
	 * @param game		Game
	 * @param enemyX	X coord
	 * @param enemyY	Y coord
	 */
	public Box_5Power(Game game, double enemyX, double enemyY) {
		super(game, enemyX, enemyY);
		self = Preloads.PW5Box;
	}
	/**
	 * Gives the player a power boost if within a certain radius
	 */
	@Override
	public boolean boxTick() {
		if (Point2D.distance(boxX, boxY, boxGame.thePlayer.getX(), boxGame.thePlayer.getY()) < 32) {
			boxGame.thePlayer.incrementPower(5);
			return true;
		}
		return super.boxTick();
	}
}
