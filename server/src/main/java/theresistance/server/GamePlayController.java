package theresistance.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Round;
import theresistance.core.state.GameAction;
import theresistance.core.state.GameState;
import theresistance.server.view.GamePlayerView;
import theresistance.server.view.ProposalView;
import theresistance.server.view.ResultsView;

/**
 * Controller for game play end points
 */
@Controller
public class GamePlayController
{
	private GameRegistry registry;

	@Autowired
	public GamePlayController(GameRegistry registry)
	{
		this.registry = registry;
	}

	@RequestMapping(value = "join", produces = "application/json")
	@ResponseBody
	public StatusResponse joinGame(@RequestParam String gameId, @RequestParam String player)
	{
		Game game = registry.getGame(gameId);
		game.addPlayer(new Player(player));

		if (game.isReady())
		{
			game.start();
		}

		return StatusResponse.success(gameId, null);
	}

	@RequestMapping(value = "leave", produces = "application/json")
	@ResponseBody
	public StatusResponse leaveGame(@RequestParam String gameId, @RequestParam String player)
	{
		Game game = registry.getGame(gameId);
		Player thisPlayer = game.getPlayer(player);
		game.removePlayer(thisPlayer);
		return StatusResponse.success(gameId, null);
	}

	@RequestMapping(value = "config", produces = "application/json")
	@ResponseBody
	public StatusResponse getConfig(@RequestParam String gameId)
	{
		Game game = registry.getGame(gameId);
		return StatusResponse.success(gameId, game.getConfig());
	}

	@RequestMapping(value = "gamePlayers", produces = "application/json")
	@ResponseBody
	public StatusResponse getGamePlayers(@RequestParam String gameId, @RequestParam String player)
	{
		Game game = registry.getGame(gameId);
		return StatusResponse.success(gameId, new GamePlayerView(game, player));
	}

	@RequestMapping(value = "gameState", produces = "application/json")
	@ResponseBody
	public StatusResponse getGameState(@RequestParam String gameId)
	{
		Game game = registry.getGame(gameId);
		return StatusResponse.success(gameId, game.getState());
	}

	@RequestMapping(value = "proposals", produces = "application/json")
	@ResponseBody
	public StatusResponse getProposals(@RequestParam String gameId, @RequestParam("round") int index)
			throws Exception
	{
		Game game = registry.getGame(gameId);
		Round round = game.getRound(index);

		return StatusResponse.success(gameId, getProposalViews(round.getProposals(), round));
	}

	private List<ProposalView> getProposalViews(List<Proposal> proposals, Round round)
	{
		List<ProposalView> views = new LinkedList<>();

		for (Proposal proposal : proposals)
		{
			views.add(new ProposalView(proposal, round));
		}

		return views;
	}

	@RequestMapping(value = "rounds", produces = "application/json")
	@ResponseBody
	public StatusResponse getRounds(@RequestParam String gameId)
	{
		Game game = registry.getGame(gameId);
		List<ResultsView> results = new ArrayList<ResultsView>(5);

		for (Round round : game.getRounds())
		{
			if (round.isFinished())
			{
				results.add(new ResultsView(round.getIndex(), round.getNumFails(), round.getResult()
						.toString()));
			}
		}

		return StatusResponse.success(gameId, results);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "action", produces = "application/json")
	@ResponseBody
	public <A extends GameAction> StatusResponse onAction(@RequestParam String gameId, @RequestParam String action) throws JsonParseException, JsonMappingException, IOException
	{
		Game game = registry.getGame(gameId);
		GameState<A> state = (GameState<A>) game.getState();
		ObjectMapper mapper = new ObjectMapper();
		A gameAction = mapper.readValue(action, state.getGameActionClass());
		state.progress(game, gameAction);
		return StatusResponse.success(gameId, null);
	}
}
