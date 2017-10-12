package theThirdTry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains a series of particles that are emitted
 * from a center point.
 * 
 * @author s-chenrob
 *
 */
public class Particles extends SpecialEffect{
	final Color myColor = new Color(128, 0, 0);
	int particleCount = 0;
	ArrayList<Particle> myparts = new ArrayList<Particle>();
	public Particles(double x, double y, int t, int c) {
		super(x, y, t);

		Random r = new Random();
		particleCount = c;
		for (int i = 0; i < particleCount; i++) {
			//these are skewed so it matches up
			int tX = r.nextInt(11) - 7;
			int tY = r.nextInt(11) - 7;
			
			myparts.add(new Particle(effectX + tX, effectY + tY, r.nextInt(360)));
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(myColor);
		for (int i = 0; i < particleCount; i++) {
			//these are skewed so it matches up
			myparts.get(i).PX += Math.cos(Math.toRadians(myparts.get(i).direction));
			myparts.get(i).PY -= Math.sin(Math.toRadians(myparts.get(i).direction));
			g2d.drawOval((int)(myparts.get(i).PX), (int)(myparts.get(i).PY), 4, 4);
		}
	}
	/**
	 * What is "Point-2D"?
	 * @author s-chenrob
	 *
	 */
	private class Particle{
		double PX;
		double PY;
		int direction;
		Particle(double px, double py, int pd){
			PX = px;
			PY = py;
			direction = pd;
		}
	}
}
