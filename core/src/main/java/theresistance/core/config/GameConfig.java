package theresistance.core.config;

import java.util.LinkedList;
import java.util.List;

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
	private String id;
	private String owner;
	private List<Player> players = new LinkedList<Player>();
	private Role[] roles;
	private Mission[] missions;
	private PostRoundEventHandler[] handlers = new PostRoundEventHandler[0];

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public void addPlayer(Player player)
	{
		players.add(player);
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

		// TODO: might want to handle this better
		Player[] players = this.players.toArray(new Player[this.players.size()]);

		new RoleAssigner().assign(players, roles);

		Game game = new Game(players, rounds, handlers);

		for (PostRoundEventHandler handler : handlers)
		{
			handler.init(game);
		}

		return game;
	}
}
