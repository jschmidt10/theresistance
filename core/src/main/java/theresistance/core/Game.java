package theresistance.core;

import java.util.Set;

import theresistance.core.round.StartNode;


public abstract class Game
{
	public abstract StartNode getGameGraph();
	
	public Player getCurrentLeader() {
		// TODO Auto-generated method stub
		return null;
	}

	public Round getCurrentRound() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isOver()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public Alignment getWinner()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
