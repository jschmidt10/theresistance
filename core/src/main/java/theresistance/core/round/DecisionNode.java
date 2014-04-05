package theresistance.core.round;


/**
 * Represents a decision state in the round
 */
abstract public class DecisionNode extends RoundNode
{
	private RoundNode onTrue;
	private RoundNode onFalse;

	public DecisionNode onTrue(RoundNode onTrue) {
		this.onTrue = onTrue;
		return this;
	}

	public DecisionNode onFalse(RoundNode onFalse) {
		this.onFalse = onFalse;
		return this;
	}
	
	@Override
	public RoundNode next()
	{
		return evaluate() ? onTrue : onFalse;
	}
	
	abstract public boolean evaluate();
}
