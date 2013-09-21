package theresistance.core;

/**
 * A mission
 */
public class Mission
{
	public enum Result
	{
		PASS, FAIL
	}

	private int participants;
	private int requiredFails;
	private Result[] results;

	public Mission(int participants, int requiredFails)
	{
		this.participants = participants;
		this.requiredFails = requiredFails;
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
		Arguments.verifyCount(participants, results.length);
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
