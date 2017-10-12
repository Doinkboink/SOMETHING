package theThirdTry;

public class Environment {
	Game environmentGame;
	public Environment(Game game) {
		environmentGame = game;
	}
	
	public void tick(int lvl, int tck) {
		switch(lvl) {
		case 1:
			lvl1Tick(tck);
			break;
		default:
			System.out.println("You done goofed environment");
		}
	}
	/**
	 * Generate stuff based on Level 1
	 * 
	 * @param tck 	the tick that the game is on
	 */
	private void lvl1Tick(int tck) {
		if (tck % 20 == 0 && tck > 0)environmentGame.enemyList.add(new Enemy_Flyby(environmentGame, 0, 0, 315, 3, 5));
	}
}
