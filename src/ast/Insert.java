package ast;

public class Insert implements Mutation {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return m instanceof Insert;
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
