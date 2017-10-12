package theThirdTry;

import java.awt.geom.Point2D;
/**
 * A classic "BOMB+" box
 * 
 * @author s-chenrob
 *
 */
public class Box_Special extends Powerup {
	/**
	 * Pass arguments to super constructor
	 * 
	 * @param game		Game
	 * @param enemyX	X coord
	 * @param enemyY	Y coord
	 */
	public Box_Special(Game game, double enemyX, double enemyY) {
		super(game, enemyX, enemyY);
		self = Preloads.SPBox;
	}
	/**
	 * Gives the player a power boost if within a certain radius
	 */
	@Override
	public boolean boxTick() {
		if (Point2D.distance(boxX, boxY, boxGame.thePlayer.getX(), boxGame.thePlayer.getY()) < 32) {
			boxGame.thePlayer.incrementSpecial(1);
			return true;
		}
		return super.boxTick();
	}
}
