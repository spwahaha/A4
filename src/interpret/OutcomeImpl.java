package interpret;

public class OutcomeImpl implements Outcome {
	protected String action;
	protected int value;
	
	public OutcomeImpl(String action, int value){
		this.action = action;
		this.value = value;
	}
}
