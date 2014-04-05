package theresistance.baseline.round;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import theresistance.core.Player;
import theresistance.core.Proposal.Vote;
import theresistance.core.round.InteractionNode;
import theresistance.core.round.RoundNode;
import theresistance.core.selection.PlayerSelection;
import theresistance.core.selection.StaticChoice;

public class VoteInteractionNode extends InteractionNode<Vote> {
 
	public VoteInteractionNode(RoundNode next) {
		super(next);
	}

	@Override
	public List<? extends PlayerSelection<Vote>> getInteractions() {
		
		Set<Player> players = game.getPlayers();
		return players
				.stream()
				.map( p -> new StaticChoice<>(p, EnumSet.allOf(Vote.class)))
				.collect(Collectors.toList());
	}

	@Override
	public Set<Player> getNecessaryPlayers() {
		return game.getPlayers();
	}

}
