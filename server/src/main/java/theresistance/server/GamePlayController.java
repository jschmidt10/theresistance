package theresistance.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.Round;
import theresistance.server.view.GamePlayerView;

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
		return StatusResponse.success(gameId, game.getGameState());
	}

	@RequestMapping(value = "proposals", produces = "application/json")
	@ResponseBody
	public StatusResponse getProposals(@RequestParam String gameId, @RequestParam("round") int index)
	{
		Game game = registry.getGame(gameId);
		Round round = game.getRound(index);

		return StatusResponse.success(gameId, round.getProposals());
	}

	@RequestMapping(value = "results", produces = "application/json")
	@ResponseBody
	public StatusResponse getResults(@RequestParam String gameId, @RequestParam("round") int index)
	{
		Game game = registry.getGame(gameId);
		Round round = game.getRound(index);

		return StatusResponse.success(gameId, round.getResults());
	}
}
