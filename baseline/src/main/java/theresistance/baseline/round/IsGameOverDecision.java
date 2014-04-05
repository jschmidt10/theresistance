package theresistance.baseline.round;

import theresistance.core.round.DecisionNode;
import theresistance.core.round.RoundNode;

public class IsGameOverDecision extends DecisionNode
{
	public IsGameOverDecision(RoundNode onTrue, RoundNode onFalse)
	{
		super(onTrue, onFalse);
	}

	@Override
	public boolean evaluate()
	{
		return game.isOver();
	}
}
