package theresistance.baseline.state;

import theresistance.core.Player;
import theresistance.core.state.GameAction;

public class AssassinationAction implements GameAction 
{
	Player assassinated;
	
	public AssassinationAction(Player assassinated)
	{
		this.assassinated = assassinated;
	}
	
	public Player getAssassinated()
	{
		return assassinated;
	}
}
