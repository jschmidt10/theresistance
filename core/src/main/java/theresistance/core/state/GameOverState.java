package theresistance.core.state;

import theresistance.core.Alignment;
import theresistance.core.Game;

public class GameOverState extends GameState<GameOverAction> 
{
	private Alignment winner;
	
	public GameOverState(Alignment winner) 
	{
		this.winner = winner;
	}
	
	@Override
	public Class<GameOverAction> getGameActionClass() 
	{
		return GameOverAction.class;
	}
	
	@Override
	public void act(GameOverAction action) { }

	@Override
	public boolean isFinished() 
	{
		return false;
	}

	@Override
	public void advance(Game game) { }
	
	public Alignment getWinner() 
	{
		return winner;
	}

}
