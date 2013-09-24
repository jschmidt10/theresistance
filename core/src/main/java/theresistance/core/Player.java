package theresistance.core;

import java.util.Map;

/**
 * A human player currently playing a game
 */
public class Player implements Comparable<Player>
{
	private String name;
	private Role role;
	private Map<String, Object> extraInfo;

	public Player(String name)
	{
		this.name = name;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public Role getRole()
	{
		return role;
	}

	@Override
	public int compareTo(Player other)
	{
		return name.compareTo(other.name);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj instanceof Player)
		{
			Player other = (Player) obj;
			return name.equals(other.name);
		}
		else
		{
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	public Map<String, Object> getExtraInfo() 
	{
		return extraInfo;
	}

	public void setExtraInfo(Map<String, Object> extraInfo) 
	{
		this.extraInfo = extraInfo;
	}
}
