package theresistance.server.stub;

import theresistance.core.Game;
import theresistance.core.PostRoundEventHandler;

public class DummyRule1 implements PostRoundEventHandler
{
	@Override
	public String getRuleName()
	{
		return "DummyRule1";
	}

	@Override
	public void init(Game game)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void roundFinished()
	{
		// TODO Auto-generated method stub
	}
}