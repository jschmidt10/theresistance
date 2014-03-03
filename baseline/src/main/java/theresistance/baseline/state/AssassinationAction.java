package theresistance.baseline.state;

import theresistance.core.state.GameAction;

public class AssassinationAction implements GameAction 
{
	private String assassinated;

	public String getAssassinated() {
		return assassinated;
	}

	public void setAssassinated(String assassinated) {
		this.assassinated = assassinated;
	}
}
