package ast;

import java.util.ArrayList;
import java.util.Random;

/**
 * A data structure representing a critter program.
 *
 */
public class ProgramImpl implements Program {
	protected ArrayList<Rule> program = new ArrayList<Rule>();
	
	
	public ProgramImpl(ArrayList<Rule> rules){
		this.program = rules;
	}
	
	public void addRule(Rule rule){
		this.program.add(rule);
	}
	
	
	
    @Override
    public int size() {
        // TODO Auto-generated method stub
    	int size = 1;
    	for(Rule rule:program){
    		size += rule.size();
    	}
        return size;
    }

    @Override
    public Node nodeAt(int index) {
        // TODO Auto-generated method stub
        if (index == 0) return this;
        index--;
        for (Rule rule : program) {
            int ruleSize = rule.size();
            if (index < ruleSize) return rule.nodeAt(index);
            index -= ruleSize;
        }
        throw new IllegalArgumentException("Index out of bounds");
    }

    @Override
    public Program mutate() {
        // TODO Auto-generated method stub
		Mutation muta = MutationFactory.getRemove();
		muta.setProgram(this);
		muta.getMutated();
		return this;
        
    }

    @Override
    public Program mutate(int index, Mutation m) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringBuilder prettyPrint(StringBuilder sb) {
        // TODO Auto-generated method stub
    	for(Rule rule : this.program){
    		rule.prettyPrint(sb);
    		sb.append(";");
    		sb.append('\n');
    	}
        return sb;
    }


	@Override
	public Node copy() {
		// TODO Auto-generated method stub
		return new ProgramImpl(new ArrayList<Rule>(this.program));
	}

	@Override
	public boolean hasChildern() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Node getChildren() {
		// TODO Auto-generated method stub
		int size = this.program.size();
		Random rand = new Random();
		return program.get(rand.nextInt(size));
		}
	
	public void remove(Node n){
		if(program.size()==1){
			return;
		}
		for(Rule r:program){
			if(r==(Rule) n){
				program.remove(r);
				break;
			}
		}
	}

	@Override
	public void replace(Node node1, Node node2) {
		// TODO Auto-generated method stub
		
	}

}
