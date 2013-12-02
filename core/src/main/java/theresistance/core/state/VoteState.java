package theresistance.core.state;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Proposal.Vote;

/**
 * State where players vote on a proposal
 */
public class VoteState extends GameState
{
	private Proposal proposal;
	private Map<Player, Vote> votes = new TreeMap<>();

	public VoteState(Collection<Player> players, Proposal proposal)
	{
		this.proposal = proposal;

		for (Player player : players)
		{
			votes.put(player, null);
		}
	}

	public void setVote(Player player, Vote vote)
	{
		votes.put(player, vote);
	}

	@Override
	public boolean isFinished()
	{
		return !votes.values().contains(null);
	}

	@Override
	public void advance(Game game)
	{
		proposal.setVotes(votes);

		if (proposal.isApproved())
		{
			game.send(proposal);
			game.setState(new MissionState(proposal.getParticipants()));
		}
		else
		{
			game.gotoNextLeader();
			game.setState(new ProposeState(game.getCurrentLeader()));
		}
	}
}
