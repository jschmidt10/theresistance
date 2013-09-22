package theresistance.baseline;

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

		Assert.assertEquals("UNKNOWN", servant.identify(anotherServant));
		Assert.assertEquals("UNKNOWN", servant.identify(merlin));
		Assert.assertEquals("UNKNOWN", servant.identify(minion));
	}
}
