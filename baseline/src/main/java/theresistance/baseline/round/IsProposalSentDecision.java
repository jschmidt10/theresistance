package theresistance.baseline.round;

import theresistance.core.Proposal;
import theresistance.core.round.DecisionNode;

public class IsProposalSentDecision extends DecisionNode
{
	@Override
	public boolean evaluate()
	{
		Proposal proposal = game.getCurrentRound().getLastProposal();
		return proposal.isApproved();
	}
}
