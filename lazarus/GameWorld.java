/* GameWorld creates a new game world */
package lazarus;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import javax.swing.JPanel;

import lazarus.main.BackgroundObject;

// GameWorld object creates a new game world
public abstract class GameWorld extends JPanel implements Runnable, Observer {

    // adds a new block Observer
	public abstract void addClockObserver(Observer obs);

    // creates a new set of sprites
	public static HashMap<String,Image> sprites = new HashMap<String,Image>();
	
    // speed at origin
	private static Point speed = new Point(0,0);
	
    // creates a new instance of GameSounds or GameClock
    public static final GameSounds sound = new GameSounds();
    public static final GameClock clock = new GameClock();
    
    // creates a buffered image
    protected BufferedImage bufferedimage;

    // creates a new background
    protected ArrayList<BackgroundObject> background;
    
    // in charge of loading sprites
    abstract protected void loadSprites();
    
    // changes speed to set amount
    public static void setSpeed(Point speed){
    	GameWorld.speed = speed;
    }
    
    // gets the current speed of GameWorld
    public static Point getSpeed(){
    	return new Point(GameWorld.speed);
    }
    
    // creates the world by taking the values of buffered image, background, width/height to create a rectangle map
    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if (bufferedimage == null || bufferedimage.getWidth() != w || bufferedimage.getHeight() != h) {
            bufferedimage = (BufferedImage) createImage(w, h);
        }
        g2 = bufferedimage.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }
    
    // gets a specific sprite
    public Image getSprite(String name) {
        URL url = LazarusWorld.class.getResource(name);
        Image img = java.awt.Toolkit.getDefaultToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
        	System.out.println("Couldnt get sprite:" + name);
        }
        return img;
    }
}
