package theresistance.server.conf;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import theresistance.core.cls.ImplDetector;

/**
 * Handles dynamically loaded game options
 */
public class DynamicGameOptions<T>
{
	private final Map<String, T> impls = new HashMap<>();
	private final Map<String, String> descriptions = new HashMap<>();

	public DynamicGameOptions(Class<T> optClazz, OptionFormatter<T> formatter)
	{
		for (T opt : new ImplDetector<T>(optClazz).getDetected())
		{
			descriptions.put(opt.getClass().getName(), formatter.getDescription(opt));
			impls.put(opt.getClass().getName(), opt);
		}
	}

	/**
	 * @return all options with their descriptions
	 */
	public Map<String, String> getDescriptions()
	{
		return descriptions;
	}

	/**
	 * @param names
	 * @return gets option implementations by names
	 */
	public List<T> getOptions(Collection<String> names)
	{
		List<T> results = new LinkedList<>();

		for (String name : names)
		{
			results.add(impls.get(name));
		}

		return results;
	}
}
