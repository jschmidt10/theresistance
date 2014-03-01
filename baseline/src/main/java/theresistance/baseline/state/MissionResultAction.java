package theresistance.baseline.state;

import theresistance.core.Mission.Result;
import theresistance.core.state.GameAction;
import theresistance.core.Player;

/**
 * Submitting a mission result card
 */
public class MissionResultAction implements GameAction
{
	private Player player;
	private Result result;

	public MissionResultAction(Player player, Result result)
	{
		this.player = player;
		this.result = result;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Result getResult()
	{
		return result;
	}
}
