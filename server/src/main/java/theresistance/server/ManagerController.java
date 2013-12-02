package theresistance.server;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.core.Game;
import theresistance.core.GameConfig;
import theresistance.core.Mission;
import theresistance.core.Player;
import theresistance.core.PostRoundEventHandler;
import theresistance.core.Role;
import theresistance.server.conf.DynamicGameOptions;
import theresistance.server.conf.RoleFormatter;
import theresistance.server.conf.RuleFormatter;
import theresistance.server.view.LobbyView;
import theresistance.server.view.PlayerView;

/**
 * Controller for configuration end points
 */
@Controller
public class ManagerController
{
	private final DynamicGameOptions<Role> roleOpts = new DynamicGameOptions<>(Role.class,
			new RoleFormatter());
	private final DynamicGameOptions<PostRoundEventHandler> ruleOpts = new DynamicGameOptions<>(
			PostRoundEventHandler.class, new RuleFormatter());

	private final GameRegistry registry;

	@Autowired
	public ManagerController(GameRegistry registry)
	{
		this.registry = registry;
	}

	@RequestMapping(value = "rules", produces = "application/json")
	@ResponseBody
	public StatusResponse getAvailableRules()
	{
		return StatusResponse.success(null, ruleOpts.getDescriptions());
	}

	@RequestMapping(value = "roles", produces = "application/json")
	@ResponseBody
	public StatusResponse getAvailableRoles()
	{
		return StatusResponse.success(null, roleOpts.getDescriptions());
	}

	@RequestMapping(value = "newgame", produces = "application/json")
	@ResponseBody
	public StatusResponse createNewGame(@RequestParam("owner") String owner,
			@RequestParam("role") List<String> roles, @RequestParam("numPlayers") List<Integer> numPlayers,
			@RequestParam("numFailures") List<Integer> numFailures, @RequestParam("rule") List<String> rules)
	{
		GameConfig config = new GameConfig();

		config.setOwner(owner);
		config.setRoles(roleOpts.getOptions(roles));
		config.setMissions(toMissions(numPlayers, numFailures));
		config.setHandlers(ruleOpts.getOptions(rules));

		String id = registry.register(new Game(config));

		return StatusResponse.success(id, null);
	}

	@RequestMapping(value = "delete", produces = "application/json")
	@ResponseBody
	public StatusResponse deleteGame(@RequestParam String gameId)
	{
		Game game = registry.getGame(gameId);
		if (game != null)
		{
			registry.unregister(game);
		}
		return StatusResponse.success(gameId, null);
	}

	@RequestMapping(value = "games", produces = "application/json")
	@ResponseBody
	public StatusResponse getNewGames()
	{
		List<LobbyView> views = new LinkedList<>();

		for (Game game : registry.getGames())
		{
			views.add(new LobbyView(game));
		}

		return StatusResponse.success(null, views);
	}

	@RequestMapping(value = "players", produces = "application/json")
	@ResponseBody
	public StatusResponse getPlayers(@RequestParam String gameId)
	{
		Game game = registry.getGame(gameId);
		if (game == null)
		{
			return StatusResponse.failure("Game doesn't exist", gameId);
		}

		List<PlayerView> views = new LinkedList<PlayerView>();

		for (Player player : game.getPlayers())
		{
			views.add(new PlayerView(player));
		}

		return StatusResponse.success(gameId, views);
	}

	private List<Mission> toMissions(List<Integer> numPlayers, List<Integer> numFailures)
	{
		List<Mission> missions = new LinkedList<>();

		for (int i = 0; i < numPlayers.size(); ++i)
		{
			missions.add(new Mission(numPlayers.get(i), numFailures.get(i)));
		}

		return missions;
	}
}
