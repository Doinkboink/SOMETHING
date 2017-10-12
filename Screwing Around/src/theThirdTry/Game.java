package theThirdTry;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	/**
	 * The list of all boxes
	 */
	public ArrayList<Powerup> boxList = new ArrayList<Powerup>();
	/**
	 * The list of all special effects
	 */
	public ArrayList<SpecialEffect> effectList = new ArrayList<SpecialEffect>();
	
	/**
	 * The current state of the game
	 */
	private String GAMEMODE = "BattleMenu";
	private int transTick;
	public void returnToMenu() {
		GAMEMODE = "Transition";
		transTick = 0;
	}
	private int menuCounter;
	
	/**
	 * the player's upgrade points
	 */
	private int UPGRADEPOINTS = 0;
	/**
	 * The level that the player is on
	 */
	private int LEVEL = 1;
	/**
	 * The current time elapsed since level start
	 */
	private int GAMETICK = 0;
	/**
	 * Whether the player is being a badass
	 */
	private boolean HARDMODE = true;
	/**
	 * Because Robert is gonna get pissed at me otherwise
	 * @return the level the player is on to other classes
	 */
	public int getLevel() {return LEVEL;}
	/**
	 * Because Robert is a prick
	 * @return the tick of the game
	 */
	public int getGameTick() {return GAMETICK;}
	/**
	 * Returns whether this is easy or not
	 * @return true if on easymode, false if otherwise
	 */
	public boolean hard() {return HARDMODE;}
	
	public final Player thePlayer = new Player(this);
	private final Environment theEnvironment = new Environment(this);
	private GameField theField = new GameField(thePlayer, this);
	
	/**
	 * The image that is in the background during battle
	 */
	private BufferedImage battleBackground;
	
	
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if (GAMEMODE == "Battle") {
					thePlayer.stopmove(e);
				}
				else if (GAMEMODE == "BattleMenu") {
					shiftBMr(e);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (GAMEMODE == "Battle") {
					thePlayer.startmove(e);
					if (e.getKeyCode() == KeyEvent.VK_P) {
						PAUSED = !PAUSED;
					}
				}
				else if (GAMEMODE == "BattleMenu") {
					shiftBMd(e);
				}
			}
		});
		setFocusable(true);
		
		try {
			battleBackground = ImageIO.read(new File("spacetest.jpg"));
		} catch (IOException e1) {
		}
	}
	/**
	 * Perform the game tick in given order:
	 * 
	 * Player updates (Important)
	 * Environment updates (Important)
	 * Bullets update (Important)
	 * Enemies update (Priority)
	 * Boxes update (Non-priority)
	 * Special effects update (Unimportant)
	 * <p>
	 * Updates the bullets first and then the enemies. Note that this removes 
	 * any risk of a concurrency error from enemies creating bullets.
	 */
	public void tick() {
		GAMETICK++;
		thePlayer.turn();
		theEnvironment.tick(LEVEL, GAMETICK);
		for (int i = 0; i < bulletList.size(); i++) {
			if (thePlayer.invulnerable()) {
				effectList.add(new Particles(bulletList.get(i).bulletX, bulletList.get(i).bulletY, 5, 1));
				bulletList.remove(i--);
			}
			else if (bulletList.get(i).bulletTick()) {
				bulletList.remove(i--);
			}
		}
		for (int i = 0; i < enemyList.size(); i++) {
			if (enemyList.get(i).enemyTick()) {
				enemyList.remove(i--);
			}
		}
		for (int i = 0; i < boxList.size(); i++) {
			if (boxList.get(i).boxTick()) {
				boxList.remove(i--);
			}
		}
		for (int i = 0; i < effectList.size(); i++) {
			if (effectList.get(i).effectTick()) {
				effectList.remove(i--);
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
		/**
		 * This does stuff
		 */
		switch (GAMEMODE) {
		case "BattleMenu":
			paintBattleMenu(g2d);
			break;
		case "Battle":
			paintBattle(g2d);
			break;
		case "Transition":
			paintTransition(g2d);
			break;
		default:
			System.out.println("You done goofed your drawing");
		}
	}
	
	Color pureBlack = new Color(0, 0, 0);
	Color transGray = new Color(128, 128, 128, 192);
	Color transBlue = new Color(0, 0, 255, 192);
	Color transRed = new Color(255, 0, 0, 192);
	public void paintBattle(Graphics2D g2d) {
		if (GAMETICK < -50) {
			paintBattleMenu(g2d);
			g2d.setColor(pureBlack);
			g2d.fillRect(Math.min(0, 1000 + GAMETICK * 10), 0, 500, 1000);
			g2d.fillRect(Math.max(500, -500 - GAMETICK * 10), 0, 500, 1000);
			return;
		}
		g2d.drawImage(battleBackground, 0, 0, null);
		g2d.drawImage(theField.getImage(), 100, 100, null);//draws the main bulk, the game boundaries
		
		g2d.setColor(pureBlack);
		g2d.drawRect(100, 100, PLAYAREAWIDTH, PLAYAREAHEIGHT);//rectangle around it
		g2d.setColor(transGray);
		g2d.fillRect(750, 150, 50, 620);
		g2d.setColor(transBlue);
		g2d.fillRect(760, 760 - 10 * thePlayer.getPW(), 30, 10 * thePlayer.getPW());
		g2d.setColor(transRed);
		for (int k = 0; k < thePlayer.getHP(); k++) {//to be replaced with better graphics
			g2d.fillOval(820, 710 - 75 * k, 60, 60);
		}
		if (GAMETICK < 0) {
			g2d.setColor(pureBlack);
			//TODO: Make this more smooth, choppy rn
			g2d.fillRect(-500 - GAMETICK * 10, 0, 500, 1000);
			g2d.fillRect(1000 + GAMETICK * 10, 0, 500, 1000);
		}
		//g2d.drawString(String.valueOf(thePlayer.curHP), 10, 30);
	}
	
	public void paintBattleMenu(Graphics2D g2d) {//to be done more stuff with later
		g2d.drawImage(battleBackground, 0, 0, null);
		thePlayer.paintAtMenu(g2d);
	}
	
	Font bigFont = new Font("Courier", Font.PLAIN, 100);
	public void paintTransition(Graphics2D g2d) {
		if (transTick <= 400)paintBattle(g2d);
		else paintBattleMenu(g2d);
		g2d.setFont(bigFont);
		g2d.setColor(new Color(0, 0, 0, Math.min(5 * transTick, 255)));
		if (transTick < 400)g2d.drawString("GAME OVER", 125, 500);
		if (transTick >= 200 && transTick < 400) {
			g2d.setColor(new Color(255, 255, 255, Math.min(2 * (transTick - 200), 255)));
			g2d.fillRect(0, 0, 1000, 1000);
		}
		else if (transTick >= 400 && transTick <= 450) {
			g2d.setColor(new Color(255, 255, 255, Math.max(255 - 5 * (transTick - 400), 0)));
			g2d.fillRect(0, 0, 1000, 1000);
		}
		if (transTick > 450) {
			menuCounter = 0;
			GAMEMODE = "BattleMenu";
		}
	}
	
	private boolean BMu, BMd, BMs;
	private void shiftBMd(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) BMu = true;
		if (e.getKeyCode() == KeyEvent.VK_DOWN) BMd = true;
		if (e.getKeyCode() == KeyEvent.VK_Z) BMs = true;
	}
	private void shiftBMr(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (BMu) menuCounter = (menuCounter + 4)%5;
			BMu = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (BMd) menuCounter = (menuCounter + 1)%5;
			BMd = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			if (BMs) {
				switch (menuCounter) {
				case 0:
					thePlayer.prepare();
					bulletList.clear();
					enemyList.clear();
					boxList.clear();
					effectList.clear();
					GAMETICK = -150;
					GAMEMODE = "Battle";
					break;
				case 4:
					GAMEMODE = "MainMenu";
					break;
				
				default:
					if (UPGRADEPOINTS > 0) {
						if (thePlayer.upgrade(menuCounter)) {
							UPGRADEPOINTS--;
						}
					}
					break;
				}
			}
			BMs = false;
		}
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
			if (thegame.GAMEMODE.equals("Battle")) {
				if (!PAUSED) {
					thegame.tick();
				}
			}else if(thegame.GAMEMODE.equals("Transition")) {
				thegame.transTick++;
				if (thegame.transTick % 5 == 0 && thegame.transTick <= 200)thegame.tick();
			}
			thegame.repaint();
			Thread.sleep(Math.max(0, 20 + ml - System.currentTimeMillis()));
		}
	}

}