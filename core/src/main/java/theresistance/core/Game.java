package theresistance.core;

import theresistance.core.config.GameConfig;
import theresistance.core.config.RoleAssigner;

public class Game
{
	private Round[] rounds;
	private Player[] players;

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
	 */
	public void completeRound()
	{
		curRound++;
	}
}
