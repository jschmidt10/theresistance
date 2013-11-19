package theresistance.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import theresistance.core.cls.ImplDetector;
import theresistance.server.view.LobbyView;
import theresistance.server.view.OptionView;
import theresistance.server.view.PlayerView;

/**
 * Controller for all game based interactions
 */
@Controller
public class ResistanceController
{
	private static final List<OptionView> ROLE_NAMES = new LinkedList<>();
	private static final Map<String, Role> ROLES = new TreeMap<>();
	private static final List<OptionView> RULE_NAMES = new LinkedList<>();
	private static final Map<String, PostRoundEventHandler> RULES = new TreeMap<>();

	static
	{
		for (Role role : new ImplDetector<>(Role.class).getDetected())
		{
			String name = role.getClass().getName();
			ROLE_NAMES.add(new OptionView(name, role.getClass().getSimpleName()));
			ROLES.put(name, role);
		}

		for (PostRoundEventHandler handler : new ImplDetector<>(PostRoundEventHandler.class).getDetected())
		{
			String name = handler.getClass().getName();
			RULE_NAMES.add(new OptionView(name, handler.getRuleName()));
			RULES.put(name, handler);
		}
	}

	private GameRegistry registry = new GameRegistry();

	@RequestMapping(value = "rules", produces = "application/json")
	@ResponseBody
	public StatusResponse getAvailableRules()
	{
		return StatusResponse.success(null, RULE_NAMES);
	}

	@RequestMapping(value = "roles", produces = "application/json")
	@ResponseBody
	public StatusResponse getAvailableRoles()
	{
		return StatusResponse.success(null, ROLE_NAMES);
	}

	@RequestMapping(value = "newgame", produces = "application/json")
	@ResponseBody
	public StatusResponse createNewGame(@RequestParam("owner") String owner,
			@RequestParam("role") List<String> roles, @RequestParam("numPlayers") List<Integer> numPlayers,
			@RequestParam("numFailures") List<Integer> numFailures, @RequestParam("rule") List<String> rules)
	{
		GameConfig config = new GameConfig();

		config.setOwner(owner);
		config.setRoles(toRoleConfig(roles));
		config.setMissions(toMissions(numPlayers, numFailures));
		config.setHandlers(toHandlerConfig(rules));

		String id = registry.register(new Game(config));

		return StatusResponse.success(id, null);
	}

	@RequestMapping(value = "newgames", produces = "application/json")
	@ResponseBody
	public StatusResponse getNewGames()
	{
		List<LobbyView> views = new LinkedList<>();

		for (Game game : registry.getNewGames())
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
		List<PlayerView> views = new LinkedList<PlayerView>();

		for (Player player : game.getPlayers())
		{
			views.add(new PlayerView(player));
		}

		return StatusResponse.success(gameId, views);
	}

	@RequestMapping(value = "join", produces = "application/json")
	@ResponseBody
	public StatusResponse joinGame(@RequestParam String gameId, @RequestParam String player)
	{
		Game game = registry.getGame(gameId);
		game.addPlayer(new Player(player));

		return StatusResponse.success(gameId, null);
	}

	private List<PostRoundEventHandler> toHandlerConfig(List<String> names)
	{
		List<PostRoundEventHandler> handlers = new LinkedList<>();

		for (String name : names)
		{
			handlers.add(RULES.get(name));
		}

		return handlers;
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

	private List<Role> toRoleConfig(List<String> names)
	{
		List<Role> roles = new LinkedList<>();

		for (String name : names)
		{
			roles.add(ROLES.get(name));
		}

		return roles;
	}
}
