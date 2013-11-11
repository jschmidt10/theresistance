package theresistance.server.view;

import theresistance.core.Player;

/**
 * View bean for player list
 */
public class PlayerView
{
	private String name;

	public PlayerView(Player player)
	{
		this.name = player.getName();
	}

	public String getName()
	{
		return name;
	}
}
