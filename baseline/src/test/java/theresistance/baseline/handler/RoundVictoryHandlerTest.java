package theresistance.baseline.handler;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import theresistance.baseline.role.LoyalServant;
import theresistance.baseline.role.Minion;
import theresistance.core.Game;
import theresistance.core.GameConfig;
import theresistance.core.Mission;
import theresistance.core.Mission.Result;
import theresistance.core.Player;
import theresistance.core.PostRoundEventHandler;
import theresistance.core.Proposal;
import theresistance.core.Round;

public class RoundVictoryHandlerTest
{
	private Game game;
	private final Player p1 = new Player("p1");
	private final Player p2 = new Player("p2");

	@Before
	public void setup()
	{
		GameConfig config = new GameConfig();
		config.setMissions(Arrays.asList(new Mission(1, 1), new Mission(1, 1), new Mission(1, 1)));
		config.setRoles(Arrays.asList(new LoyalServant(), new Minion()));
		config.setHandlers(Collections.<PostRoundEventHandler> singletonList(new RoundVictoryHandler()));

		game = new Game(config);

		game.addPlayer(p1);
		game.addPlayer(p2);

		game.start();
	}

	@Test
	public void testNeedMajorityOfRoundsToWin()
	{
		Proposal proposal = game.propose(Arrays.asList(p1));
		Round round = game.send(proposal);
		round.setResults(Collections.singletonList(Result.PASS));
		game.completeRound();

		Assert.assertFalse(game.isOver());

		proposal = game.propose(Arrays.asList(p1));
		round = game.send(proposal);
		round.setResults(Collections.singletonList(Result.PASS));
		game.completeRound();

		Assert.assertTrue(game.isOver());
		Assert.assertTrue(game.getWinners().isGood());
	}
}
