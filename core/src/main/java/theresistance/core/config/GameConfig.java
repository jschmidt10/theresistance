package theresistance.core.config;

import theresistance.core.Mission;
import theresistance.core.Player;
import theresistance.core.Role;

public class GameConfig
{
	private Player[] players;
	private Role[] roles;
	private Mission[] missions;

	public Player[] getPlayers()
	{
		return players;
	}

	public void setPlayers(Player... players)
	{
		this.players = players;
	}

	public Role[] getRoles()
	{
		return roles;
	}

	public void setRoles(Role... roles)
	{
		this.roles = roles;
	}

	public Mission[] getMissions()
	{
		return missions;
	}

	public void setMissions(Mission... missions)
	{
		this.missions = missions;
	}
}
