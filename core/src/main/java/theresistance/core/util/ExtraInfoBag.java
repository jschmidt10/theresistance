package theresistance.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds a bag of extra info about other classes such as games, rounds, missions, etc.
 */
public class ExtraInfoBag
{
	private Map<String, Object> extraInfo = new HashMap<>();
	
	/**
	 * Creates a new empty extra info bag.
	 */
	public ExtraInfoBag() 
	{
	}
	
	/**
	 * Creates an extra info bag from the specified map.
	 * @param extraInfo
	 */
	public ExtraInfoBag(Map<String, Object> extraInfo) 
	{
		this.extraInfo = extraInfo;
	}
	
	public void putString(String key, String value)
	{
		extraInfo.put(key, value);
	}
	public void putInteger(String key, int value) 
	{
		extraInfo.put(key, value);
	}
	public void putLong(String key, long value)
	{
		extraInfo.put(key, value);
	}
	public void putBoolean(String key, boolean value)
	{
		extraInfo.put(key, value);
	}
	public void put(String key, String value)
	{
		putString(key, value);
	}
	public void put(String key, int value)
	{
		putInteger(key, value);
	}
	public void put(String key, long value)
	{
		putLong(key, value);
	}
	public void put(String key, boolean value)
	{
		putBoolean(key, value);
	}
	
	public String getString(String key, String defaultValue)
	{
		Object value = extraInfo.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			if (value instanceof String)
			{
				return (String)value;
			}
			else
			{
				return defaultValue;
			}
		}
	}
	public String get(String key, String defaultValue)
	{
		return getString(key, defaultValue);
	}
	public int getInteger(String key, int defaultValue)
	{
		Object value = extraInfo.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			if (value instanceof Integer)
			{
				return (Integer)value;
			}
			else if (value instanceof String)
			{
				try 
				{
					int intValue = Integer.parseInt((String)value);
					return intValue;
				}
				catch (NumberFormatException ex) { }
			}
			return defaultValue;
		}
	}
	
	public long getLong(String key, long defaultValue)
	{
		Object value = extraInfo.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else 
		{
			if (value instanceof Long)
			{
				return (Long)value;
			}
			else if (value instanceof String)
			{
				try 
				{
					long longValue = Long.parseLong((String)value);
					return longValue;
				}
				catch (NumberFormatException ex) { }
			}
			return defaultValue;
		}
	}
	
	public boolean getBoolean(String key, boolean defaultValue)
	{
		Object value = extraInfo.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			if (value instanceof Boolean)
			{
				return (Boolean)value;
			}
			else if (value instanceof String)
			{
				return ((String)value).equalsIgnoreCase("true");
			}
			else
			{
				return defaultValue;
			}
		}
	}
}
