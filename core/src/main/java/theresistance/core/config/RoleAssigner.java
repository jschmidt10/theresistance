package theresistance.core.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import theresistance.core.Player;
import theresistance.core.Role;

/**
 * Randomly assigns roles to players
 */
public class RoleAssigner
{
	private static final Random RANDOM = new Random(System.currentTimeMillis());

	/**
	 * assigns roles to players
	 * 
	 * @param players
	 * @param roles
	 */
	public void assign(Player[] players, Role[] roles)
	{
		List<Role> roleList = cloneToList(roles);

		for (int i = 0; i < players.length; ++i)
		{
			int j = RANDOM.nextInt(roleList.size());
			players[i].setRole(roleList.get(j));
			roleList.remove(j);
		}
	}

	private List<Role> cloneToList(Role[] roles)
	{
		List<Role> roleList = new ArrayList<Role>(roles.length);
		for (Role role : roles)
		{
			roleList.add(role);
		}
		return roleList;
	}
}
