package theresistance.core.round;


public class StartNode extends RoundNode {

	private RoundNode next;
	
	public StartNode onNext(RoundNode next) {
		this.next = next;
		return this;
	}
	
	@Override
	public RoundNode next() {
		return next;
	}

}
