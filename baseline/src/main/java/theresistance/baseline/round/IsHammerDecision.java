package theresistance.baseline.round;

import theresistance.core.round.DecisionNode;

public class IsHammerDecision extends DecisionNode
{
	private final static int LAST_PROPOSAL = 4;
	
	@Override
	public boolean evaluate()
	{
		return game.getCurrentRound().getProposalIndex() == LAST_PROPOSAL;
	}
}
