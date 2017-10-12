package theThirdTry;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Representation of a homing bullet
 * 
 * @author s-chenrob
 */
public class Bullet_PHoming extends Bullet{

	public Bullet_PHoming(Game game, boolean bf, double bx, double by, double bd, double bs, double bh,
			BufferedImage bi) {
		super(game, bf, bx, by, bd, bs, bh, bi);
	}
	
	public Bullet_PHoming(Game game, boolean bf, double bx, double by, double bd, double bs, double bh, int pbd,
			BufferedImage bi) {
		super(game, bf, bx, by, bd, bs, bh, pbd, bi);
	}
	/**
	 * Update the bullet
	 * <p>
	 * Changes the bullet direction to point towards the nearest enemy
	 * 
	 * TODO Possible bottleneck
	 */
	@Override
	boolean bulletUpdate() {
		if (!bulletGame.enemyList.isEmpty()) {
			double minDist = 99999;
			int counter = -1;
			for (int i = 0; i < bulletGame.enemyList.size(); i++) {
				if (Point2D.distance(bulletX, bulletY, bulletGame.enemyList.get(i).enemyX, bulletGame.enemyList.get(i).enemyY) < minDist) {
					counter = i;
					minDist = Point2D.distance(bulletX, bulletY, bulletGame.enemyList.get(i).enemyX, bulletGame.enemyList.get(i).enemyY);
				}
			}
			double needdir = Math.toDegrees(Math.atan2(bulletY - bulletGame.enemyList.get(counter).enemyY, bulletGame.enemyList.get(counter).enemyX - bulletX));
			
			bulletDirection = (bulletDirection + 3600) % 360;
			needdir = (needdir + 3600) % 360;
			if (Math.abs(bulletDirection - needdir) < 30)bulletDirection = needdir;
			else if (180 > (bulletDirection - needdir + 360) % 360)bulletDirection -= 30;
			else bulletDirection += 30;
			if (Math.abs(bulletDirection - needdir) < 75)bulletSpeed = 30;
			else bulletSpeed = 10;
			
		}
		return super.bulletUpdate();
	}
}
