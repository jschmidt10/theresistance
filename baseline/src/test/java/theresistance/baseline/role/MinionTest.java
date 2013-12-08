package theresistance.baseline.role;

import org.junit.Assert;
import org.junit.Test;

public class MinionTest
{
	@Test
	public void testMinionsCanSeeEvil_specificRoles()
	{
		Minion minion = new Minion();
		Minion anotherMinion = new Minion();
		Mordred mordred = new Mordred();
		Assassin assassin = new Assassin();

		Assert.assertEquals("Minion", minion.identify(anotherMinion));
		Assert.assertEquals("Mordred", minion.identify(mordred));
		Assert.assertEquals("Assassin", minion.identify(assassin));
	}

	@Test
	public void testMinionsCanSeeGood_asUnknown()
	{
		Minion minion = new Minion();
		LoyalServant servant = new LoyalServant();
		Merlin merlin = new Merlin();

		Assert.assertEquals("Unknown", minion.identify(servant));
		Assert.assertEquals("Unknown", minion.identify(merlin));
	}
}
