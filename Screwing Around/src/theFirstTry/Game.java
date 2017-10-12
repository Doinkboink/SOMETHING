package theFirstTry;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Game extends JPanel{
	public static final int PWIDTH = 500;
	public static final int PHEIGHT = 400;
	public static boolean PAUSED = false;
	
	Player kid = new Player(this);
	
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				kid.stahp(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					PAUSED = !PAUSED;
				}
				kid.maybemove(e);
			}
		});
		setFocusable(true);
	}
	
	public void updatestuff() {
		kid.move();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);//clears the board
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		kid.paint(g2d);
		g2d.drawString(String.valueOf(this.getWidth()), 10, 30);
		g2d.drawString(String.valueOf(this.getHeight()), 10, 60);
	}
	
	public static void main(String[] args) throws InterruptedException {
		JFrame theframe = new JFrame("Arbitrary Title");
		Game thegame = new Game();
		theframe.add(thegame);
		theframe.setSize(PWIDTH, PHEIGHT);
		theframe.setResizable(false);
		theframe.setVisible(true);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while(true) {
			if (!PAUSED) {
				thegame.updatestuff();
				thegame.repaint();
			}
			Thread.sleep(20);
		}
	}

}
