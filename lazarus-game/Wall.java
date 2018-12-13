/* Wall class is used to create and draw wall objects */
package lazarus-game;

import java.awt.Image;
import java.awt.Point;
import lazarus.game.BackgroundObject;
import lazarus.game.enemy.Box;

public class Wall extends Box{

  // Put variables here
  static int firstX;
  static int secondX;

  // This Wall constructor creates a new Wall object
  public Wall(int x, int y, Image image){
    super(new Point(x * 40, y * 40), new Point(0, 0), y, image);
    
    if (LazarusWorld.first == 1){
      firstX = x * 40;
    }
    else if (LazarusWorld.first == 2)
      secondX = x * 40;
  }
 }