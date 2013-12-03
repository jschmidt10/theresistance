package theresistance.core.state;

import theresistance.core.Game;

/**
 * The state that game is currently in
 */
public abstract class GameState<A extends GameAction>
{
	/**
	 * @return name of this state
	 */
	public String getName()
	{
		return this.getClass().getSimpleName();
	}

	/**
	 * progress the game with a player action
	 * 
	 * @param game
	 * @param action
	 */
	public void progress(Game game, A action)
	{
		act(action);

		if (isFinished())
		{
			advance(game);
		}
	}

	/**
	 * perform some internal processing based on user action
	 * 
	 * @param action
	 */
	public abstract void act(A action);

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
