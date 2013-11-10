package theresistance.core;

/**
 * A handler that is invoked after every round
 */
public interface PostRoundEventHandler
{
	/**
	 * @return Human friendly readable description of this rule
	 */
	public String getRuleName();

	/**
	 * initialize the handler
	 * 
	 * @param game
	 */
	public void init(Game game);

	/**
	 * post round processing
	 */
	public void roundFinished();
}
