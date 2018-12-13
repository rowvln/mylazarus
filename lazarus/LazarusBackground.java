/* Background for Lazarus game is here */
package lazarus;

import java.awt.Image;
import java.awt.Point;
import lazarus.main.BackgroundObject;
import lazarus.main.GameObject;

// LazarusBackground class is derived from BackgroundObject class
public class LazarusBackground extends BackgroundObject {
  int w;
  int h;
  
  // creates a background with width w, height h, point speed, and the appropriate image
  public LazarusBackground(int w, int h, Point speed, Image img){
    super(new Point(0, 0), speed, img);
    setImage(img);
    this.img = img;
    this.w = w;
    this.h = h;
  }
  
  // updates observers based on change in width or height
  public void update(int w, int h) {}
  
  // updates collision
  public boolean collision(GameObject otherObject)
  {
    return false;
  }
}