package theThirdTry;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * The abstract version of an enemy
 * 
 * Superclasses all enemies
 * @author s-chenrob
 *
 */
public abstract class Enemy {
	/**
	 * Because Alex loves passing around references
	 */
	Game enemyGame;
	double enemyX;
	double enemyY;
	double enemyDirection;
	double enemySpeed;
	double enemyHealth;
	/**
	 * The current tick for the enemy
	 */
	double enemyCounter = 0;
	
	BufferedImage enemyImage = null;
	public Enemy(Game g, double ex, double ey, double ed, double es, double eh) {
		enemyGame = g;
		enemyX = ex;
		enemyY = ey;
		enemyDirection = ed;
		enemySpeed = es;
		enemyHealth = eh;
	}

	/**
	 * Complete tick of an enemy
	 * 
	 * @return		Whether or not to delete this enemy
	 */
	public boolean enemyTick() {
		enemyCounter++;
		if (enemyHealth < 0) {
			onDeath();
			return true;
		} else {
			if (enemyMove() || enemyAttack())return true;
		}
		return false;
	}
	
	public boolean enemyMove() {//override this stuff for individual enemies
		return (enemyX < 0 
				|| enemyX > Game.PLAYAREAWIDTH 
				|| enemyY < 0 
				|| enemyY > Game.PLAYAREAHEIGHT);
	}
	
	public boolean enemyAttack() {//override this stuff for individual enemies, returns whether to self-destruct
		return false;
	}

	AffineTransform trans = new AffineTransform();
	public void paint(Graphics2D g) {
		g.translate(enemyX, enemyY);
		g.rotate(Math.toRadians(-enemyDirection));
		g.drawImage(enemyImage, 0 - enemyImage.getWidth()/2, 0 - enemyImage.getHeight()/2, null);
		g.setTransform(trans);
	}
	
	public void onDeath() {
		enemyGame.effectList.add(new Explosion(enemyX, enemyY));
		//override this
	}
}
