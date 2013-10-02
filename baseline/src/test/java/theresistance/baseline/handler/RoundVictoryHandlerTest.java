package theresistance.baseline.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import theresistance.baseline.role.LoyalServant;
import theresistance.baseline.role.Minion;
import theresistance.core.Game;
import theresistance.core.Mission;
import theresistance.core.Mission.Result;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.config.GameConfig;

public class RoundVictoryHandlerTest
{
	private Game game;
	private final Player p1 = new Player("p1");
	private final Player p2 = new Player("p2");

	@Before
	public void setup()
	{
		GameConfig config = new GameConfig();
		config.setMissions(new Mission(1, 1), new Mission(1, 1), new Mission(1,
				1));
		config.setPlayers(p1, p2);
		config.setRoles(new LoyalServant(), new Minion());
		config.setHandlers(new RoundVictoryHandler());

		game = config.create();
	}

	@Test
	public void testNeedMajorityOfRoundsToWin()
	{
		Proposal proposal = game.propose(p1);
		Mission mission = game.send(proposal);
		mission.setResults(Result.PASS);
		game.completeRound();

		Assert.assertFalse(game.isOver());

		proposal = game.propose(p1);
		mission = game.send(proposal);
		mission.setResults(Result.PASS);
		game.completeRound();

		Assert.assertTrue(game.isOver());
		Assert.assertTrue(game.getWinners().isGood());
	}
}
