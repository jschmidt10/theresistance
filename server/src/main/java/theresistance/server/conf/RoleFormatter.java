package theresistance.server.conf;

import theresistance.core.Role;

/**
 * OptionFormatter for Roles
 */
public class RoleFormatter implements OptionFormatter<Role>
{
	@Override
	public String getDescription(Role option)
	{
		return option.getClass().getSimpleName();
	}
}
