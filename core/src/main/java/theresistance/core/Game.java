package theresistance.core;

import java.util.Map;

import theresistance.core.config.GameConfig;
import theresistance.core.util.Arguments;

/**
 * A game of The Resistance. Use {@link GameConfig#create()} to create a new
 * game.
 */
public class Game
{
	private final Round[] rounds;
	private final Player[] players;
	private Map<String, Object> extraInfo;
	private final PostRoundEventHandler[] handlers;

	int curRound = 0;

	public Game(Player[] players, Round[] rounds,
			PostRoundEventHandler[] handlers)
	{
		this.players = players;
		this.rounds = rounds;
		this.handlers = handlers;
	}

	/**
	 * make the next proposal
	 * 
	 * @param participants
	 * @return proposal
	 */
	public Proposal propose(Player... participants)
	{
		Arguments.verifyCount(getCurrentRound().getMission()
				.getNumParticipants(), participants.length);
		Proposal proposal = new Proposal(this.players.length);
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
	public Mission send(Proposal proposal)
	{
		Mission mission = getCurrentRound().getMission();
		mission.setParticipants(proposal.getParticipants());
		return mission;
	}

	/**
	 * progresses the game to the next round and calls the post round event
	 * handlers.
	 */
	public void completeRound()
	{
		for (PostRoundEventHandler handler : handlers)
		{
			handler.roundFinished();
		}
		curRound++;
	}

	public Map<String, Object> getExtraInfo()
	{
		return extraInfo;
	}

	public void setExtraInfo(Map<String, Object> extraInfo)
	{
		this.extraInfo = extraInfo;
	}

	public Round[] getRounds()
	{
		return rounds;
	}

	public Player[] getPlayers()
	{
		return players;
	}

	public int getNumPlayers()
	{
		return players.length;
	}

	public Round getCurrentRound()
	{
		return rounds[curRound];
	}
}