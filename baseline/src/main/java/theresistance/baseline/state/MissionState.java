package theresistance.baseline.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import theresistance.baseline.role.Assassin;
import theresistance.baseline.role.Merlin;
import theresistance.core.Alignment;
import theresistance.core.Game;
import theresistance.core.Mission.Result;
import theresistance.core.state.GameState;
import theresistance.core.Player;
import theresistance.core.Round;

public class MissionState extends GameState<MissionResultAction>
{
	private static final int ROUNDS_NEEDED_TO_WIN = 3;
	Map<String, Result> results = new TreeMap<>();
	Set<String> leftToVote = new TreeSet<>();

	public MissionState(Collection<Player> participants)
	{
		for (Player player : participants)
		{
			results.put(player.getName(), null);
			leftToVote.add(player.getName());
		}
	}

	@Override
	public Class<MissionResultAction> getGameActionClass()
	{
		return MissionResultAction.class;
	}

	@Override
	public void act(MissionResultAction action)
	{
		results.put(action.getPlayer(), action.getResult());
		leftToVote.remove(action.getPlayer());
	}

	@Override
	public boolean isFinished()
	{
		return leftToVote.isEmpty();
	}

	public Set<String> getParticipants()
	{
		Set<String> participants = new TreeSet<String>();
		for (String player : results.keySet())
		{
			participants.add(player);
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

		int succeeds = 0;
		int failures = 0;
		for (Round round : game.getRounds())
		{
			if (!round.isFinished()) 
			{
				break;
			}
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
		if (succeeds >= ROUNDS_NEEDED_TO_WIN)
		{
			boolean hasMerlin = false;
			boolean hasAssassin = false;
			Player assassin = null;
			for (Player player : game.getPlayers())
			{
				if (player.getRole() instanceof Merlin)
				{
					hasMerlin = true;
				}
				else if (player.getRole() instanceof Assassin)
				{
					hasAssassin = true;
					assassin = player;
				}
			}
			if (hasMerlin && hasAssassin)
			{
				game.setState(new AssassinationState(assassin.getName()));
				return;
			}
			else
			{
				winner = Alignment.GOOD;
			}
		}
		else if (failures >= ROUNDS_NEEDED_TO_WIN)
		{
			winner = Alignment.EVIL;
		}
		
		if (winner != Alignment.NEITHER)
		{
			game.setWinners(winner);
			game.setState(new GameOverState(winner));
		} 
		else 
		{
			Round round = game.getCurrentRound();
			boolean isHammer = round.getProposalIndex() == 4;
			game.setState(new ProposeState(game.getCurrentLeader(), round.getMission()
					.getNumParticipants(), isHammer));
		}
	}
}
