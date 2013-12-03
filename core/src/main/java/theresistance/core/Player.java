package theresistance.core;


/**
 * A human player currently playing a game
 */
public class Player implements Comparable<Player>
{
	private String name;
	private Role role;

	public Player(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
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
}
