package theresistance.core.round;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import theresistance.core.Player;
import theresistance.core.selection.PlayerSelection;

/**
 * Represents a part of processing where players take some action
 */
abstract public class InteractionNode<T> extends RoundNode
{
	protected Map<Player, T> choices = new HashMap<>();
	protected RoundNode next;
	
	public InteractionNode(RoundNode next) 
	{
		this.next = next;
	}
	
	public abstract List<? extends PlayerSelection<T>> getInteractions();
	
	public abstract Set<Player> getNecessaryPlayers();
	
	@Override
	public RoundNode next()
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
