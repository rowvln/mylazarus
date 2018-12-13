/* GameClock creates a game clock */
package lazarus;

import java.util.Observable;

//Game clock notifies observers to update after each tick
public class GameClock extends Observable {

	// Put variables here
	private int startTime;
	private int frame;
	
	// Creates a new GameClock object
	public GameClock(){
		startTime = (int) System.currentTimeMillis();
		frame = 0;
	}
	
	// Increases frame by one, updating observers along the way.
	public void tick(){
		frame += 1;
		setChange();
		this.notifyObservers();
	}
	
	// gets the current Frame
	public int getFrame(){
		return this.frame;
	}
	
	// gets the current Time
	public int getTime(){
		return (int) System.currentTimeMillis() - startTime;
	}
}