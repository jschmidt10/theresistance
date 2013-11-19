package theresistance.core;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import theresistance.core.Mission.Result;
import theresistance.core.Proposal.Vote;
import theresistance.core.stub.BadGuy;
import theresistance.core.stub.GoodGuy;

public class GameTest
{
	private Game game;
	private Player[] players;

	@Before
	public void setup()
	{
		players = new Player[] { new Player("p1"), new Player("p2"), new Player("p3"), new Player("p4"),
				new Player("p5"), new Player("p6") };

		GameConfig config = new GameConfig();

		config.setMissions(Arrays.asList(new Mission(2, 1), new Mission(3, 1), new Mission(4, 1),
				new Mission(3, 1), new Mission(4, 1)));
		config.setRoles(Arrays.asList(new GoodGuy(), new GoodGuy(), new GoodGuy(), new GoodGuy(),
				new BadGuy(), new BadGuy()));

		game = new Game(config);

		for (Player player : players)
		{
			game.addPlayer(player);
		}

		game.start();
	}

	@Test
	public void testAllRoundInitialized()
	{
		for (Round round : game.getRounds())
		{
			Assert.assertNotNull(round.getMission());
		}
	}

	@Test
	public void testAllPlayersAreAssignedRoles()
	{
		for (Player player : game.getPlayers())
		{
			Assert.assertNotNull(player.getRole());
		}
	}

	@Test
	public void testCurrentRound()
	{
		Round round = game.getCurrentRound();
		Assert.assertEquals(0, round.getIndex());
	}

	@Test
	public void testSendAMission_thenProgressCurrentRound()
	{
		Proposal proposal = game.propose(Arrays.asList(players[0], players[1]));
		proposal.setVote(players[0], Vote.SEND);
		proposal.setVote(players[1], Vote.SEND);
		proposal.setVote(players[2], Vote.SEND);
		proposal.setVote(players[3], Vote.SEND);
		proposal.setVote(players[4], Vote.SEND);
		proposal.setVote(players[5], Vote.SEND);

		Assert.assertTrue(proposal.isApproved());

		Round round = game.send(proposal);
		round.setResults(Arrays.asList(Result.PASS, Result.PASS));

		game.completeRound();

		Round nextRound = game.getCurrentRound();
		Assert.assertEquals(1, nextRound.getIndex());
	}
}