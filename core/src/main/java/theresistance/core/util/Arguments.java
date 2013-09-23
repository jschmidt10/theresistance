package theresistance.core.util;

/**
 * Utility for checking arguments
 */
public class Arguments
{
	/**
	 * checks if the actual number of args equals the expected and throws an
	 * IllegalArgumentException if not
	 * 
	 * @param expected
	 * @param actual
	 * @throws IllegalArgumentException
	 */
	public static void verifyCount(int expected, int actual)
	{
		if (expected != actual)
		{
			throw new IllegalArgumentException(String.format("expected %d args but received %d", expected,
					actual));
		}
	}
}
