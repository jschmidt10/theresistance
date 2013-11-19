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

	private final int numParticipants;
	private final int requiredFails;

	public Mission(int numParticipants, int requiredFails)
	{
		this.numParticipants = numParticipants;
		this.requiredFails = requiredFails;
	}

	public int getNumParticipants()
	{
		return numParticipants;
	}

	public int getRequiredFails()
	{
		return requiredFails;
	}
}
