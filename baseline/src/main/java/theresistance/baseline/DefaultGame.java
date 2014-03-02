package theresistance.baseline;

import theresistance.core.Game;
import theresistance.core.GameConfig;
import theresistance.core.state.GameState;
import theresistance.core.state.ProposeState;

/**
 * The default game flow. Includes: propose, vote, mission, assassination
 */
public class DefaultGame extends Game
{
	public DefaultGame(GameConfig config)
	{
		super(config);
	}

	@Override
	public GameState<?> getInitialState()
	{
		return new ProposeState(getCurrentLeader(), getCurrentRound().getMission().getNumParticipants());
	}
}
