package theresistance.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A round is a mission along with all of the proposals for that mission
 */
public class Round
{
	private static final int NUM_PROPOSALS = 5;

	private int index;
	private Mission mission;
	private List<Proposal> proposals = new ArrayList<Proposal>(NUM_PROPOSALS);

	public Round(int index, Mission mission)
	{
		this.index = index;
		this.mission = mission;
	}

	public int getIndex()
	{
		return index;
	}

	public Mission getMission()
	{
		return mission;
	}

	public void addProposal(Proposal proposal)
	{
		proposals.add(proposal);
	}
}
