package theresistance.core;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * A mission proposal
 */
public class Proposal
{
	public enum Vote
	{
		SEND, DONT_SEND
	}

	private int totalPlayers;
	private Player[] participants;
	private Map<Player, Vote> votes = new TreeMap<Player, Vote>();

	public Proposal(int totalPlayers)
	{
		this.totalPlayers = totalPlayers;
	}

	public void setParticipants(Player... participants)
	{
		this.participants = participants;
	}

	public Player[] getParticipants()
	{
		return participants;
	}

	public boolean isApproved()
	{
		int approvals = 0;

		for (Vote vote : votes.values())
		{
			if (Vote.SEND.equals(vote))
			{
				approvals++;
			}
		}

		return approvals * 2 > totalPlayers;
	}

	public void setVote(Player player, Vote vote)
	{
		votes.put(player, vote);
	}

	public Map<Player, Vote> getVotes()
	{
		return Collections.unmodifiableMap(votes);
	}
}
