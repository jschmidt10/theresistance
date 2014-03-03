package theresistance.baseline.state;

import theresistance.core.Alignment;
import theresistance.core.Game;
import theresistance.core.state.GameState;

public class GameOverState extends GameState<GameOverAction> 
{
	private Alignment winner;
	private String extraMessage = "";
	
	public GameOverState(Alignment winner)
	{
		this(winner, "");
	}
	public GameOverState(Alignment winner, String extraMessage) 
	{
		this.winner = winner;
		this.extraMessage = extraMessage;
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
	
	public String getExtraMessage()
	{
		return extraMessage;
	}
}
