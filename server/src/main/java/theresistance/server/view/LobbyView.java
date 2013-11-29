package theresistance.server.view;

import theresistance.core.Game;
import theresistance.core.GameConfig;

/**
 * Display information for the lobby view
 */
public class LobbyView
{
	private String gameId;
	private String owner;
	private int currentPlayers;
	private int totalPlayers;
	private boolean isStarted;

	public LobbyView(Game game)
	{
		this.gameId = game.getId();
		this.currentPlayers = game.getPlayers().size();
		this.isStarted = game.isStarted();

		GameConfig config = game.getConfig();
		this.owner = config.getOwner();
		this.totalPlayers = config.getRoles().size();
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

	public boolean isStarted()
	{
		return isStarted;
	}

	public void setStarted(boolean isStarted)
	{
		this.isStarted = isStarted;
	}
}
