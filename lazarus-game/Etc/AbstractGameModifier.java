/* Sends messages to modify game events */
package lazarus-game.Etc;

import java.util.Observable;

/*Abstract Game Modifiers are things that make changes to the game world:
 * player movements
 * player input
 * level events
 * */
public abstract class AbstractGameModifier extends Observable{
	
	public AbstractGameModifier(){}
	
	/* read is used to send messages from game observables to game observers */
	public abstract void read(Object theObject);
}