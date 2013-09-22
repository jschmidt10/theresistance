package theresistance.baseline;

import theresistance.core.Role;

public class Percival extends GoodRole
{
	@Override
	public String identify(Role other)
	{
		if (other instanceof Merlin || other instanceof Morgana)
		{
			return "MERLIN_OR_MORGANA";
		}
		else
		{
			return "UNKNOWN";
		}
	}
}
