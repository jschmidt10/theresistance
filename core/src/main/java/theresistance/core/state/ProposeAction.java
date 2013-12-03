package theresistance.core.state;

import java.util.List;

import theresistance.core.Player;

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
