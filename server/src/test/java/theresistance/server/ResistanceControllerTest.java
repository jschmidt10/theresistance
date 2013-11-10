package theresistance.server;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResistanceControllerTest
{
	private ResistanceController controller;

	@Before
	public void setUp()
	{
		controller = new ResistanceController();
	}

	@Test
	public void testListRoles()
	{
		Map<String, String> roles = controller.getAvailableRoles();

		Assert.assertEquals(2, roles.size());
		assertMapEntry(roles, "theresistance.server.stub.GoodGuy", "GoodGuy");
		assertMapEntry(roles, "theresistance.server.stub.BadGuy", "BadGuy");
	}

	@Test
	public void testListRules()
	{
		Map<String, String> rules = controller.getAvailableRules();

		Assert.assertEquals(1, rules.size());
		assertMapEntry(rules, "theresistance.server.stub.DummyRule1", "DummyRule1");
	}

	private void assertMapEntry(Map<String, String> map, String key, String value)
	{
		Assert.assertTrue(map.containsKey(key));
		Assert.assertEquals(value, map.get(key));
	}
}