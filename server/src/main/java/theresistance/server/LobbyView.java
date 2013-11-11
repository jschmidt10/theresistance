package theresistance.server;

import theresistance.core.config.GameConfig;

/**
 * Display information for the lobby view
 */
public class LobbyView
{
	private String gameId;
	private String owner;
	private int currentPlayers;
	private int totalPlayers;

	public LobbyView(GameConfig config)
	{
		this.gameId = config.getId();
		this.owner = config.getOwner();
		this.currentPlayers = config.getPlayers().size();
		this.totalPlayers = config.getRoles().length;
	}

	public String getGameId()
	{
		return gameId;
	}

	public String getOwner()
	{
		return owner;
	}

	public int getCurrentPlayers()
	{
		return currentPlayers;
	}

	public int getTotalPlayers()
	{
		return totalPlayers;
	}
}
