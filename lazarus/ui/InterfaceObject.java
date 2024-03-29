/* InterfaceObject Constructor for Lazarus game */
package lazarus.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public abstract class InterfaceObject implements Observer {
	Point location;
	ImageObserver observer;
	
	public abstract void draw(Graphics g, int x, int y);

	public void update(Observable o, Object arg) {
	}
}