package theresistance.baseline;

import org.junit.Assert;
import org.junit.Test;

public class MerlinTest
{
	@Test
	public void testMerlinSeesEvil_exceptMordred()
	{
		Merlin merlin = new Merlin();
		Mordred mordred = new Mordred();
		Morgana morgana = new Morgana();
		Minion minion = new Minion();

		Assert.assertEquals("EVIL", merlin.identify(minion));
		Assert.assertEquals("EVIL", merlin.identify(morgana));
		Assert.assertEquals("UNKNOWN", merlin.identify(mordred));
	}
}
