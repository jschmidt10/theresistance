package theresistance.core;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import theresistance.core.util.ExtraInfoBag;

/**
 * A mission proposal
 */
public class Proposal
{
	public enum Vote
	{
		SEND, DONT_SEND
	}

	private Player leader;
	private ExtraInfoBag extraInfo = new ExtraInfoBag();
	private int totalPlayers;
	private Player[] participants;
	private Map<Player, Vote> votes = new TreeMap<>();

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

	public Player getLeader()
	{
		return leader;
	}

	public void setLeader(Player leader)
	{
		this.leader = leader;
	}

	public ExtraInfoBag getExtraInfo()
	{
		return extraInfo;
	}

	public void setExtraInfo(ExtraInfoBag extraInfo)
	{
		this.extraInfo = extraInfo;
	}
}
