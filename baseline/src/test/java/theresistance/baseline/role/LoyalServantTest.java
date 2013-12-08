package theresistance.baseline.role;

import org.junit.Assert;
import org.junit.Test;

public class LoyalServantTest
{
	@Test
	public void testSeesAllAsUnknown()
	{
		LoyalServant servant = new LoyalServant();
		LoyalServant anotherServant = new LoyalServant();
		Minion minion = new Minion();
		Merlin merlin = new Merlin();

		Assert.assertEquals("Unknown", servant.identify(anotherServant));
		Assert.assertEquals("Unknown", servant.identify(merlin));
		Assert.assertEquals("Unknown", servant.identify(minion));
	}
}
