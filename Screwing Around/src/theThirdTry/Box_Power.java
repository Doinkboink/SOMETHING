package theThirdTry;

import java.awt.geom.Point2D;
/**
 * A classic "Power" box
 * 
 * @author s-chenrob
 *
 */
public class Box_Power extends Powerup {
	/**
	 * Pass arguments to super constructor
	 * 
	 * @param game		Game
	 * @param enemyX	X coord
	 * @param enemyY	Y coord
	 */
	public Box_Power(Game game, double enemyX, double enemyY) {
		super(game, enemyX, enemyY);
		self = Preloads.PWBox;
	}
	/**
	 * Gives the player a power boost if within a certain radius
	 */
	@Override
	public boolean boxTick() {
		if (Point2D.distance(boxX, boxY, boxGame.thePlayer.getX(), boxGame.thePlayer.getY()) < 32) {
			boxGame.thePlayer.incrementPower(1);
			return true;
		}
		return super.boxTick();
	}
}
