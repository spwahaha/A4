package ast;

import java.util.Random;

public class Update implements Node{

	protected Expr index;
	protected Expr expr;
	
	public Update(Expr index, Expr expr){
		this.index = index;
		this.expr = expr;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.index.size() + this.expr.size() + 1;
	}

	@Override
	public Node nodeAt(int index) {
		// TODO Auto-generated method stub
		if(index == 0) return this;
		return this.index.nodeAt(index - 1);
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append("mem");
		sb.append("[");
		this.index.prettyPrint(sb);
		sb.append("]");
    	sb.append(" ");
		sb.append(":=");
    	sb.append(" ");
		this.expr.prettyPrint(sb);
		return null;
	}

	@Override
	public Node copy() {
		// TODO Auto-generated method stub
		return new Update((Expr)this.index.copy(), (Expr)this.expr.copy());
	}

	@Override
	public boolean hasChildern() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Node getChildren() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		return rand.nextDouble()>0.5?this.index:this.expr;	}

}
