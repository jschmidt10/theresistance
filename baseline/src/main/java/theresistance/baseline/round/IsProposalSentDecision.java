package theresistance.baseline.round;

import theresistance.core.Proposal;
import theresistance.core.round.DecisionNode;
import theresistance.core.round.RoundNode;

public class IsProposalSentDecision extends DecisionNode
{
	public IsProposalSentDecision(RoundNode onTrue, RoundNode onFalse)
	{
		super(onTrue, onFalse);
	}

	@Override
	public boolean evaluate()
	{
		Proposal proposal = game.getCurrentRound().getLastProposal();
		return proposal.isApproved();
	}
}
