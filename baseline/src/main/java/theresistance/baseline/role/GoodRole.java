package theresistance.baseline.role;

import theresistance.core.Alignment;
import theresistance.core.Role;

abstract public class GoodRole implements Role
{
	@Override
	public String getName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public Alignment getAlignment()
	{
		return Alignment.GOOD;
	}
}
