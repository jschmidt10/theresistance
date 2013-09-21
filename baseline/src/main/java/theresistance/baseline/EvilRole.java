package theresistance.baseline;

import theresistance.core.Alignment;
import theresistance.core.Role;

abstract public class EvilRole implements Role
{
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
			return "UNKNOWN";
		}
		else
		{
			return other.getClass().getSimpleName().toUpperCase();
		}
	}
}
