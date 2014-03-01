package theresistance.server;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import theresistance.baseline.state.AssassinationAction;
import theresistance.baseline.state.AssassinationState;
import theresistance.baseline.state.MissionResultAction;
import theresistance.baseline.state.MissionState;
import theresistance.baseline.state.ProposeAction;
import theresistance.baseline.state.ProposeState;
import theresistance.baseline.state.VoteAction;
import theresistance.baseline.state.VoteState;
import theresistance.core.Game;
import theresistance.core.Mission.Result;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Proposal.Vote;
import theresistance.core.Round;
import theresistance.server.view.GamePlayerView;
import theresistance.server.view.ProposalView;

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

	@RequestMapping(value = "assassinate", produces = "application/json")
	@ResponseBody
	public StatusResponse assassinate(@RequestParam String gameId, @RequestParam String assassinated)
	{
		Game game = registry.getGame(gameId);
		AssassinationState state = game.getState(AssassinationState.class);
		state.progress(game, new AssassinationAction(game.getPlayer(assassinated)));
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
