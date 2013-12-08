package theresistance.baseline.role;

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

		Assert.assertEquals("Evil", merlin.identify(minion));
		Assert.assertEquals("Evil", merlin.identify(morgana));
		Assert.assertEquals("Unknown", merlin.identify(mordred));
	}

	@Test
	public void testMerlinSeesGood_asUnknown()
	{
		Merlin merlin = new Merlin();
		LoyalServant servant = new LoyalServant();
		Percival percival = new Percival();

		Assert.assertEquals("Unknown", merlin.identify(servant));
		Assert.assertEquals("Unknown", merlin.identify(percival));
	}
}
