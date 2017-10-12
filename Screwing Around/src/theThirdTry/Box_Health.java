package theThirdTry;

import java.awt.geom.Point2D;
/**
 * A classic "1-UP" box
 * 
 * @author s-chenrob
 *
 */
public class Box_Health extends Powerup {
	/**
	 * Pass arguments to super constructor
	 * 
	 * @param game		Game
	 * @param enemyX	X coord
	 * @param enemyY	Y coord
	 */
	public Box_Health(Game game, double enemyX, double enemyY) {
		super(game, enemyX, enemyY);
		self = Preloads.HPBox;
	}
	/**
	 * Gives the player a power boost if within a certain radius
	 */
	@Override
	public boolean boxTick() {
		if (Point2D.distance(boxX, boxY, boxGame.thePlayer.getX(), boxGame.thePlayer.getY()) < 32) {
			boxGame.thePlayer.incrementHealth(1);
			return true;
		}
		return super.boxTick();
	}
}
