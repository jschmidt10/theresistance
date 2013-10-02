package theresistance.baseline.handler;

import theresistance.baseline.role.Assassin;
import theresistance.baseline.role.Merlin;
import theresistance.core.Alignment;
import theresistance.core.Game;
import theresistance.core.Player;

/**
 * A post round victory handler that checks to see
 * if one of the sides have won on rounds or not. If the evil
 * side has won on rounds, then evil wins. If the good side
 * does, then the {@link AssassinVictoryHandlerTest#WAITING_FOR_ASSASSINATION}
 * property is set to true in the game's extra info. 
 */
public class AssassinVictoryHandler extends RoundVictoryHandler
{
	/**
	 * Game extra info property indicating that an 
	 * the assassin should try to assassinate Merlin.
	 */
	public static final String WAITING_FOR_ASSASSINATION = "waiting.for.assassination";
	
	/**
	 * The class of the role to be assassinated.
	 */
	public static final Class<?> ROLE_TO_ASSASSINATE = Merlin.class;

	/**
	 * The class of the role that does the assassinating.
	 */
	public static final Class<?> ASSASSIN = Assassin.class;
	
	Game game;
	
	@Override
	public void init(Game game)
	{
		super.init(game);
		this.game = game;
		game.getExtraInfo().put(WAITING_FOR_ASSASSINATION, false);
	}
	
	@Override
	public void roundFinished()
	{
		Alignment winner = getWinner();
		if (winner != Alignment.NEITHER)
		{
			if (winner == Alignment.EVIL)
			{
				game.setWinners(winner);
			}
			else
			{
				game.getExtraInfo().put(WAITING_FOR_ASSASSINATION, true);
			}
		}
	}
	
	/**
	 * Sets the winner of the game based on the player that was assassinated.
	 * 
	 * @param game
	 * 		Game to set the winner of.
	 * @param assassinated
	 * 		The player that was assassinated.
	 */
	public static void setWinnerBasedOnAssassination(Game game, Player assassinated)
	{
		if (assassinated.getRole().getClass().equals(ROLE_TO_ASSASSINATE))
		{
			game.setWinners(Alignment.EVIL);
		}
		else
		{
			game.setWinners(Alignment.GOOD);
		}
		game.getExtraInfo().put(WAITING_FOR_ASSASSINATION, false);
	}
	
	public static boolean isGameWaitingForAssassination(Game game)
	{
		return game.getExtraInfo().getBoolean(WAITING_FOR_ASSASSINATION, false);
	}
}
