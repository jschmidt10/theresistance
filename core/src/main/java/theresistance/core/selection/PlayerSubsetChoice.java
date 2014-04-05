package theresistance.core.selection;

import java.util.List;
import java.util.Set;

import theresistance.core.Player;

/**
 * Represents a selection which includes some subset of the players
 */
public class PlayerSubsetChoice extends PlayerSelection<List<Player>>
{
	private Set<Player> subset;
	private int numPlayers;

	public PlayerSubsetChoice(Player player, Set<Player> subset, int numPlayers)
	{
		super(player);
		this.subset = subset;
		this.numPlayers = numPlayers;
	}
	
	public Set<Player> getSubset()
	{
		return subset;
	}
	
	public int getNumPlayers()
	{
		return numPlayers;
	}
}
