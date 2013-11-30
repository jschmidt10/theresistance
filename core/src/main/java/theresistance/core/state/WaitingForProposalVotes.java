package theresistance.core.state;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Proposal.Vote;

public class WaitingForProposalVotes extends GameState
{
	private Proposal proposal;
	private Set<String> playersLeftToVote;
	private Map<Player, Vote> votes = new TreeMap<>();

	public WaitingForProposalVotes(Collection<Player> players, Proposal proposal)
	{
		this.proposal = proposal;
		playersLeftToVote = new TreeSet<String>();
		for (Player player : players)
		{
			playersLeftToVote.add(player.getName());
		}
	}

	public Set<String> getPlayersLeftToVote()
	{
		return playersLeftToVote;
	}

	public void setVote(Player player, Vote vote)
	{
		votes.put(player, vote);
		playersLeftToVote.remove(player);
	}

	@Override
	public boolean isFinished()
	{
		return playersLeftToVote.isEmpty();
	}
	
	@Override
	public void advanceGameState(Game game)
	{
		proposal.setVotes(votes);
		if (proposal.isApproved())
		{
			game.send(proposal);
		}
		else
		{
			game.gotoNextLeader();
			game.setGameState(new WaitingForProposal(game.getCurrentLeader()));
		}
	}
}
