package theresistance.core;

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

	private int numParticipants;
	private int requiredFails;
	private Player[] participants;
	private Result[] results;

	public Mission(int numParticipants, int requiredFails)
	{
		this.numParticipants = numParticipants;
		this.requiredFails = requiredFails;
	}

	public int getNumParticipants()
	{
		return numParticipants;
	}

	public void setParticipants(Player[] participants)
	{
		this.participants = participants;
	}

	/**
	 * sets the results from the mission
	 * 
	 * @param results
	 * @throws IllegalArgumentException
	 *             when the number of results differs from the number of
	 *             participants
	 */
	public void setResults(Result... results)
	{
		Arguments.verifyCount(numParticipants, results.length);
		this.results = results;
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

		return failCnt < requiredFails;
	}
}
