package theresistance.core.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import theresistance.core.Alignment;
import theresistance.core.Game;
import theresistance.core.Mission.Result;
import theresistance.core.Player;
import theresistance.core.Round;

public class MissionState extends GameState<MissionResultAction>
{
	Map<Player, Result> results = new TreeMap<>();
	Set<String> leftToVote = new TreeSet<>();

	public MissionState(Collection<Player> participants)
	{
		for (Player player : participants)
		{
			results.put(player, null);
			leftToVote.add(player.getName());
		}
	}

	@Override
	public void act(MissionResultAction action)
	{
		results.put(action.getPlayer(), action.getResult());
		leftToVote.remove(action.getPlayer().getName());
	}

	@Override
	public boolean isFinished()
	{
		return leftToVote.isEmpty();
	}

	public Set<String> getParticipants()
	{
		Set<String> participants = new TreeSet<String>();
		for (Player player : results.keySet())
		{
			participants.add(player.getName());
		}
		return participants;
	}

	public Set<String> getPlayersLeftToVote()
	{
		return leftToVote;
	}

	@Override
	public void advance(Game game)
	{
		List<Result> results = new ArrayList<>(this.results.values());
		List<Result> shuffledResults = new ArrayList<>(results.size());

		Random random = new Random();
		while (!results.isEmpty())
		{
			int next = random.nextInt(results.size());
			shuffledResults.add(results.remove(next));
		}

		game.getCurrentRound().setResults(shuffledResults);
		game.completeRound();

		if (game.getCurrentRound() == null)
		{
			int succeeds = 0;
			int failures = 0;
			for (Round round : game.getRounds())
			{
				if (round.isSuccess())
				{
					succeeds++;
				}
				else
				{
					failures++;
				}
			}
			Alignment winner = Alignment.NEITHER;
			if (succeeds > failures)
			{
				winner = Alignment.GOOD;
			}
			else
			{
				winner = Alignment.EVIL;
			}

			game.setWinners(winner);
			game.setState(new GameOverState(winner));
		}
		else
		{
			game.setState(new ProposeState(game.getCurrentLeader(), game.getCurrentRound().getMission()
					.getNumParticipants()));
		}
	}
}
