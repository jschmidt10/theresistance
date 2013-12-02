package theresistance.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import theresistance.core.state.GameState;
import theresistance.core.state.WaitingForMissionResult;
import theresistance.core.state.WaitingForProposal;
import theresistance.core.util.ExtraInfoBag;

/**
 * A game of The Resistance.
 */
public class Game
{
	private String id;
	private final GameConfig config;
	private final List<Round> rounds = new LinkedList<>();
	private List<Player> players = new LinkedList<>();
	private Map<String, Player> playersByName = new TreeMap<>();
	private GameState gameState;

	private final ExtraInfoBag extraInfo = new ExtraInfoBag();

	private boolean isStarted = false;
	private int curRound = 0;
	private int curLeader = 0;
	private Alignment winners = Alignment.NEITHER;

	public Game(GameConfig config)
	{
		this.config = config;
	}

	/**
	 * set id
	 * 
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return id
	 */
	public String getId()
	{
		return id;
	}

	public void gotoNextLeader()
	{
		curLeader = (curLeader + 1) % players.size();
	}

	/**
	 * @return config
	 */
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
		if (playersByName.put(player.getName(), player) == null)
		{
			players.add(player);
		}
	}

	/**
	 * Removes a player from the game.
	 * 
	 * @param player
	 */
	public void removePlayer(Player player)
	{
		if (playersByName.remove(player.getName()) != null)
		{
			players.remove(player);
		}
	}

	/**
	 * initializes the game and prepares for play
	 */
	public void start()
	{
		new RoleAssigner().assign(players, config.getRoles());

		for (Player p : players)
		{
			playersByName.put(p.getName(), p);
		}

		for (Mission mission : config.getMissions())
		{
			rounds.add(new Round(rounds.size(), mission));
		}

		for (PostRoundEventHandler handler : config.getHandlers())
		{
			handler.init(this);
		}

		if (gameState == null)
		{
			gameState = new WaitingForProposal(getCurrentLeader());
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
		if (gameState instanceof WaitingForProposal)
		{
			WaitingForProposal state = (WaitingForProposal) gameState;
			state.setProposal(participants);
			state.advanceGameState(this);
			return getCurrentRound().getLastProposal();
		}
		else
		{
			throw new IllegalStateException("Cannot propose a mission at this time.");
		}
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
		gameState = new WaitingForMissionResult(proposal.getParticipants());
		return curRound;
	}

	/**
	 * progresses the game to the next round and calls the post round event
	 * handlers.
	 */
	public void completeRound()
	{
		GameState current = gameState;
		for (PostRoundEventHandler handler : config.getHandlers())
		{
			handler.roundFinished();
		}
		curRound++;

		// If the post round event handlers haven't changed
		// the game state, the we can advance it by default.
		if (gameState == current)
		{
			gotoNextLeader();
			gameState = new WaitingForProposal(getCurrentLeader());
		}
	}

	/**
	 * @return true if the game is over, false, otherwise
	 */
	public boolean isOver()
	{
		return winners != Alignment.NEITHER;
	}

	/**
	 * @return the winning side
	 */
	public Alignment getWinners()
	{
		return winners;
	}

	/**
	 * set winning team
	 * 
	 * @param winners
	 */
	public void setWinners(Alignment winners)
	{
		this.winners = winners;
	}

	/**
	 * @return extra info
	 */
	public ExtraInfoBag getExtraInfo()
	{
		return extraInfo;
	}

	/**
	 * @return rounds
	 */
	public List<Round> getRounds()
	{
		return rounds;
	}

	/**
	 * @return players
	 */
	public List<Player> getPlayers()
	{
		return players;
	}

	/**
	 * get a player by their name
	 * 
	 * @param name
	 * @return player
	 */
	public Player getPlayer(String name)
	{
		return playersByName.get(name);
	}

	/**
	 * @return number of players
	 */
	public int getNumPlayers()
	{
		return players.size();
	}

	/**
	 * @return current round of play
	 */
	public Round getCurrentRound()
	{
		return rounds.get(curRound);
	}

	/**
	 * @return round handlers
	 */
	public List<PostRoundEventHandler> getPostRoundEventHandlers()
	{
		return config.getHandlers();
	}

	/**
	 * @return true if the game is ready to start, false, otherwise
	 */
	public boolean isReady()
	{
		return players.size() == config.getRoles().size();
	}

	/**
	 * @return current mission leader
	 */
	public Player getCurrentLeader()
	{
		return players.get(curLeader);
	}

	public GameState getGameState()
	{
		return gameState;
	}

	public void setGameState(GameState gameState)
	{
		this.gameState = gameState;
	}

	public Round getRound(int index)
	{
		return rounds.get(index);
	}
}
