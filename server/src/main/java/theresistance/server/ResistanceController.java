package theresistance.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.core.Mission;
import theresistance.core.Player;
import theresistance.core.PostRoundEventHandler;
import theresistance.core.Role;
import theresistance.core.cls.ImplDetector;
import theresistance.core.config.GameConfig;
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
	public List<OptionView> getAvailableRules()
	{
		return RULE_NAMES;
	}

	@RequestMapping(value = "roles", produces = "application/json")
	@ResponseBody
	public List<OptionView> getAvailableRoles()
	{
		return ROLE_NAMES;
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

		String id = registry.register(config);

		return StatusResponse.success(id);
	}

	@RequestMapping(value = "newgames", produces = "application/json")
	@ResponseBody
	public Collection<LobbyView> getNewGames()
	{
		List<LobbyView> views = new LinkedList<>();

		for (GameConfig config : registry.getNewGames())
		{
			views.add(new LobbyView(config));
		}

		return views;
	}

	@RequestMapping(value = "players", produces = "application/json")
	@ResponseBody
	public List<PlayerView> getPlayers(@RequestParam String gameId)
	{
		GameConfig config = registry.getGameConfig(gameId);
		List<PlayerView> views = new LinkedList<PlayerView>();

		for (Player player : config.getPlayers())
		{
			views.add(new PlayerView(player));
		}

		return views;
	}

	@RequestMapping(value = "join", produces = "application/json")
	@ResponseBody
	public StatusResponse joinGame(@RequestParam String gameId, @RequestParam String player)
	{
		GameConfig config = registry.getGameConfig(gameId);
		config.addPlayer(new Player(player));

		return StatusResponse.success(gameId);
	}

	private PostRoundEventHandler[] toHandlerConfig(List<String> names)
	{
		PostRoundEventHandler[] handlers = new PostRoundEventHandler[names.size()];

		for (int i = 0; i < handlers.length; ++i)
		{
			handlers[i] = RULES.get(names.get(i));
		}

		return handlers;
	}

	private Mission[] toMissions(List<Integer> numPlayers, List<Integer> numFailures)
	{
		Mission[] missions = new Mission[numPlayers.size()];

		for (int i = 0; i < missions.length; ++i)
		{
			missions[i] = new Mission(numPlayers.get(i), numFailures.get(i));
		}

		return missions;
	}

	private Role[] toRoleConfig(List<String> names)
	{
		Role[] arr = new Role[names.size()];

		for (int i = 0; i < arr.length; ++i)
		{
			arr[i] = ROLES.get(names.get(i));
		}

		return arr;
	}
}
