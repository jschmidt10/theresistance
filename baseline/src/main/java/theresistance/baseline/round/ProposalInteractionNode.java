package theresistance.baseline.round;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import theresistance.core.Player;
import theresistance.core.Round;
import theresistance.core.round.InteractionNode;
import theresistance.core.round.RoundNode;
import theresistance.core.selection.PlayerSelection;
import theresistance.core.selection.PlayerSubsetChoice;

public class ProposalInteractionNode extends InteractionNode<Set<Player>> {

	public ProposalInteractionNode(RoundNode next) {
		super(next);
	}

	@Override
	public List<? extends PlayerSelection<Set<Player>>> getInteractions() {
		Round round = game.getCurrentRound();
		int numberParticipants = round.getMission().getNumParticipants();
		return Collections.singletonList(new PlayerSubsetChoice(game.getCurrentLeader(), game.getPlayers(), numberParticipants));
	}

	@Override
	public Set<Player> getNecessaryPlayers() 
	{
		return Collections.singleton(game.getCurrentLeader());
	}
}
