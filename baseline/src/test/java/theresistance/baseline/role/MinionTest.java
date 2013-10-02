package theresistance.baseline.role;

import org.junit.Assert;
import org.junit.Test;

import theresistance.baseline.role.Assassin;
import theresistance.baseline.role.LoyalServant;
import theresistance.baseline.role.Merlin;
import theresistance.baseline.role.Minion;
import theresistance.baseline.role.Mordred;

public class MinionTest
{
	@Test
	public void testMinionsCanSeeEvil_specificRoles()
	{
		Minion minion = new Minion();
		Minion anotherMinion = new Minion();
		Mordred mordred = new Mordred();
		Assassin assassin = new Assassin();

		Assert.assertEquals("MINION", minion.identify(anotherMinion));
		Assert.assertEquals("MORDRED", minion.identify(mordred));
		Assert.assertEquals("ASSASSIN", minion.identify(assassin));
	}

	@Test
	public void testMinionsCanSeeGood_asUnknown()
	{
		Minion minion = new Minion();
		LoyalServant servant = new LoyalServant();
		Merlin merlin = new Merlin();

		Assert.assertEquals("UNKNOWN", minion.identify(servant));
		Assert.assertEquals("UNKNOWN", minion.identify(merlin));
	}
}
