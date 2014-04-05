package theresistance.core.selection;

import java.util.Set;

import theresistance.core.Player;

/**
 * Represents a selection between static options
 */
public class StaticChoice<T> extends PlayerSelection<T>
{
	private Set<T> options;
	
	public StaticChoice(Player player, Set<T> options)
	{
		super(player);
		this.options = options;
	}
	
	public Set<T> getOptions()
	{
		return options;
	}
}
