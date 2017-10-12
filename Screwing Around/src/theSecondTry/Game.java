package theSecondTry;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * The game field
 * 
 * @author s-chenrob
 * @author alexia
 */
@SuppressWarnings("serial")
public class Game extends JPanel{
	/**
	 * Width of the panel
	 */
	public static final int PWIDTH = 1000;
	/**
	 * Height of the panel
	 */
	public static final int PHEIGHT = 1000;
	/**
	 * Width of the play area
	 */
	public static final int PLAYAREAWIDTH = 600;
	/**
	 * Height of the play area
	 */
	public static final int PLAYAREAHEIGHT = 750;
	/**
	 * Is the game paused
	 */
	private static boolean PAUSED = false;
	/**
	 * The list of all bullets
	 */
	public ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	/**
	 * The list of all enemies 
	 */
	public ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	
	public final Player thePlayer = new Player(this);
	private GameField theField = new GameField(thePlayer, this);
	
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				thePlayer.stopmove(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					PAUSED = !PAUSED;
				}
				thePlayer.startmove(e);
			}
		});
		setFocusable(true);
	}
	/**
	 * Perform the game tick 
	 * <p>
	 * Updates the bullets first and then the enemies. Note that this removes 
	 * any risk of a concurrency error from enemies creating bullets.
	 */
	public void tick() {
		thePlayer.turn();
		for (int i = 0; i < bulletList.size(); i++) {
			if (bulletList.get(i).bulletTick()) {
				bulletList.remove(i--);
			}
		}
		for (int i = 0; i < enemyList.size(); i++) {
			if (enemyList.get(i).enemyTick()) {
				enemyList.remove(i--);
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		//Clears the board
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawImage(theField.getImage(), 100, 100, null);//draws the main bulk, the game boundaries
		g2d.drawRect(100, 100, PLAYAREAWIDTH, PLAYAREAHEIGHT);//rectangle around it
		g2d.drawString(String.valueOf(bulletList.size()), 10, 30);//number of bullets onscreen
		g2d.drawString(String.valueOf(thePlayer.shootTemp), 10, 60);//cooldown
	}
	
	public static void main(String[] args) throws InterruptedException {
		JFrame theframe = new JFrame("Arbitrary Title");
		Game thegame = new Game();
		theframe.add(thegame);
		theframe.setSize(PWIDTH, PHEIGHT);
		theframe.setResizable(false);
		theframe.setVisible(true);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thegame.enemyList.add(new Enemy_Test(thegame, 300, 300, 0, 1, 10));

		thegame.enemyList.add(new Enemy_Test(thegame, 300, 334, 0, 1, 10));

		thegame.enemyList.add(new Enemy_Test(thegame, 300, 120, 0, 1, 10));

		thegame.enemyList.add(new Enemy_Test(thegame, 300, 540, 0, 1, 10));

		thegame.enemyList.add(new Enemy_Test(thegame, 300, 550, 0, 1, 10));
		
		while(true) {
			long ml = System.currentTimeMillis();
			if (!PAUSED) {
				thegame.tick();
				thegame.repaint();
			}
			Thread.sleep(Math.max(0, 20 + ml - System.currentTimeMillis()));
		}
	}

}