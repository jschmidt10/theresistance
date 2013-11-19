package theresistance.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import theresistance.core.Game;

/**
 * Manages uniquely id'ing new games and managing existing games
 */
public class GameRegistry
{
	private Map<String, Game> games = new HashMap<>();

	/**
	 * @return new games
	 */
	public synchronized Collection<Game> getNewGames()
	{
		List<Game> newGames = new LinkedList<>();

		for (Game g : games.values())
		{
			if (!g.isStarted())
			{
				newGames.add(g);
			}
		}

		return newGames;
	}

	/**
	 * registers a new game
	 * 
	 * @param game
	 * @return game id
	 */
	public synchronized String register(Game game)
	{
		String id = null;

		do
		{
			id = UUID.randomUUID().toString();
		}
		while (games.containsKey(id));

		games.put(id, game);
		game.setId(id);

		return id;
	}

	/**
	 * @param gameId
	 * @return game config
	 */
	public synchronized Game getGame(String gameId)
	{
		return games.get(gameId);
	}
}
