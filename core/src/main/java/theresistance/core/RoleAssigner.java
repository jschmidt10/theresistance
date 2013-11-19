package theresistance.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	public void assign(List<Player> players, List<Role> roles)
	{
		List<Role> clonedRoles = new LinkedList<>(roles);

		for (int i = 0; i < players.size(); ++i)
		{
			int j = RANDOM.nextInt(clonedRoles.size());
			players.get(i).setRole(clonedRoles.get(j));
			clonedRoles.remove(j);
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
