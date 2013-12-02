package theresistance.core.state;

import theresistance.core.Game;

/**
 * The state that game is currently in
 */
public abstract class GameState
{
	/**
	 * @return name of this state
	 */
	public String getName()
	{
		return this.getClass().getSimpleName();
	}

	/**
	 * @return true if this state is finished
	 */
	public abstract boolean isFinished();

	/**
	 * advances the game to the next state
	 * 
	 * @param game
	 */
	public abstract void advance(Game game);
}
