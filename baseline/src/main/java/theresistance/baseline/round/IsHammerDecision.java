package theresistance.baseline.round;

import theresistance.core.round.DecisionNode;
import theresistance.core.round.RoundNode;

public class IsHammerDecision extends DecisionNode
{
	private final static int LAST_PROPOSAL = 4;
	
	public IsHammerDecision(RoundNode onTrue, RoundNode onFalse)
	{
		super(onTrue, onFalse);
	}
	
	@Override
	public boolean evaluate()
	{
		return game.getCurrentRound().getProposalIndex() == LAST_PROPOSAL;
	}
}
