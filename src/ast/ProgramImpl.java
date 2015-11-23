package ast;

import java.util.ArrayList;
import java.util.Random;

/**
 * A data structure representing a critter program.
 *
 */
public class ProgramImpl implements Program,Swapable {
	protected ArrayList<Rule> rules = new ArrayList<Rule>();
	
	
	public ProgramImpl(ArrayList<Rule> rules){
		this.rules = rules;
	}
	
	public void addRule(Rule rule){
		this.rules.add(rule);
	}
	
	
	
    @Override
    public int size() {
        // TODO Auto-generated method stub
    	int size = 1;
    	for(Rule rule:rules){
    		size += rule.size();
    	}
        return size;
    }

    @Override
    public Node nodeAt(int index) {
        // TODO Auto-generated method stub
        if (index == 0) return this;
        index--;
        for (Rule rule : rules) {
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
    	for(Rule rule : this.rules){
    		rule.prettyPrint(sb);
    		sb.append(";");
    		sb.append('\n');
    	}
        return sb;
    }


	@Override
	public Program copy() {
		// TODO Auto-generated method stub
		ArrayList<Rule> newRules=new ArrayList<Rule>();
		for (int i=0; i<this.rules.size(); i++){
			newRules.add((Rule)this.rules.get(i).copy());
		}
		return new ProgramImpl(newRules); 
	}

	@Override
	public boolean hasChildern() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Node getChildren() {
		// TODO Auto-generated method stub
		int size = this.rules.size();
		Random rand = new Random();
		return rules.get(rand.nextInt(size));
		}
	
	public boolean remove(Node n){
		if(rules.size()==1){
			return false;
		}
		for(Rule r:rules){
			if(r==(Rule) n){
				rules.remove(r);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean replace(Node node1, Node node2) {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean swep() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int n1 = rand.nextInt(rules.size());
		int n2 = rand.nextInt(rules.size());
		Rule r1 = rules.get(n1);
		Rule r2 = rules.get(n2);
		rules.add(n1, r2);
		rules.add(n2, r1);
		return true;
	}

}
