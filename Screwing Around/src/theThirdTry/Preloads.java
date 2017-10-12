package theThirdTry;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Preloads all images before hand 
 * 
 * @author s-chenrob
 *
 */
public class Preloads {
	static {
		BufferedImage empty = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		
		BufferedImage PWx = empty, PW5x = empty, SPx = empty, HPx = empty, GODx = empty;
		/**
		 * Enemies
		 */
		BufferedImage EFBx = empty;
		/**
		 * Bullets
		 */
		BufferedImage BPLx = empty;
		/**
		 * Explosions
		 */
		BufferedImage eX0x = empty, eX1x = empty, eX2x = empty, eX3x = empty, eX4x = empty;
		
		try {
			PWx = ImageIO.read(new File("PWBox.png"));
			PW5x = ImageIO.read(new File("5PWBox.png"));
			SPx = ImageIO.read(new File("SPBox.png"));
			HPx = ImageIO.read(new File("HPBox.png"));
			GODx = ImageIO.read(new File("GODBox.png"));
			
			EFBx = ImageIO.read(new File("Flyby.png"));
			
			BPLx = ImageIO.read(new File("Pellet.png"));
			
			eX0x = ImageIO.read(new File("explosion-0.png"));
			eX1x = ImageIO.read(new File("explosion-1.png"));
			eX2x = ImageIO.read(new File("explosion-2.png"));
			eX3x = ImageIO.read(new File("explosion-3.png"));
			eX4x = ImageIO.read(new File("explosion-4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PWBox = PWx;
		PW5Box = PW5x;
		SPBox = SPx;
		HPBox = HPx;
		GODBox = GODx;
		
		Flyby = EFBx;
		
		Pellet = BPLx;
		
		EX0 = eX0x;
		EX1 = eX1x;
		EX2 = eX2x;
		EX3 = eX3x;
		EX4 = eX4x;
	}
	/**
	 * The image for the power box
	 */
	public static final BufferedImage PWBox, PW5Box, SPBox, HPBox, GODBox;
	public static final BufferedImage Flyby;
	public static final BufferedImage Pellet;
	public static final BufferedImage EX0, EX1, EX2, EX3, EX4;

	/**
	 * Ensures that nobody can construct this
	 */
	private Preloads() {}
}
