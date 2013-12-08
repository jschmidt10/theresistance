package theresistance.baseline.role;

import theresistance.core.Alignment;
import theresistance.core.Role;

abstract public class EvilRole implements Role
{
	@Override
	public String getName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public Alignment getAlignment()
	{
		return Alignment.EVIL;
	}

	@Override
	public String identify(Role other)
	{
		if (other.getAlignment().isGood())
		{
			return "Unknown";
		}
		else
		{
			return other.getName();
		}
	}
}
