package ast;

public class Replace implements Mutation{

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return m instanceof Replace;
	}

	@Override
	public void setProgram(Program pro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getMutated() {
		return false;
		// TODO Auto-generated method stub
		
	}
	
}
