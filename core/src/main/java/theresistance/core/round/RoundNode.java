package theresistance.core.round;

import theresistance.core.Game;

/**
 * Represents a node in the round processing graph
 */
public interface RoundNode
{
	RoundNode next(Game game);
}
