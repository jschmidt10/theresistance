package theresistance.server.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Proposal.Vote;
import theresistance.core.Round;

public class ProposalView
{
	private int round;
	private String leader;
	private List<String> participants = new LinkedList<>();
	private Map<String, String> votes = new TreeMap<>();

	public ProposalView(Proposal proposal, Round round)
	{
		this.round = round.getIndex();
		this.leader = proposal.getLeader().getName();

		for (Player player : proposal.getParticipants())
		{
			participants.add(player.getName());
		}

		for (Map.Entry<String, Vote> entry : proposal.getVotes().entrySet())
		{
			votes.put(entry.getKey(), entry.getValue().toString());
		}
	}

	public int getRound()
	{
		return round;
	}

	public String getLeader()
	{
		return leader;
	}

	public List<String> getParticipants()
	{
		return participants;
	}

	public Map<String, String> getVotes()
	{
		return votes;
	}
}
