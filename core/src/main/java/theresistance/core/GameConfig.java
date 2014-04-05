package theresistance.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Configuration for a new game
 */
public class GameConfig
{
	private String id;
	private String owner;
	private List<Role> roles = new LinkedList<>();
	private List<Mission> missions = new LinkedList<>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public List<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(List<Role> roles)
	{
		this.roles = roles;
	}

	public List<Mission> getMissions()
	{
		return missions;
	}

	public void setMissions(List<Mission> missions)
	{
		this.missions = missions;
	}
}
