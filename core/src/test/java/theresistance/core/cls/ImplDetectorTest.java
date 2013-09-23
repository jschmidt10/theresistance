package theresistance.core.cls;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import theresistance.core.Role;
import theresistance.core.stub.BadGuy;
import theresistance.core.stub.GoodGuy;

public class ImplDetectorTest
{
	@Test
	public void testLoadingRoles()
	{
		List<Role> roles = new ImplDetector<>(Role.class).getDetected();

		Assert.assertEquals(2, roles.size());
		Assert.assertTrue(roles.contains(new GoodGuy()));
		Assert.assertTrue(roles.contains(new BadGuy()));
	}
}
