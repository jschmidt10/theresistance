package theresistance.baseline.handler;

import java.util.Collections;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import theresistance.baseline.role.Assassin;
import theresistance.baseline.role.Merlin;
import theresistance.core.Game;
import theresistance.core.Mission;
import theresistance.core.Mission.Result;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.config.GameConfig;

public class AssassinVictoryHandlerTest
{
	Game game;
	private final Player p1 = new Player("p1");
	private final Player p2 = new Player("p2");

	@Before
	public void setup()
	{
		GameConfig config = new GameConfig();
		config.setHandlers(new AssassinVictoryHandler());
		config.setMissions(new Mission(1, 1), new Mission(1, 1), new Mission(1, 1));
		config.setRoles(new Merlin(), new Assassin());
		config.addPlayer(p1);
		config.addPlayer(p2);
		game = config.create();
	}

	@Test
	public void testNotEndOfGame()
	{
		Proposal proposal = game.propose(p1);
		Mission mission = game.send(proposal);
		mission.setResults(Collections.singletonList(Result.PASS));
		game.completeRound();

		Assert.assertFalse(game.isOver());
		Assert.assertFalse(AssassinVictoryHandler.isGameWaitingForAssassination(game));
	}

	@Test
	public void testEndOfGameEvilWinOnRounds()
	{
		Proposal proposal = game.propose(p1);
		Mission mission = game.send(proposal);
		mission.setResults(Collections.singletonList(Result.FAIL));
		game.completeRound();

		Assert.assertFalse(game.isOver());
		Assert.assertFalse(AssassinVictoryHandler.isGameWaitingForAssassination(game));

		proposal = game.propose(p1);
		mission = game.send(proposal);
		mission.setResults(Collections.singletonList(Result.FAIL));
		game.completeRound();

		Assert.assertTrue(game.isOver());
		Assert.assertFalse(AssassinVictoryHandler.isGameWaitingForAssassination(game));
		Assert.assertTrue(game.getWinners().isEvil());
	}

	@Test
	public void testEndOfGameMisAssassination()
	{
		passFirstTwoRounds();
		Player assassin = (p1.getRole() instanceof Merlin ? p2 : p1);

		AssassinVictoryHandler.setWinnerBasedOnAssassination(game, assassin);

		Assert.assertTrue(game.isOver());
		Assert.assertFalse(AssassinVictoryHandler.isGameWaitingForAssassination(game));
		Assert.assertTrue(game.getWinners().isGood());
	}

	@Test
	public void testEndOfGameMerlinAssassinated()
	{
		passFirstTwoRounds();
		Player merlin = (p1.getRole() instanceof Merlin ? p1 : p2);

		AssassinVictoryHandler.setWinnerBasedOnAssassination(game, merlin);

		Assert.assertTrue(game.isOver());
		Assert.assertFalse(AssassinVictoryHandler.isGameWaitingForAssassination(game));
		Assert.assertTrue(game.getWinners().isEvil());
	}

	protected void passFirstTwoRounds()
	{
		Proposal proposal = game.propose(p1);
		Mission mission = game.send(proposal);
		mission.setResults(Collections.singletonList(Result.PASS));
		game.completeRound();

		Assert.assertFalse(game.isOver());
		Assert.assertFalse(AssassinVictoryHandler.isGameWaitingForAssassination(game));

		proposal = game.propose(p1);
		mission = game.send(proposal);
		mission.setResults(Collections.singletonList(Result.PASS));
		game.completeRound();

		Assert.assertFalse(game.isOver());
		Assert.assertTrue(AssassinVictoryHandler.isGameWaitingForAssassination(game));
	}
}
