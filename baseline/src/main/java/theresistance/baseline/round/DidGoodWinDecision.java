package theresistance.baseline.round;


import theresistance.core.Alignment;

import theresistance.core.round.DecisionNode;
import theresistance.core.round.RoundNode;

public class DidGoodWinDecision extends DecisionNode
{
	public DidGoodWinDecision(RoundNode onTrue, RoundNode onFalse)
	{
		super(onTrue, onFalse);
	}

	@Override
	public boolean evaluate()
	{
		return game.getWinner() == Alignment.GOOD;
	}
}
