package theresistance.core.state;

import theresistance.core.Mission.Result;
import theresistance.core.Player;

/**
 * Submitting a mission result card
 */
public class MissionResultAction implements GameAction
{
	private String player;
	private Result result;

	public String getPlayer()
	{
		return player;
	}
	public void setPlayer(String player)
	{
		this.player = player;
	}

	public void setResult(String result)
	{
		this.result = Result.valueOf(result);
	}
	public Result getResult()
	{
		return result;
	}
}
