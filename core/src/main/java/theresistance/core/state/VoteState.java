package theresistance.core.state;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Proposal.Vote;
import theresistance.core.Round;

/**
 * State where players vote on a proposal
 */
public class VoteState extends GameState<VoteAction>
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

	@Override
	public void act(VoteAction action)
	{
		votes.put(action.getPlayer(), action.getVote());
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
			Round round = game.getCurrentRound();
			round.setParticipants(proposal.getParticipants());
			game.setState(new MissionState(proposal.getParticipants()));
		}
		else
		{
			game.gotoNextLeader();
			game.setState(new ProposeState(game.getCurrentLeader()));
		}
	}
}