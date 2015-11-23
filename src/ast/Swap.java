package ast;

import java.util.Random;

public class Swap implements Mutation{

	
	protected Program program;
	
	public void setProgram(Program pro){
		this.program = pro;
	} 
	
	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return m instanceof Swap;
	}



	@Override
	public boolean getMutated() {
		// TODO Auto-generated method stub
		int prosize = this.program.size();
		Random rand = new Random();
		int count = 0;
		while(count<1000){
			count++;
			Node node = program.nodeAt(rand.nextInt(prosize));
			if(node instanceof BinaryCondition || node instanceof BinaryExpr
					|| node instanceof BinaryRel || node instanceof ProgramImpl){
				((Swapable) node).swep();
				return true;
			}
			
						
		}
		return false;
	}
	

}
