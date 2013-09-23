package theresistance.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.core.Player;

@Controller
public class ResistanceController
{
	// this should return json of the player
	// { "name": "test name" }
	@RequestMapping(value = "sample", produces = "application/json")
	@ResponseBody
	public Player sample(@RequestParam("name") String name)
	{
		return new Player(name);
	}
}
