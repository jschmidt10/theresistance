package theresistance.server;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.core.Game;
import theresistance.core.Mission.Result;
import theresistance.core.Player;
import theresistance.core.Proposal.Vote;
import theresistance.core.Round;
import theresistance.core.state.MissionResultAction;
import theresistance.core.state.MissionState;
import theresistance.core.state.ProposeAction;
import theresistance.core.state.ProposeState;
import theresistance.core.state.VoteAction;
import theresistance.core.state.VoteState;
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
		return StatusResponse.success(gameId, game.getState());
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

	@RequestMapping(value = "propose", produces = "application/json")
	@ResponseBody
	public StatusResponse propose(@RequestParam String gameId, @RequestParam("players") List<String> players)
	{
		Game game = registry.getGame(gameId);
		ProposeState state = game.getState(ProposeState.class);
		state.progress(game, new ProposeAction(toPlayers(game, players)));

		return StatusResponse.success(gameId, null);
	}

	@RequestMapping(value = "vote", produces = "application/json")
	@ResponseBody
	public StatusResponse vote(@RequestParam String gameId, @RequestParam String player,
			@RequestParam String vote)
	{
		Game game = registry.getGame(gameId);
		VoteState state = game.getState(VoteState.class);
		state.progress(game, new VoteAction(game.getPlayer(player), Vote.valueOf(vote)));

		return StatusResponse.success(gameId, null);
	}

	@RequestMapping(value = "mission", produces = "application/json")
	@ResponseBody
	public StatusResponse goOnMission(@RequestParam String gameId, @RequestParam String player,
			@RequestParam String result)
	{
		Game game = registry.getGame(gameId);
		MissionState state = game.getState(MissionState.class);
		state.progress(game, new MissionResultAction(game.getPlayer(player), Result.valueOf(result)));

		return StatusResponse.success(gameId, null);
	}

	private List<Player> toPlayers(Game game, List<String> players)
	{
		List<Player> results = new LinkedList<>();

		for (String name : players)
		{
			results.add(game.getPlayer(name));
		}

		return results;
	}
}
