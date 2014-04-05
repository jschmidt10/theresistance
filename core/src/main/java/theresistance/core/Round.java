package theresistance.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import theresistance.core.Mission.Result;

/**
 * A round is a mission along with all of the proposals for that mission
 */
public class Round
{
	private static final int NUM_PROPOSALS = 5;

	private int index;
	private Mission mission;
	private List<Proposal> proposals = new ArrayList<>(NUM_PROPOSALS);
	private Set<Player> participants = new TreeSet<>();
	private List<Mission.Result> results = new LinkedList<>();

	public Round(int index, Mission mission)
	{
		this.index = index;
		this.mission = mission;
	}

	public int getIndex()
	{
		return index;
	}

	public List<Proposal> getProposals()
	{
		return proposals;
	}

	public int getProposalIndex()
	{
		return proposals.size();
	}

	public Proposal getLastProposal()
	{
		if (proposals.isEmpty())
		{
			return null;
		}
		else
		{
			return proposals.get(proposals.size() - 1);
		}
	}

	public void setParticipants(Set<Player> participants)
	{
		this.participants = participants;
	}

	public Set<Player> getParticipants()
	{
		return participants;
	}

	public void setResults(List<Mission.Result> results)
	{
		this.results = results;
	}

	public List<Mission.Result> getResults()
	{
		return results;
	}

	public Mission getMission()
	{
		return mission;
	}

	public void addProposal(Proposal proposal)
	{
		proposals.add(proposal);
	}

	public boolean isFinished()
	{
		return !results.isEmpty();
	}

	public Result getResult()
	{
		return isSuccess() ? Result.PASS : Result.FAIL;
	}

	public int getNumFails()
	{
		int failCnt = 0;

		for (Mission.Result result : results)
		{
			if (Mission.Result.FAIL.equals(result))
			{
				failCnt++;
			}
		}

		return failCnt;
	}

	public boolean isSuccess()
	{
		return getNumFails() < mission.getRequiredFails();
	}
}
