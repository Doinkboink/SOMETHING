package theSecondTry;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Represents a bullet
 * @author s-chenrob
 * @author alexia
 *
 */
public class Bullet {
	/**
	 * Because Alex loves passing around references
	 */
	Game bulletGame;
	
	/**
	 * Is the bullet the player's
	 */
	boolean bulletFriendly;
	double bulletX;
	double bulletY;
	double bulletDirection;
	double bulletSpeed;
	/**
	 * Circle with radius of bulletHitbox is the hitbox
	 */
	double bulletHitbox;
	int bulletDamage;
	
	/**
	 * Ticks elapsed since bullet's creation
	 */
	int bulletCounter = 0;
	BufferedImage bulletImage;
	
	public Bullet(Game game, boolean bf, double bx, double by, double bd, double bs, double bh, BufferedImage bi) {
		this(game, bf, bx, by, bd, bs, bh, 1, bi);
	}
	
	public Bullet(Game game, boolean bf, double bx, double by, double bd, double bs, double bh, int pbd, BufferedImage bi) {
		bulletGame = game;
		bulletFriendly = bf;
		bulletX = bx;
		bulletY = by;
		bulletDirection = bd;
		bulletSpeed = bs;
		bulletHitbox = bh;
		bulletDamage = pbd;
		bulletImage = bi;
	}
	/**
	 * Perform a tick on the bullet
	 * 
	 * @return		Whether or not to delete this bullet
	 */
	public boolean bulletTick() {
		bulletCounter++;
		if (bulletUpdate())return true;
		
		if(!bulletFriendly) {
			//Enemy bullet detecting hits on player
			if (detectHitPlayer()) {
				//do stuff: currently non-existent
				bulletGame.thePlayer.getShot();
				return true;
			}
		}else {
			//Player bullet detecting hits on enemy
			for (int i = 0; i < bulletGame.enemyList.size(); i++) {
				if (detectHitEnemy(bulletGame.enemyList.get(i))) {
					bulletGame.enemyList.get(i).enemyHealth -= bulletDamage;
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Update the movement of the bullet.
	 * <p>
	 * All bullets extending Bullet should call super.bulletUpdate()
	 * 
	 * @return		True if the bullet is out of bounds, otherwise false
	 */
	boolean bulletUpdate() {//the boolean returned is whether it is out of bounds
		bulletX += bulletSpeed * Math.cos(Math.toRadians(bulletDirection));
		bulletY -= bulletSpeed * Math.sin(Math.toRadians(bulletDirection));
		return (bulletX < 0 
				|| bulletX > Game.PLAYAREAWIDTH 
				|| bulletY < 0 
				|| bulletY > Game.PLAYAREAHEIGHT);
	}
	/**
	 * Detect collisions with the player
	 * 
	 * @return		Whether hit the player
	 */
	private boolean detectHitPlayer() {
		return (Point2D.distance(bulletX, bulletY, bulletGame.thePlayer.getX(), bulletGame.thePlayer.getY()) < bulletHitbox);
	}
	/**
	 * Detect hits with the enemy
	 * 
	 * @param enemyCounter		Counter of the enemy
	 * @return					Whether hit the enemy
	 */
	private boolean detectHitEnemy(Enemy enemy) {
		return (Point2D.distance(bulletX, bulletY, enemy.enemyX, enemy.enemyY) < bulletHitbox);
	}
	/**
	 * Paints the bullet onto the screen
	 * 
	 * @param g		The graphics object to paint the bullet on
	 */
	public void paint(Graphics2D g) {
		g.translate(bulletX, bulletY);
		g.rotate(Math.toRadians(-bulletDirection));
		g.drawImage(bulletImage, 0 - bulletImage.getWidth()/2, 0 - bulletImage.getHeight()/2, null);
		g.setTransform(new AffineTransform());
	}
}
