package theresistance.core;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import theresistance.core.Mission.Result;

public class MissionTest
{
	@Test
	public void testFailedMission()
	{
		Round round = new Round(0, new Mission(5, 1));
		round.setResults(Arrays.asList(Result.PASS, Result.PASS, Result.PASS, Result.FAIL, Result.FAIL));

		Assert.assertFalse(round.isSuccess());
	}

	@Test
	public void testNotEnoughFails()
	{
		Round round = new Round(0, new Mission(5, 2));
		round.setResults(Arrays.asList(Result.PASS, Result.PASS, Result.PASS, Result.PASS, Result.FAIL));

		Assert.assertTrue(round.isSuccess());
	}
}
