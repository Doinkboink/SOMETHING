package theThirdTry;

import java.awt.Graphics2D;

public abstract class SpecialEffect {
	double effectX, effectY;
	int effectTime;
	
	public SpecialEffect (double x, double y) {
		effectX = x;
		effectY = y;
	}
	
	public SpecialEffect (double x, double y, int t) {
		this(x, y);
		
		effectTime = t;
	}
	
	public boolean effectTick() {
		return (--effectTime <= 0);
	}
	
	public abstract void paint(Graphics2D g2d);
}
