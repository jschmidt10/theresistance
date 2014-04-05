package theresistance.baseline.round;

import theresistance.core.round.DecisionNode;

public class IsGameOverDecision extends DecisionNode
{
	@Override
	public boolean evaluate()
	{
		return game.isOver();
	}
}
