package theresistance.core;

import java.util.LinkedList;
import java.util.List;

import theresistance.core.util.Arguments;

/**
 * A mission
 */
public class Mission
{
	public enum Result
	{
		PASS, FAIL
	}

	private final int numParticipants;
	private final int requiredFails;
	private List<Player> participants = new LinkedList<>();
	private List<Result> results = new LinkedList<>();

	public Mission(int numParticipants, int requiredFails)
	{
		this.numParticipants = numParticipants;
		this.requiredFails = requiredFails;
	}

	public int getNumParticipants()
	{
		return numParticipants;
	}

	public void setParticipants(List<Player> participants)
	{
		this.participants = participants;
	}

	public List<Player> getParticipants()
	{
		return participants;
	}

	/**
	 * sets the results from the mission
	 * 
	 * @param results
	 * @throws IllegalArgumentException
	 *             when the number of results differs from the number of
	 *             participants
	 */
	public void setResults(List<Result> results)
	{
		Arguments.verifyCount(numParticipants, results.size());
		this.results = results;
	}

	/**
	 * @return true is this mission has been sent, false, otherwise
	 */
	public boolean isSent()
	{
		return !results.isEmpty();
	}

	/**
	 * checks if this mission was successful or not
	 * 
	 * @return true if the mission was a success, false, otherwise
	 */
	public boolean isSuccess()
	{
		int failCnt = 0;

		for (Result result : results)
		{
			if (Result.FAIL.equals(result))
			{
				failCnt++;
			}
		}

		return !results.isEmpty() && failCnt < requiredFails;
	}
}
