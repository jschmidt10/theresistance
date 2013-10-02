package theresistance.baseline.role;

import org.junit.Assert;
import org.junit.Test;

import theresistance.baseline.role.LoyalServant;
import theresistance.baseline.role.Merlin;
import theresistance.baseline.role.Minion;
import theresistance.baseline.role.Morgana;
import theresistance.baseline.role.Percival;

public class PercivalTest
{
	@Test
	public void percivalSeesMerlinAndMorgana()
	{
		Percival percival = new Percival();
		Merlin merlin = new Merlin();
		Morgana morgana = new Morgana();

		Assert.assertEquals("MERLIN_OR_MORGANA", percival.identify(morgana));
		Assert.assertEquals("MERLIN_OR_MORGANA", percival.identify(merlin));
	}

	@Test
	public void percivalSeesEveryoneElse_asUnknown()
	{
		Percival percival = new Percival();
		LoyalServant servant = new LoyalServant();
		Minion minion = new Minion();

		Assert.assertEquals("UNKNOWN", percival.identify(servant));
		Assert.assertEquals("UNKNOWN", percival.identify(minion));
	}
}
