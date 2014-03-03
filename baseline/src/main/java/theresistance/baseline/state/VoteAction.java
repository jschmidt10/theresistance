package theresistance.baseline.state;

import theresistance.core.Proposal.Vote;
import theresistance.core.state.GameAction;

/**
 * A proposal vote
 */
public class VoteAction implements GameAction
{
	private String player;
	private Vote vote;

	public String getPlayer()
	{
		return player;
	}
	
	public void setPlayer(String player)
	{
		this.player = player;
	}
	
	public void setVote(String vote) 
	{
		this.vote = Vote.valueOf(vote);
	}

	public Vote getVote()
	{
		return vote;
	}
}
