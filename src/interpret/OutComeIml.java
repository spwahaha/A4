package interpret;

public class OutComeIml implements Outcome {
	protected String action;
	protected int value;
	
	public OutComeIml(String action, int value){
		this.action = action;
		this.value = value;
	}
}
