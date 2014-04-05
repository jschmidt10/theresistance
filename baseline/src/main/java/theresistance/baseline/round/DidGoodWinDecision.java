package theresistance.baseline.round;


import theresistance.core.Alignment;
import theresistance.core.round.DecisionNode;

public class DidGoodWinDecision extends DecisionNode
{
	@Override
	public boolean evaluate()
	{
		return game.getWinner() == Alignment.GOOD;
	}
}
