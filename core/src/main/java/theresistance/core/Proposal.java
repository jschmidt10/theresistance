package theresistance.core;

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
	private int onMissionCnt;
	private Player[] players;
	private Map<Player, Vote> votes = new TreeMap<Player, Vote>();

	public Proposal(int onMissionCnt, int totalPlayers)
	{
		this.onMissionCnt = onMissionCnt;
		this.totalPlayers = totalPlayers;
	}

	public void setPlayers(Player... players)
	{
		Arguments.verifyCount(onMissionCnt, players.length);
		this.players = players;
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
		return votes;
	}
}
