package ast;

public class NamedUnaryExpr extends NamedExpr{

	protected Expr expr;
	public NamedUnaryExpr(Kind kind, Expr expr) {
		super(kind);
		this.expr = expr;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return expr.size() + 1;
	}
	
	public Node nodeAt(int index) {
		// TODO Auto-generated method stub
		
        if (index == 0) return this;
        index--;
        return this.expr.nodeAt(index);
	}
	
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append(this.kind);
		sb.append("[");
		this.expr.prettyPrint(sb);
		sb.append("]");
		return sb;
		
	}

}
