package theresistance.baseline.role;

import theresistance.core.Role;

public class Percival extends GoodRole
{
	@Override
	public String identify(Role other)
	{
		if (other instanceof Merlin || other instanceof Morgana)
		{
			return "Merlin or Morgana";
		}
		else
		{
			return "Unknown";
		}
	}
}
