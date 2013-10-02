package theresistance.baseline.handler;

import org.junit.Before;

import theresistance.baseline.role.LoyalServant;
import theresistance.baseline.role.Minion;
import theresistance.core.Game;
import theresistance.core.Mission;
import theresistance.core.Player;
import theresistance.core.config.GameConfig;

public class VictoryHandlerTest
{
	private Game game;

	@Before
	public void setup()
	{
		GameConfig config = new GameConfig();
		config.setMissions(new Mission(1, 1), new Mission(1, 1), new Mission(1,
				1));
		config.setPlayers(new Player("p1"), new Player("p2"));
		config.setRoles(new LoyalServant(), new Minion());
		config.setHandlers(new VictoryHandler());

		game = config.create();
	}
}
