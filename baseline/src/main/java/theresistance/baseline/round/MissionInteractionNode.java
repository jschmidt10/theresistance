package theresistance.baseline.round;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import theresistance.core.Mission.Result;
import theresistance.core.Player;
import theresistance.core.round.InteractionNode;
import theresistance.core.round.RoundNode;
import theresistance.core.selection.PlayerSelection;
import theresistance.core.selection.StaticChoice;

public class MissionInteractionNode extends InteractionNode<Result>{

	public MissionInteractionNode(RoundNode next) {
		super(next);
	}

	@Override
	public List<? extends PlayerSelection<Result>> getInteractions() {
		Set<Player> onMission = game.getCurrentRound().getParticipants();
		return onMission.stream()
				.map( p -> new StaticChoice<>(p, EnumSet.allOf(Result.class)))
				.collect(Collectors.toList());
	}

	@Override
	public Set<Player> getNecessaryPlayers() {
		return game.getCurrentRound().getParticipants();
	}

}
