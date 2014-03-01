package theresistance.baseline.state;

import java.util.List;

import theresistance.core.Player;
import theresistance.core.state.GameAction;

/**
 * A proposal
 */
public class ProposeAction implements GameAction
{
	private List<Player> players;

	public ProposeAction(List<Player> players)
	{
		this.players = players;
	}

	public List<Player> getPlayers()
	{
		return players;
	}
}
