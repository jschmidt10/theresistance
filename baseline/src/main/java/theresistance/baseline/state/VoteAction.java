package theresistance.baseline.state;

import theresistance.core.Player;
import theresistance.core.Proposal.Vote;
import theresistance.core.state.GameAction;

/**
 * A proposal vote
 */
public class VoteAction implements GameAction
{
	private Player player;
	private Vote vote;

	public VoteAction(Player player, Vote vote)
	{
		this.player = player;
		this.vote = vote;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Vote getVote()
	{
		return vote;
	}
}
