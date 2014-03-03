package theresistance.baseline.state;

import theresistance.baseline.role.Merlin;
import theresistance.core.Alignment;
import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.state.GameState;

public class AssassinationState extends GameState<AssassinationAction> 
{
	String assassin;
	String assassinated = null;
	
	public AssassinationState(String assassin)
	{
		this.assassin = assassin;
	}
	
	@Override
	public Class<AssassinationAction> getGameActionClass() 
	{
		return AssassinationAction.class;
	}

	@Override
	public void act(AssassinationAction action) 
	{
		assassinated = action.getAssassinated();
	}

	@Override
	public boolean isFinished() 
	{
		return assassinated != null;
	}

	@Override
	public void advance(Game game) 
	{
		Player assassinated = game.getPlayer(this.assassinated);
		Alignment winner;
		if (assassinated.getRole() instanceof Merlin)
		{
			winner = Alignment.EVIL;
		}
		else
		{
			winner = Alignment.GOOD;
		}
		game.setWinners(winner);
		game.setState(new GameOverState(winner, assassin + " assassinated " + this.assassinated));
	}
	
	public String getAssassin()
	{
		return assassin;
	}
}
