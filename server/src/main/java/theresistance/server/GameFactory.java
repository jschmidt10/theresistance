package theresistance.server;

import java.lang.reflect.Constructor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import theresistance.core.Game;
import theresistance.core.GameConfig;

/**
 * Wrapper for creating new games in the server
 */
@Component
public class GameFactory
{
	private final Class<? extends Game> gameClass;
	private Constructor<? extends Game> constructor;

	@Autowired
	public GameFactory(Class<? extends Game> gameClass)
	{
		this.gameClass = gameClass;
	}

	@PostConstruct
	public void init() throws NoSuchMethodException, SecurityException
	{
		constructor = gameClass.getConstructor(GameConfig.class);
	}

	/**
	 * Creates a new game
	 * 
	 * @param config
	 * @return game
	 */
	public Game createNewGame(GameConfig config)
	{
		try
		{
			return constructor.newInstance(config);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not create a new game!", e);
		}
	}
}
