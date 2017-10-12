package theSecondTry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameField {
	/*
	 * Change the width / height to whatever you want
	 */
	private BufferedImage im = new BufferedImage(Game.PLAYAREAWIDTH, Game.PLAYAREAHEIGHT, BufferedImage.TYPE_INT_ARGB);
	private Graphics g = im.getGraphics();
	private Graphics2D g2d = (Graphics2D) g;
	
	private Player fieldPlayer;
	private Game fieldGame;
	
	public GameField(Player p, Game g) {
		fieldPlayer = p;
		fieldGame = g;
	}
	/*
	 * To use, do in Game.java, g.draw(gameFieldImage.getImage(), whereItStarts.x, whereItStarts.y);
	 */
	public BufferedImage getImage() {
		/*
		 * Perform all your drawings here, e.g. Player.drawStuff()
		 */
		g2d.setBackground(new Color(0, 0, 0, 0));
		g2d.clearRect(0, 0, im.getWidth(), im.getHeight());
		
		fieldPlayer.paint(g2d);
		for (int i = 0; i < fieldGame.bulletList.size(); i++) {
			fieldGame.bulletList.get(i).paint(g2d);
		}
		for (int i = 0; i < fieldGame.enemyList.size(); i++) {
			fieldGame.enemyList.get(i).paint(g2d);
		}
		return im;
	}
}
