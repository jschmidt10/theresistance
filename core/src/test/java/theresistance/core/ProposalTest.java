package theresistance.core;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import theresistance.core.Proposal.Vote;

public class ProposalTest
{
	private Proposal proposal;
	private Player[] players;

	@Before
	public void setup()
	{
		proposal = new Proposal(3, 5);
		players = new Player[] { new Player("player1"), new Player("player2"), new Player("player3"),
				new Player("player4"), new Player("player5") };

		proposal.setVote(players[0], Vote.SEND);
		proposal.setVote(players[1], Vote.SEND);
		proposal.setVote(players[2], Vote.SEND);
		proposal.setVote(players[3], Vote.SEND);
		proposal.setVote(players[4], Vote.SEND);
	}

	@Test
	public void testApprovedOnMajorityVote()
	{
		proposal.setVote(players[4], Vote.DONT_SEND);
		Assert.assertTrue(proposal.isApproved());
	}

	@Test
	public void testNotApprovedOnMajorityDownvote()
	{
		proposal.setVote(players[2], Vote.DONT_SEND);
		proposal.setVote(players[3], Vote.DONT_SEND);
		proposal.setVote(players[4], Vote.DONT_SEND);
		Assert.assertFalse(proposal.isApproved());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNumberOfProposedPlayers()
	{
		proposal.setPlayers(new Player("player1"), new Player("player2"));
	}

	@Test
	public void testRetrieveVoteHistory()
	{
		proposal.setVote(players[4], Vote.DONT_SEND);

		Map<Player, Vote> votes = proposal.getVotes();

		Assert.assertEquals(Vote.SEND, votes.get(players[0]));
		Assert.assertEquals(Vote.DONT_SEND, votes.get(players[4]));
	}
}
