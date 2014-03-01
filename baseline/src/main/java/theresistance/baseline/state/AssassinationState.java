package theresistance.baseline.state;

import theresistance.baseline.role.Merlin;
import theresistance.core.Alignment;
import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.state.GameState;

public class AssassinationState extends GameState<AssassinationAction> 
{
	Player assassin;
	Player assassinated = null;
	
	public AssassinationState(Player assassin)
	{
		this.assassin = assassin;
	}
	
	@Override
	public void act(AssassinationAction action) 
	{
		this.assassinated = action.getAssassinated();
	}

	@Override
	public boolean isFinished() 
	{
		return assassinated != null;
	}

	@Override
	public void advance(Game game) 
	{
		Alignment winner;
		if (assassinated.getRole() instanceof Merlin)
		{
			winner = Alignment.GOOD;
		}
		else
		{
			winner = Alignment.EVIL;
		}
		game.setWinners(winner);
		game.setState(new GameOverState(winner));
	}

}
