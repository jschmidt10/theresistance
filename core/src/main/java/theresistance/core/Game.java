package theresistance.core;

import java.util.LinkedList;
import java.util.List;

import theresistance.core.util.ExtraInfoBag;

/**
 * A game of The Resistance.
 */
public class Game
{
	private String id;
	private final GameConfig config;
	private final List<Round> rounds = new LinkedList<>();
	private final List<Player> players = new LinkedList<>();

	private ExtraInfoBag extraInfo = new ExtraInfoBag();

	private boolean isStarted = false;
	private int curRound = 0;
	private Alignment winners = Alignment.NEITHER;

	public Game(GameConfig config)
	{
		this.config = config;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public GameConfig getConfig()
	{
		return config;
	}

	/**
	 * Adds a new player to the game
	 * 
	 * @param player
	 */
	public void addPlayer(Player player)
	{
		players.add(player);
	}

	/**
	 * initializes the game and prepares for play
	 */
	public void start()
	{
		new RoleAssigner().assign(players, config.getRoles());

		for (Mission mission : config.getMissions())
		{
			rounds.add(new Round(rounds.size(), mission));
		}

		for (PostRoundEventHandler handler : config.getHandlers())
		{
			handler.init(this);
		}

		isStarted = true;
	}

	/**
	 * @return true if the game has already started, false, otherwise
	 */
	public boolean isStarted()
	{
		return isStarted;
	}

	/**
	 * make the next proposal
	 * 
	 * @param participants
	 * @return proposal
	 */
	public Proposal propose(List<Player> participants)
	{
		Proposal proposal = new Proposal(getNumPlayers());
		proposal.setParticipants(participants);

		getCurrentRound().addProposal(proposal);

		return proposal;
	}

	/**
	 * sends the proposal on the mission
	 * 
	 * @param proposal
	 * @return mission
	 */
	public Round send(Proposal proposal)
	{
		Round curRound = getCurrentRound();
		curRound.setParticipants(proposal.getParticipants());
		return curRound;
	}

	/**
	 * progresses the game to the next round and calls the post round event
	 * handlers.
	 */
	public void completeRound()
	{
		for (PostRoundEventHandler handler : config.getHandlers())
		{
			handler.roundFinished();
		}

		curRound++;
	}

	public boolean isOver()
	{
		return winners != Alignment.NEITHER;
	}

	public Alignment getWinners()
	{
		return winners;
	}

	public void setWinners(Alignment winners)
	{
		this.winners = winners;
	}

	public ExtraInfoBag getExtraInfo()
	{
		return extraInfo;
	}

	public void setExtraInfo(ExtraInfoBag extraInfo)
	{
		this.extraInfo = extraInfo;
	}

	public List<Round> getRounds()
	{
		return rounds;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public int getNumPlayers()
	{
		return players.size();
	}

	public Round getCurrentRound()
	{
		return rounds.get(curRound);
	}

	public List<PostRoundEventHandler> getPostRoundEventHandlers()
	{
		return config.getHandlers();
	}
}
