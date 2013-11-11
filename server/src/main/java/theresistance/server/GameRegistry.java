package theresistance.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import theresistance.core.Game;
import theresistance.core.config.GameConfig;

/**
 * Manages uniquely id'ing new games and managing existing games
 */
public class GameRegistry
{
	private Set<String> usedIds = new HashSet<>();
	private Map<String, GameConfig> configs = new HashMap<>();
	private Map<String, Game> games = new HashMap<>();

	/**
	 * @return new games
	 */
	public synchronized Collection<GameConfig> getNewGames()
	{
		return configs.values();
	}

	/**
	 * registers a new game config
	 * 
	 * @param config
	 * @return id for new config
	 */
	public synchronized String register(GameConfig config)
	{
		String id = null;

		do
		{
			id = UUID.randomUUID().toString();
		}
		while (usedIds.contains(id));

		configs.put(id, config);

		return id;
	}
}
