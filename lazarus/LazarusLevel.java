/* The LazarusLevel class is created from a .txt file */
package lazarus;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import lazarus.main.BackgroundObject;
import lazarus.main.PlayerShip;
import lazarus.main.boxes.Box;
import lazarus.main.boxes.CardBox;
import lazarus.main.boxes.MetalBox;
import lazarus.main.boxes.StoneBox;
import lazarus.main.boxes.WoodBox;
import lazarus.etc.AbstractGameModifier;
import lazarus.ui.GameMenu;

// Creates the level from a textfile and implements observers to watch for updates/changes
public class LazarusLevel extends AbstractGameModifier implements Observer{
  int start;
  Integer position;
  String filename;
  BufferedReader level;
  Box currentBox;
  Box nextBox;
  int w;
  int h;
  static int endgameDelay = 5;
  Random generator = new Random();

  // creates the level from filename and tries to render what the map will look like
  public LazarusLevel(String filename){
    this.filename = filename;
    try{
      this.level = new BufferedReader(new InputStreamReader(LazarusWorld.class.getResource(filename).openStream()));
      String line = this.level.readLine();
      this.w = line.length();
      this.h = 0;
      while (line != null){
        this.h += 1;
        line = this.level.readLine();
      }
      this.level.close();
    }
    catch (IOException e){
      e.printStackTrace();
      System.exit(1);
    }
  }
  
  // reads the passed object
  public void read(Object anObject) {}
  
  // loads the structure of the map by creating a new instance
  public void load(){
    LazarusWorld world = LazarusWorld.getInstance();
    try{
      this.level = new BufferedReader(new InputStreamReader(LazarusWorld.class.getResource(this.filename).openStream()));
    }
    catch (IOException e){
      e.printStackTrace();
      System.exit(1);
    }
    try{
      String line = this.level.readLine();
      this.w = line.length();
      this.h = 0;
      while (line != null){
        int i = 0;
        for (int n = line.length(); i < n; i++){
          char c = line.charAt(i);
          if (c == '1'){
            Wall wall = new Wall(i, this.h, (Image)LazarusWorld.sprites.get("wall"));
            world.addBackground(new Wall[] { wall });
          }
          if (c == '2'){
        	  LazarusWorld.first++;
              Wall wall = new Wall(i, this.h, (Image)LazarusWorld.sprites.get("StopButton"));
              world.addBackground(new Wall[] { wall });
          }
          if (c == '3'){
            int[] controls = { 37, 38, 39, 40, 10 };
            world.addPlayer(new PlayerShip[] { new Lazarus(new Point(i * 40, this.h * 40), (Image)LazarusWorld.sprites.get("player1"), controls, "2") });
          }
          if (c == '4'){
            CardBox cardbox = new CardBox(i * 40, this.h * 40);
            world.addBackground(new Box[] { cardbox });
            this.currentBox = cardbox;
            this.nextBox = cardbox;
          }
        }
        this.h += 1;
        line = this.level.readLine();
      }
      this.level.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  // updates observers and objects based on actions
  public void update(Observable o, Object arg){
    LazarusWorld world = LazarusWorld.getInstance();
    if (world.countFallingboxes() < 1){
      Rectangle playerloc = new Rectangle(160, 0, 40, 40);
      Box box = getRandomBox(0, 440);
      this.currentBox = this.nextBox;
      ListIterator<PlayerShip> players = world.getPlayers();

      while (players.hasNext()){
        Lazarus player = (Lazarus)players.next();
        playerloc = player.getLocation();
      }

      this.currentBox.setLocation(new Point(playerloc.x, 0));

      if (GameMenu.selection == 0){
    	  this.currentBox.setSpeed(new Point(0, 2));
      }
      else if (GameMenu.selection == 1){
    	  this.currentBox.setSpeed(new Point(0, 4));
      }
      else if (GameMenu.selection == 2){
    	  this.currentBox.setSpeed(new Point(0, 6));
      }

      world.addFallingbox(new Box[] { this.currentBox });
      this.nextBox = box;

      world.addBackground(new Box[] { this.nextBox });
      setChanged();
      notifyObservers();
    }
    if (world.isGameOver()) {
      if (this.endgameDelay <= 0){
        world.removeClockObserver(this);
        world.finishGame();
      }
      else{
        this.endgameDelay -= 1;
      }
    }
  }
  
  // gets a random box and uses it as the next box dropped
  private Box getRandomBox(int x, int y){
    Box newBox = null;
    int i = this.generator.nextInt(4) + 1;
    if (i == 1) {
      newBox = new CardBox(x, y);
    } 
    else if (i == 2) {
      newBox = new WoodBox(x, y);
    } 
    else if (i == 3) {
      newBox = new MetalBox(x, y);
    } 
    else if (i == 4) {
      newBox = new StoneBox(x, y);
    }
    return newBox;
  }
}
