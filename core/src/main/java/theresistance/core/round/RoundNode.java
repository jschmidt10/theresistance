package theresistance.core.round;

import theresistance.core.Game;

/**
 * Represents a node in the round processing graph
 */
public abstract class RoundNode
{
	protected Game game;
	
	protected void initialize(Game game) 
	{
		this.game = game;
	}
	
	public abstract RoundNode next();
}
