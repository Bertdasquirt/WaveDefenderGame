package animation;

import org.newdawn.slick.Image;

import core.DrawableImage;

/**
 * Represents a frame within an Animation
 * @author craig
 */
public class Frame {
	
	public float newx, newy, duration, startx, starty, xdiff, ydiff;
	public DrawableImage obj;
	private Image img;
	
	/**
	 * Setup a new frame object
	 * @param xdiff The x movement from it's origin
	 * @param ydiff The y movement from it's origin
	 * @param duration The duration of the frame
	 * @param img The image to show
	 * @param obj The Drawable object
	 */
	public Frame(float xdiff, float ydiff, float duration, Image img, DrawableImage obj) {
		this.duration = duration;
		this.obj = obj;
		this.img = img;
		
		this.startx = this.obj.getX();
		this.starty = this.obj.getY();
		
		this.newx = startx + xdiff;
		this.newy = starty + ydiff;
		
		this.xdiff = xdiff;
		this.ydiff = ydiff;
	}
	
	/**
	 * Called when this frame is visited/shown
	 */
	public void visit() {
		this.newx = this.obj.getX() + xdiff;
		this.newy = this.obj.getY() + ydiff;
		this.obj.img = img;
		this.obj.imagePath = img.getResourceReference();
		this.obj.setX(newx);
		this.obj.setY(newy);
	}
	
}
