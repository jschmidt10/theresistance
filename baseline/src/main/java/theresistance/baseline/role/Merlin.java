package theresistance.baseline.role;

import theresistance.core.Role;

public class Merlin extends GoodRole
{
	@Override
	public String identify(Role other)
	{
		if (other.getAlignment().isGood() || other instanceof Mordred)
		{
			return "UNKNOWN";
		}
		else
		{
			return "EVIL";
		}
	}
}
