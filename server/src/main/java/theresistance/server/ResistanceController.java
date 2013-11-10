package theresistance.server;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.core.Player;
import theresistance.core.PostRoundEventHandler;
import theresistance.core.Role;
import theresistance.core.cls.ImplDetector;

@Controller
public class ResistanceController
{
	private static final Map<String, String> ROLES = new TreeMap<String, String>();
	private static final Map<String, String> RULES = new TreeMap<String, String>();

	static
	{
		for (Role role : new ImplDetector<>(Role.class).getDetected())
		{
			ROLES.put(role.getClass().getName(), role.getClass().getSimpleName());
		}

		for (PostRoundEventHandler handler : new ImplDetector<>(PostRoundEventHandler.class).getDetected())
		{
			RULES.put(handler.getClass().getName(), handler.getRuleName());
		}
	}

	@RequestMapping(value = "rules", produces = "application/json")
	@ResponseBody
	public Map<String, String> getAvailableRules()
	{
		return RULES;
	}

	@RequestMapping(value = "roles", produces = "application/json")
	@ResponseBody
	public Map<String, String> getAvailableRoles()
	{
		return ROLES;
	}

	// this should return json of the player
	// { "name": "test name" }
	@RequestMapping(value = "sample", produces = "application/json")
	@ResponseBody
	public Player sample(@RequestParam("name") String name)
	{
		return new Player(name);
	}
}
