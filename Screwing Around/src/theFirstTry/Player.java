package theFirstTry;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Player {
	private static final int MAXSPEED = 6, FOCUSSPEED = 2;
	
	private Game tempgame;
	private int x = Game.PWIDTH / 2;
	private int y = Game.PHEIGHT / 2;
	private int xx = 0;
	private int yy = 0;
	public int HPLvl = 0, PWLvl = 0, SPLvl = 0;
	private boolean lmove = false, rmove = false, umove = false, dmove = false, focus = false;
	BufferedImage pimg = null, pbody = null, pguns = null, pwing = null;
	
	public Player(Game game) {
		this.tempgame = game;
		try {
			pimg = ImageIO.read(new File("playerchar.png"));//get images from piskelapp.com
			pwing = ImageIO.read(new File("player-wing-" + SPLvl + ".png"));
			pguns = ImageIO.read(new File("player-guns-" + PWLvl + ".png"));
			pbody = ImageIO.read(new File("player-body-" + HPLvl + ".png"));
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
	
	public void paint(Graphics2D g) {
		//g.drawImage(pimg, x, y, null);
		g.drawImage(pwing, x - 16, y - 16, null);
		g.drawImage(pguns, x - 16, y - 16, null);
		g.drawImage(pbody, x - 16, y - 16, null);
	}
	
	public void maybemove(KeyEvent e) {//detects if a key has started being pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			lmove = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rmove = true;
		if (e.getKeyCode() == KeyEvent.VK_UP)
			umove = true;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			dmove = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			focus = true;
			
	}
	
	public void stahp(KeyEvent e) {//detects if key has stopped being pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//xx = Math.max(0, xx);
			lmove = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//xx = Math.min(0, xx);
			rmove = false;
		}	
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			//yy = Math.max(0, yy);
			umove = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			//yy = Math.min(0, yy);
			dmove = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			focus = false;
	}
	
	public void refreshPlayer () {
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
	}
	
	public void move () {
		refreshPlayer();
		x += xx;
		y += yy;
		x = Math.max(16, x);
		x = Math.min(tempgame.getWidth() - 16, x);
	}
}
