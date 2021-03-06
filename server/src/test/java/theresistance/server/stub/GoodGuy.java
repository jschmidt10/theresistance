package theresistance.server.stub;

import theresistance.core.Alignment;
import theresistance.core.Role;

/**
 * A simple good guy role for testing
 */
public class GoodGuy implements Role
{
	@Override
	public String getName()
	{
		return "Good Guy";
	}

	@Override
	public String identify(Role other)
	{
		return "Unknown";
	}

	@Override
	public Alignment getAlignment()
	{
		return Alignment.GOOD;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof GoodGuy;
	}
}
