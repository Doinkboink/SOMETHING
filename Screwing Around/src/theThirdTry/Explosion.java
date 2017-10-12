package theThirdTry;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Explosion extends SpecialEffect{

	public Explosion(double x, double y) {
		super(x, y);
		effectTime = 20;
		// TODO Auto-generated constructor stub
	}
	
	private BufferedImage explosionFrame = Preloads.EX0;
	
	@Override
	public void paint(Graphics2D g2d) {
		if (effectTime == 16)explosionFrame = Preloads.EX1;
		else if (effectTime == 12)explosionFrame = Preloads.EX2;
		else if (effectTime == 8)explosionFrame = Preloads.EX3;
		else if (effectTime == 4)explosionFrame = Preloads.EX4;
		
		g2d.drawImage(explosionFrame, (int)effectX - 24, (int)effectY - 24, null);
	}
}
