package theresistance.core.stub;

import theresistance.core.Alignment;
import theresistance.core.Role;

/**
 * A simple bad guy role for testing
 */
public class BadGuy implements Role
{

	@Override
	public String identify(Role other)
	{
		return other.getClass().getSimpleName().toUpperCase();
	}

	@Override
	public Alignment getAlignment()
	{
		return Alignment.EVIL;
	}

}
