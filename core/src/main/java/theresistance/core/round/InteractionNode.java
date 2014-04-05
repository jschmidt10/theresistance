package theresistance.core.round;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.selection.PlayerSelection;

/**
 * Represents a part of processing where players take some action
 */
abstract public class InteractionNode<T> implements RoundNode
{
	private Map<Player, T> choices = new HashMap<>();
	private RoundNode next;
	
	public InteractionNode(RoundNode next)
	{
		this.next = next;
	}
	
	abstract List<PlayerSelection<T>> getInteractions(Game game);
	
	abstract Set<Player> getNecessaryPlayers();
	
	@Override
	public RoundNode next(Game game)
	{
		return next;
	}

	public void update(Player player, T choice)
	{
		choices.put(player, choice);
	}
	
	public boolean isFinished() {
		return choices.keySet().containsAll(getNecessaryPlayers());
	}
}
