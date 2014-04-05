package theresistance.baseline;

import theresistance.baseline.round.IsHammerDecision;
import theresistance.baseline.round.IsProposalSentDecision;
import theresistance.baseline.round.MissionInteractionNode;
import theresistance.baseline.round.ProposalInteractionNode;
import theresistance.baseline.round.VoteInteractionNode;
import theresistance.core.Game;
import theresistance.core.round.DecisionNode;
import theresistance.core.round.EndNode;
import theresistance.core.round.StartNode;

public class DefaultGame extends Game
{
	@Override
	public StartNode getGameGraph()
	{
		// states
		StartNode start = new StartNode();
		ProposalInteractionNode proposal = new ProposalInteractionNode();
		MissionInteractionNode mission = new MissionInteractionNode();
		VoteInteractionNode vote = new VoteInteractionNode();
		EndNode end = new EndNode();

		// decisions
		DecisionNode isHammer = new IsHammerDecision().onTrue(mission).onFalse(vote);
		DecisionNode isMissionGoing = new IsProposalSentDecision().onTrue(mission).onFalse(proposal);

		// wiring states together
		start.onNext(proposal);
		proposal.onNext(isHammer);
		vote.onNext(isMissionGoing);
		mission.onNext(end);

		return start;
	}
}
