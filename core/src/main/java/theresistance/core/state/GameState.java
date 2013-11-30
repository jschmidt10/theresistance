package theresistance.core.state;

import theresistance.core.Game;

public abstract class GameState
{
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public abstract boolean isFinished();
	public abstract void advanceGameState(Game game);
}
