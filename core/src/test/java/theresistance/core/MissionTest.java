package theresistance.core;

import org.junit.Assert;
import org.junit.Test;

import theresistance.core.Mission.Result;

public class MissionTest
{
	@Test
	public void testFailedMission()
	{
		Mission mission = new Mission(5, 1);
		mission.setResults(Result.PASS, Result.PASS, Result.PASS, Result.FAIL, Result.FAIL);

		Assert.assertFalse(mission.isSuccess());
	}

	@Test
	public void testNotEnoughFails()
	{
		Mission mission = new Mission(5, 2);
		mission.setResults(Result.PASS, Result.PASS, Result.PASS, Result.PASS, Result.FAIL);

		Assert.assertTrue(mission.isSuccess());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNumberOfResults()
	{
		Mission mission = new Mission(5, 2);
		mission.setResults(Result.PASS, Result.PASS, Result.PASS, Result.PASS);
	}
}
