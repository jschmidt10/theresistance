package theresistance.core.round;


/**
 * Represents a decision state in the round
 */
abstract public class DecisionNode extends RoundNode
{
	private RoundNode onTrue;
	private RoundNode onFalse;
	
	public DecisionNode(RoundNode onTrue, RoundNode onFalse)
	{
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}
	
	@Override
	public RoundNode next()
	{
		return evaluate() ? onTrue : onFalse;
	}
	
	abstract public boolean evaluate();
}
