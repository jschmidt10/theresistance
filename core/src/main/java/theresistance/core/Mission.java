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

	private final int index;
	private final int numParticipants;
	private final int requiredFails;

	public Mission(int index, int numParticipants, int requiredFails)
	{
		this.index = index;
		this.numParticipants = numParticipants;
		this.requiredFails = requiredFails;
	}

	public int getIndex()
	{
		return index;
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
