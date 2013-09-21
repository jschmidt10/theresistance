package theresistance.baseline;

import theresistance.core.Alignment;
import theresistance.core.Role;

abstract public class GoodRole implements Role
{
	@Override
	public Alignment getAlignment()
	{
		return Alignment.GOOD;
	}
}
