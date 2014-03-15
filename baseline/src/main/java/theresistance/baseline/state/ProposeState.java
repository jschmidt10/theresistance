package theresistance.baseline.state;

import java.util.List;

import theresistance.core.Game;
import theresistance.core.Player;
import theresistance.core.Proposal;
import theresistance.core.Round;
import theresistance.core.state.GameState;

/**
 * The proposal portion of a game
 */
public class ProposeState extends GameState<ProposeAction>
{
	private final Player leader;
	private int numberOfParticipants;
	private List<Player> participants;
	private boolean isHammer;

	public ProposeState(Player leader, int numberOfParticipants, boolean isHammer)
	{
		this.leader = leader;
		this.numberOfParticipants = numberOfParticipants;
		this.isHammer = isHammer;
	}
	
	@Override
	public Class<ProposeAction> getGameActionClass() 
	{
		return ProposeAction.class;
	}
	
	@Override
	public void act(ProposeAction action)
	{
		this.participants = action.getPlayers();
	}

	@Override
	public boolean isFinished()
	{
		return participants != null;
	}
	
	public String getLeader()
	{
		return leader.getName();
	}
	
	public int getNumberOfParticipants()
	{
		return numberOfParticipants;
	}
	
	public boolean isHammer() 
	{
		return isHammer; 
	}
	
	@Override
	public void advance(Game game)
	{
		Round round = game.getCurrentRound();
		Proposal proposal = new Proposal(leader, game.getNumPlayers());
		proposal.setParticipants(participants);
		round.addProposal(proposal);

		// check hammer
		if (isHammer)
		{
			round.setParticipants(proposal.getParticipants());
			game.setState(new MissionState(proposal.getParticipants(), leader));
		}
		else
		{
			game.setState(new VoteState(game.getPlayers(), proposal));
		}
	}
}
