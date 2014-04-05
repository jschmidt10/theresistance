package theresistance.baseline.round;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import theresistance.baseline.role.Assassin;
import theresistance.core.Player;
import theresistance.core.round.InteractionNode;
import theresistance.core.selection.PlayerSelection;
import theresistance.core.selection.PlayerSubsetChoice;

public class AssassinationInteractionNode extends InteractionNode<Set<Player>> {

	@Override
	public List<? extends PlayerSelection<Set<Player>>> getInteractions() {
		Set<Player> players = game.getPlayers();
		return players
				.stream()
				.map( p -> new PlayerSubsetChoice(p, players, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Set<Player> getNecessaryPlayers() {
		return game.getPlayers()
				.stream()
				.filter( p -> p.getRole() instanceof Assassin )
				.collect(Collectors.toSet());
	}

}
