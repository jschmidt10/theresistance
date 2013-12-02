package theresistance.core.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import theresistance.core.Game;
import theresistance.core.Mission.Result;
import theresistance.core.Player;

public class WaitingForMissionResult extends GameState
{
	Set<String> participants = new TreeSet<String>();
	Set<String> waitingForPlayers = new TreeSet<String>();
	Map<Player, Result> results = new TreeMap<>();
	
	public WaitingForMissionResult(Collection<Player> participants)
	{
		for (Player player : participants)
		{
			this.participants.add(player.getName());
			this.waitingForPlayers.add(player.getName());
		}
	}
	
	public Set<String> getWaitingForPlayers()
	{
		return waitingForPlayers;
	}
	
	public void setResult(Player player, Result result)
	{
		if (!participants.contains(player.getName()))
		{
			return;
		}
		results.put(player, result);
		waitingForPlayers.remove(player.getName());
	}
	
	@Override
	public boolean isFinished()
	{
		return waitingForPlayers.isEmpty();
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
	}
}
