package theresistance.baseline.handler;

import theresistance.core.Alignment;
import theresistance.core.Game;
import theresistance.core.Mission;
import theresistance.core.PostRoundEventHandler;
import theresistance.core.Round;

/**
 * The post round handler that checks both sides for a victory (majority of the
 * rounds won)
 */
public class VictoryHandler implements PostRoundEventHandler
{
	private static final int GOOD_INDEX = 0;
	private static final int EVIL_INDEX = 1;

	private Game game;
	private int numWins;

	@Override
	public void init(Game game)
	{
		this.game = game;
		this.numWins = (game.getRounds().length + 1) / 2;
	}

	@Override
	public void roundFinished()
	{
		int[] scores = getScores();

		if (scores[GOOD_INDEX] >= numWins)
		{
			game.setWinners(Alignment.GOOD);
		}
		else if (scores[EVIL_INDEX] >= numWins)
		{
			game.setWinners(Alignment.EVIL);
		}
	}

	private int[] getScores()
	{
		int[] scores = new int[2];

		for (Round round : game.getRounds())
		{
			Mission mission = round.getMission();

			if (mission.isSent())
			{
				if (round.getMission().isSuccess())
				{
					scores[GOOD_INDEX]++;
				}
				else
				{
					scores[EVIL_INDEX]++;
				}
			}
		}

		return scores;
	}
}
