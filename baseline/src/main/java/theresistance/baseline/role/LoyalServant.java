package theresistance.baseline.role;

import theresistance.core.Role;

/**
 * A loyal servant of arthur
 */
public class LoyalServant extends GoodRole
{
	@Override
	public String identify(Role other)
	{
		return "UNKNOWN";
	}
}
