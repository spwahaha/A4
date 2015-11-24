package ast;

import java.util.ArrayList;
import java.util.Random;

public class Duplicate implements Mutation{

	Program program;
	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return m instanceof Duplicate;
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
			if(node instanceof Program){
				ArrayList<Rule> rules = ((ProgramImpl)node).rules;
				Rule selectedRule = rules.get(rand.nextInt(rules.size()));
				rules.add(selectedRule);
//				((ProgramImpl)node).rules.add(selectedRule);
				return true;
			}
			
			if(node instanceof Command){
				ArrayList<Update> updates = ((Command)node).updates;
				if(updates.size()==0)
					continue;
				Update selectedUpdate = updates.get(rand.nextInt(updates.size()));
				updates.add(selectedUpdate);
//				((Command)node).updates.add(selectedUpdate);
				return true;
			}
						
		}
	
		return false;
	}

}
