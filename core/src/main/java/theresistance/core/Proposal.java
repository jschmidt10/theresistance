package theresistance.core;

import java.util.Collections;
import java.util.List;
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

	private final int totalPlayers;
	private final Player leader;

	private List<Player> participants;
	private Map<String, Vote> votes = new TreeMap<>();

	public Proposal(Player leader, int totalPlayers)
	{
		this.leader = leader;
		this.totalPlayers = totalPlayers;
	}

	/**
	 * @param participants
	 */
	public void setParticipants(List<Player> participants)
	{
		this.participants = participants;
	}

	/**
	 * @return mission participants
	 */
	public List<Player> getParticipants()
	{
		return participants;
	}

	/**
	 * @return true if the strict majority approved the participants, false,
	 *         otherwise
	 */
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

	/**
	 * sets a players vote
	 * 
	 * @param player
	 * @param vote
	 */
	public void setVote(Player player, Vote vote)
	{
		votes.put(player.getName(), vote);
	}

	public void setVotes(Map<String, Vote> votes)
	{
		this.votes.putAll(votes);
	}

	/**
	 * @return votes
	 */
	public Map<String, Vote> getVotes()
	{
		return Collections.unmodifiableMap(votes);
	}

	/**
	 * @return mission leader
	 */
	public Player getLeader()
	{
		return leader;
	}
}
