package theresistance.core.state;

import java.util.List;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Round;

/**
 * The proposal portion of a game
 */
public class ProposeState extends GameState
{
	private Player leader;
	private List<Player> participants;

	public ProposeState(Player player)
	{
		this.leader = player;
	}

	public String getLeader()
	{
		return leader.getName();
	}

	@Override
	public boolean isFinished()
	{
		return participants != null;
	}

	public void setProposal(List<Player> participants)
	{
		this.participants = participants;
	}

	@Override
	public void advance(Game game)
	{
		Round round = game.getCurrentRound();
		Proposal proposal = new Proposal(game.getNumPlayers());
		proposal.setParticipants(participants);
		proposal.setLeader(game.getCurrentLeader());
		round.addProposal(proposal);

		// check hammer
		if (round.getProposalIndex() == 5)
		{
			game.send(proposal);
		}
		else
		{
			game.setGameState(new WaitingForProposalVotes(game.getPlayers(), proposal));
		}
	}
}
