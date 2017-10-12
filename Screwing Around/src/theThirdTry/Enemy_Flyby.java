package theThirdTry;

public class Enemy_Flyby extends Enemy{

	public Enemy_Flyby(Game g, double ex, double ey, double ed, double es, double eh) {
		super(g, ex, ey, ed, es, eh);
		enemyImage = Preloads.Flyby;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean enemyMove() {
		enemyX += enemySpeed * Math.cos(Math.toRadians(enemyDirection));
		enemyY -= enemySpeed * Math.sin(Math.toRadians(enemyDirection));
		
		return super.enemyMove();
	}
	
	@Override
	public boolean enemyAttack() {
		if (enemyCounter == 50) {
			enemyGame.bulletList.add(new Bullet(enemyGame, false, enemyX, enemyY, enemyGame.thePlayer.directionToPlayer(enemyX, enemyY), 5, 3, Preloads.Pellet));
			if (enemyGame.hard()) {
				enemyGame.bulletList.add(new Bullet(enemyGame, false, enemyX, enemyY, enemyGame.thePlayer.directionToPlayer(enemyX, enemyY) - 45, 5, 3, Preloads.Pellet));
				enemyGame.bulletList.add(new Bullet(enemyGame, false, enemyX, enemyY, enemyGame.thePlayer.directionToPlayer(enemyX, enemyY) + 45, 5, 3, Preloads.Pellet));
			}
			enemySpeed = 1;
		}
		if (enemyCounter == (enemyGame.hard() ? 150 : 250)) {
			enemySpeed = 3;
		}
		return false;
	}
	
	@Override
	public void onDeath() {
		enemyGame.boxList.add(new Box_Power(enemyGame, enemyX, enemyY));
		super.onDeath();
	}
}
