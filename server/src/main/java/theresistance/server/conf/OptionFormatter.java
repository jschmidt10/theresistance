package theresistance.server.conf;

/**
 * Prints a user friendly version of a dynamically loaded option
 */
public interface OptionFormatter<T>
{
	/**
	 * Gets a user friendly description of the option
	 * 
	 * @param option
	 * @return user friendly description
	 */
	public String getDescription(T option);
}
