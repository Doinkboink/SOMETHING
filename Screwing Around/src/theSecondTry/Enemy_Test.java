package theSecondTry;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * A test enemy
 * 
 * @author s-chenrob
 */
public class Enemy_Test extends Enemy {
	public Enemy_Test(Game g, double ex, double ey, double ed, double es, double eh) {
		super(g, ex, ey, ed, es, eh);
		try {
			enemyImage = ImageIO.read(new File ("playerchar.png"));
		} catch (IOException e) {
		}
	}
	/**
	 * Movement tick of an enemy
	 * 
	 * @return		Whether or not the enemy is out of bounds
	 */
	@Override
	public boolean enemyMove() {
		enemyX += enemySpeed * Math.cos(Math.toRadians(enemyDirection));
		enemyY -= enemySpeed * Math.sin(Math.toRadians(enemyDirection));
		
		return super.enemyMove();
	}
	/**
	 * Controls enemey's shooting ticks
	 * 
	 * @return		Whether or not to remove enemy
	 */
	@Override
	public boolean enemyAttack() {//override this stuff for individual enemies, returns whether to self-destruct
		if (enemyCounter % 50 == 9) {
			try {
				//TODO : CHANGE THIS 
				enemyGame.bulletList.add(new Bullet(enemyGame, false, enemyX, enemyY, 270, 10, 8, ImageIO.read(new File("RedSmallBullet.png"))));
			} catch (IOException e) {
			}
		}
		return false;
	}
}
