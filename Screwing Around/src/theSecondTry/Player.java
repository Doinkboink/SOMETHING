package theSecondTry;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Player {
	private static final int MAXSPEED = 6, MAXDIAG = 4, FOCUSSPEED = 2, FOCUSDIAG = 1;
	
	private Game playerGame;
	private int HPLvl = 0, PWLvl = 0, SPLvl = 0;
	
	BufferedImage pimg, pbody, pguns, pwing;
	/**
	 * Normal homing, Normal small, Normal large
	 */
	BufferedImage playerBulletNH, playerBulletNS, playerBulletNL;
	/**
	 * Focus homing, Focus small, Focus large
	 */
	BufferedImage playerBulletFH, playerBulletFS, playerBulletFL;
	
	private int x = Game.PWIDTH / 2;
	private int y = Game.PHEIGHT / 2;
	private int xx = 0;
	private int yy = 0;
	private boolean lmove = false, rmove = false, umove = false, dmove = false, focus = false, pshot = false;
	public int shootTemp = 0;//to be replaced with game ticks
	private int curPW = 25;
	
	public Player(Game game) {//constructor
		this.playerGame = game;
		try {
			pimg = ImageIO.read(new File("playerchar.png"));//get images from piskelapp.com
			pwing = ImageIO.read(new File("player-wing-" + SPLvl + ".png"));
			pguns = ImageIO.read(new File("player-guns-" + PWLvl + ".png"));
			pbody = ImageIO.read(new File("player-body-" + HPLvl + ".png"));
			playerBulletNH = ImageIO.read(new File("BlueSmallBullet.png"));
			playerBulletNS = ImageIO.read(new File("BlueBigBullet.png"));
			playerBulletNL = ImageIO.read(new File("BlueHugeBullet.png"));
			playerBulletFH = ImageIO.read(new File("RedSmallBullet.png"));
			playerBulletFS = ImageIO.read(new File("RedBigBullet.png"));
			playerBulletFL = ImageIO.read(new File("RedHugeBullet.png"));
		}
		catch (IOException e) {
			System.err.println("Image not loaded");
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void paint(Graphics2D g) {//render the player using the preloaded images
		//g.drawImage(pimg, x, y, null);
		g.drawImage(pwing, x - 16, y - 16, null);
		g.drawImage(pguns, x - 16, y - 16, null);
		g.drawImage(pbody, x - 16, y - 16, null);
	}
	
	public void startmove(KeyEvent e) {//detects if a key has started being pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			lmove = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rmove = true;
		if (e.getKeyCode() == KeyEvent.VK_UP)
			umove = true;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			dmove = true;
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			focus = true;
		if (e.getKeyCode() == KeyEvent.VK_Z)	
			pshot = true;
	}
	
	public void stopmove(KeyEvent e) {//detects if key has stopped being pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT) 
			lmove = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
			rmove = false;
		if (e.getKeyCode() == KeyEvent.VK_UP) 
			umove = false;		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) 
			dmove = false;	
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			focus = false;
		if (e.getKeyCode() == KeyEvent.VK_Z)
			pshot = false;
	}
	
	void refreshPlayer1 () {//each tick, updates the player's inertia and uses it to change stuff
		xx += (lmove ? -1 : 0) + (rmove ? 1 : 0);
		yy += (umove ? -1 : 0) + (dmove ? 1 : 0);
		if (!lmove && xx < 0)xx = Math.min(xx + 2, 0);
		if (!rmove && xx > 0)xx = Math.max(0, xx - 2);
		if (!umove && yy < 0)yy = Math.min(yy + 2, 0);
		if (!dmove && yy > 0)yy = Math.max(0, yy - 2);
		if (focus) 
			xx = Math.min(FOCUSSPEED, Math.max(-1 * FOCUSSPEED, xx));
		else 
			xx = Math.min(MAXSPEED, Math.max(-1 * MAXSPEED, xx));
		if (focus) 
			yy = Math.min(FOCUSSPEED, Math.max(-1 * FOCUSSPEED, yy));
		else 
			yy = Math.min(MAXSPEED, Math.max(-1 * MAXSPEED, yy));
		x += xx;
		y += yy;
	}
	
	void refreshPlayer2() {//uses a more simple stop and go method
		xx = ((umove ^ dmove)?(focus ? FOCUSDIAG : MAXDIAG) : (focus ? FOCUSSPEED : MAXSPEED));
		x += ((lmove ^ rmove) ? 1 : 0) * (lmove ? -1 : 1) * xx;
		yy = ((lmove ^ rmove)?(focus ? FOCUSDIAG : MAXDIAG) : (focus ? FOCUSSPEED : MAXSPEED));
		y += ((umove ^ dmove) ? 1 : 0) * (umove ? -1 : 1) * yy;
	}
	
	void move() {//each tick, updates the player's position
		//refreshPlayer1();
		refreshPlayer2();
		x = Math.max(0 + 16, x);
		x = Math.min(Game.PLAYAREAWIDTH - 16, x);
		y = Math.max(0 + 16, y);
		y = Math.min(Game.PLAYAREAHEIGHT - 16, y);
	}
	
	void shoot() {
		if (shootTemp > 0)shootTemp--;
		if (shootTemp == 0 && pshot) {
			shootTemp = 10 - PWLvl;
			if (focus) {
				if (curPW < 5) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 3, playerBulletFL));
				}
				else if (curPW < 10) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x - 5, y, 90, 30, 24, 2, playerBulletFS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x + 5, y, 90, 30, 24, 2, playerBulletFS));
				}
				else if (curPW < 24) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x - 5, y, 90, 30, 24, 2, playerBulletFS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 2, playerBulletFS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x + 5, y, 90, 30, 24, 2, playerBulletFS));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 90, 30, 24, 1, playerBulletFH));
				}
				else if (curPW < 60) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x - 5, y, 90, 30, 24, 2, playerBulletFS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 2, playerBulletFS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x + 5, y, 90, 30, 24, 2, playerBulletFS));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 90, 30, 24, 1, playerBulletFH));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 60, 30, 24, 1, playerBulletFH));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 120, 30, 24, 1, playerBulletFH));
				}
				else {
					playerGame.bulletList.add(new Bullet(playerGame, true, x - 5, y, 90, 30, 24, 3, playerBulletFL));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 3, playerBulletFL));
					playerGame.bulletList.add(new Bullet(playerGame, true, x + 5, y, 90, 30, 24, 3, playerBulletFL));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 90, 30, 24, 1, playerBulletFH));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 60, 30, 24, 1, playerBulletFH));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 120, 30, 24, 1, playerBulletFH));
				}
			}
			else {
				if (curPW < 5) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 3, playerBulletNL));
				}
				else if (curPW < 10) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 75, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 105, 30, 24, 2, playerBulletNS));
				}
				else if (curPW < 24) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 75, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 3, playerBulletNL));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 105, 30, 24, 2, playerBulletNS));
				}
				else if (curPW < 60) {
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 75, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 3, playerBulletNL));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 105, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 45, 30, 24, 1, playerBulletNH));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 135, 30, 24, 1, playerBulletNH));
				}
				else {
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 60, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 75, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 90, 30, 24, 3, playerBulletNL));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 105, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet(playerGame, true, x, y, 120, 30, 24, 2, playerBulletNS));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 45, 30, 24, 1, playerBulletNH));
					playerGame.bulletList.add(new Bullet_PHoming(playerGame, true, x, y, 135, 30, 24, 1, playerBulletNH));
				}
			}
		}
	}
	
	public void getShot() {
		return;//what happens when the player is shot
	}
	
	public void turn() {
		move();
		shoot();
	}
}

