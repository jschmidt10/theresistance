package theresistance.core;

/**
 * Represents good or evil
 */
public enum Alignment
{
	GOOD, EVIL;

	/**
	 * @return true if this is a good role, false, otherwise
	 */
	public boolean isGood()
	{
		return GOOD.equals(this);
	}
}
