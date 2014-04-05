package theresistance.core.selection;

import java.util.Set;

import theresistance.core.Player;

/**
 * Represents a selection between static options
 */
public class StaticChoice extends PlayerSelection<String>
{
	private Set<String> options;
	
	public StaticChoice(Player player, Set<String> options)
	{
		super(player);
		this.options = options;
	}
	
	public Set<String> getOptions()
	{
		return options;
	}
}
