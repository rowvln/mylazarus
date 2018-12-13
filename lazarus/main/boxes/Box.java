/**
 *  The Box Class is the super class for all box types
*/

package lazarus.main.boxes;

import java.awt.Image;
import java.awt.Point;
import lazarus.main.Ship;

// Box constructor for Card, Metal, Stone, and Wood
public class Box extends Ship{
	public Box(Point position, Point speed, int strength, Image img){
        super(position, speed, strength, img);
    }
	public void setSpeed(Point pt){
        speed = pt;
    }
	public int getStrength(){
        return strength;
    }

    public void fire()
    {
    }
}
