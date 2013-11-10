package theresistance.core.cls;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Scans the classpath for implementations of game extension implementations
 */
public class ImplDetector<T>
{
	private List<T> detected = new LinkedList<>();

	public ImplDetector(Class<T> clz)
	{
		ServiceLoader<T> loader = ServiceLoader.load(clz);

		for (T elem : loader)
		{
			detected.add(elem);
		}

		detected = Collections.unmodifiableList(detected);
	}

	public List<T> getDetected()
	{
		return detected;
	}
}
