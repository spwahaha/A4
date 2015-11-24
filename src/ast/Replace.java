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
		// success rate is too low
		// TODO Auto-generated method stub
		int prosize = this.program.size();
		Random rand = new Random();
		int count = 0;
		while(count<1000){
			count++;
			Node node = program.nodeAt(rand.nextInt(prosize));
			Node child = node.getChildren();
			Node similarChild = null;
			int con2 = 0;
			boolean similar = false;
			while(con2<1000){
				con2++;
				similarChild = program.nodeAt(rand.nextInt(prosize));
				if(child == similarChild) continue;
				
				if(child instanceof Condition && 
						similarChild instanceof Condition){
					similar = true;
					break;
				}
				
				if(child instanceof Expr && 
						similarChild instanceof Expr){
					similar = true;
					break;
				}
				if(child instanceof Update && 
						similarChild instanceof Update){
					similar = true;
					break;
				}
				if(child instanceof Action && 
						similarChild instanceof Action){
					similar = true;
					break;
				}	
			}
			
			if(similar){
				node.replace(child, similarChild.copy());
				return true;
			}
						
		}
		return false;
	}
	
}
