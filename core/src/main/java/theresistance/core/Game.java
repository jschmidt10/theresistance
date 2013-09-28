package theresistance.core;

import java.util.List;
import java.util.Map;

import theresistance.core.cls.ImplDetector;
import theresistance.core.config.GameConfig;
import theresistance.core.config.RoleAssigner;
import theresistance.core.util.Arguments;

public class Game
{
	private Round[] rounds;
	private Player[] players;
	private Map<String, Object> extraInfo;
	private List<PostRoundEventHandler> postRoundEventHandlers;

	int curRound = 0;

	/**
	 * initialize this game to start playing
	 * 
	 * @param config
	 */
	public void init(GameConfig config)
	{
		initRounds(config.getMissions());
		new RoleAssigner().assign(config.getPlayers(), config.getRoles());
		
		this.players = config.getPlayers();
		
		ImplDetector<PostRoundEventHandler> postRoundEventHandlerDetector = new ImplDetector<>(PostRoundEventHandler.class);
		this.postRoundEventHandlers = postRoundEventHandlerDetector.getDetected();
		for (PostRoundEventHandler handler : postRoundEventHandlers) {
			handler.init(this);
		}
	}

	private void initRounds(Mission[] missions)
	{
		rounds = new Round[missions.length];

		for (int i = 0; i < missions.length; ++i)
		{
			rounds[i] = new Round(i, missions[i]);
		}
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

	/**
	 * make the next proposal
	 * 
	 * @param participants
	 * @return proposal
	 */
	public Proposal propose(Player... participants)
	{
		Arguments.verifyCount(getCurrentRound().getMission().getNumParticipants(), participants.length);
		Proposal proposal = new Proposal(this.players.length);
		proposal.setParticipants(participants);

		getCurrentRound().addProposal(proposal);

		return proposal;
	}

	/**
	 * sends the last proposal on the mission
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
	 * progresses the game to the next round 
	 * and calls the post round event handlers.
	 */
	public void completeRound()
	{
		for (PostRoundEventHandler handler : postRoundEventHandlers) 
		{
			handler.roundFinished();
		}
		curRound++;
	}

	public Map<String, Object> getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(Map<String, Object> extraInfo) {
		this.extraInfo = extraInfo;
	}
}
