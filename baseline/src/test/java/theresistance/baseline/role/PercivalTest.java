package theresistance.baseline.role;

import org.junit.Assert;
import org.junit.Test;

public class PercivalTest
{
	@Test
	public void percivalSeesMerlinAndMorgana()
	{
		Percival percival = new Percival();
		Merlin merlin = new Merlin();
		Morgana morgana = new Morgana();

		Assert.assertEquals("Merlin or Morgana", percival.identify(morgana));
		Assert.assertEquals("Merlin or Morgana", percival.identify(merlin));
	}

	@Test
	public void percivalSeesEveryoneElse_asUnknown()
	{
		Percival percival = new Percival();
		LoyalServant servant = new LoyalServant();
		Minion minion = new Minion();

		Assert.assertEquals("Unknown", percival.identify(servant));
		Assert.assertEquals("Unknown", percival.identify(minion));
	}
}
