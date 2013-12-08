package theresistance.core;

/**
 * A character role
 */
public interface Role
{
	/**
	 * Gets the name of this role
	 */
	String getName();

	/**
	 * Get a tag that identifies how this role should see the other
	 * 
	 * @param other
	 * @return tag
	 */
	String identify(Role other);

	/**
	 * @return alignment of the role
	 */
	Alignment getAlignment();

}
