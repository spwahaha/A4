package ast;

import java.util.Random;

public class Replace implements Mutation{

	Program program;
	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return m instanceof Replace;
	}

	@Override
	public void setProgram(Program pro) {
		// TODO Auto-generated method stub
		this.program = pro;
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
			Node child = node.getChildren();
			
			
						
		}
		return false;
	}
	
}
