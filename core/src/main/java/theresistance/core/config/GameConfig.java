package theresistance.core.config;

import theresistance.core.Game;
import theresistance.core.Mission;
import theresistance.core.Player;
import theresistance.core.PostRoundEventHandler;
import theresistance.core.Role;
import theresistance.core.Round;

/**
 * Configures a new game with all the available options
 */
public class GameConfig
{
	private String owner;
	private Player[] players;
	private Role[] roles;
	private Mission[] missions;
	private PostRoundEventHandler[] handlers = new PostRoundEventHandler[0];

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public Player[] getPlayers()
	{
		return players;
	}

	public void setPlayers(Player... players)
	{
		this.players = players;
	}

	public Role[] getRoles()
	{
		return roles;
	}

	public void setRoles(Role... roles)
	{
		this.roles = roles;
	}

	public Mission[] getMissions()
	{
		return missions;
	}

	public void setMissions(Mission... missions)
	{
		this.missions = missions;
	}

	public PostRoundEventHandler[] getHandlers()
	{
		return handlers;
	}

	public void setHandlers(PostRoundEventHandler... handlers)
	{
		this.handlers = handlers;
	}

	/**
	 * creates a new game with the specified configurations
	 * 
	 * @return game
	 */
	public Game create()
	{
		Round[] rounds = new Round[missions.length];

		for (int i = 0; i < missions.length; ++i)
		{
			rounds[i] = new Round(i, missions[i]);
		}

		new RoleAssigner().assign(players, roles);

		Game game = new Game(players, rounds, handlers);

		for (PostRoundEventHandler handler : handlers)
		{
			handler.init(game);
		}

		return game;
	}
}
