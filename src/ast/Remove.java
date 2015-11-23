package ast;

import java.util.Random;

public class Remove implements Mutation{

	protected Program program;
	
	public void setProgram(Program pro){
		this.program = pro;
	} 
	
	public void getMutated(){
		int prosize = this.program.size();
		Random rand = new Random();
		int count = 0;
		while(count<1000){
			count++;
			int randindex = rand.nextInt(prosize);
			Node node = program.nodeAt(randindex);
			if(node.hasChildern()){
				Node child = node.getChildren();
				if(child.hasChildern()){
					Node child2 = child.getChildren();
					
					if(child instanceof BinaryExpr){
						((BinaryRel)node).replace(child, child2);
					}
					
				}
				
				/*
				if(child instanceof Rule){
					((ProgramImpl)node).remove(child);
					break;
				}*/
			}
		}
		
	}
	
	
	
	
	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return m instanceof Remove;
	}





}
