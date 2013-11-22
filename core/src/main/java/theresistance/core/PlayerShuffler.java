package theresistance.core;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Shuffles players in a list
 */
public class PlayerShuffler
{
	private final Random random = new Random();

	public List<Player> shuffle(List<Player> players)
	{
		Player[] results = new Player[players.size()];

		for (Player player : players)
		{
			int index = -1;

			do
			{
				index = random.nextInt(players.size());
			} while (results[index] != null);

			results[index] = player;
		}

		return Arrays.asList(results);
	}
}
