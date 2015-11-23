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
	public void getMutated() {
		// TODO Auto-generated method stub
		int prosize = this.program.size();
		Random rand = new Random();
		int count = 0;
		while(count<1000){
			count++;
			Node node1 = program.nodeAt(rand.nextInt(prosize));
			Node node2 = program.nodeAt(rand.nextInt(prosize));
			if(node1 instanceof Rule && node2 instanceof Rule){
				
			}
		}
	}
	

}
