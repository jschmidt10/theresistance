package theresistance.core.round;


public class StartNode extends RoundNode {

	final RoundNode next;
	
	public StartNode(RoundNode next) {
		this.next = next;
	}
	
	@Override
	public RoundNode next() {
		return next;
	}

}
