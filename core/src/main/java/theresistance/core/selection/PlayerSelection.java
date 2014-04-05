package theresistance.core.selection;

import theresistance.core.Player;

/**
 * Represents a choice made by a player
 * 
 * @param <T>
 */
abstract public class PlayerSelection<T>
{
	private Player player;
	private T choice;

	public PlayerSelection(Player player)
	{
		this.player = player;
	}

	public Player getPlayer()
	{
		return player;
	}
	
	public void setChoice(T choice)
	{
		this.choice = choice;
	}
	
	public T getChoice()
	{
		return choice;
	}
}
