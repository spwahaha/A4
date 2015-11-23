package ast;

public class Number implements Expr {
	protected int index;
	
	public Number(int index){
		this.index = index;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Node nodeAt(int index) {
		// TODO Auto-generated method stub
		if(index == 0)
			return this;
        throw new IllegalArgumentException("Index out of bounds");
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		return sb.append(index);
	}
	@Override
	public Node copy() {
		// TODO Auto-generated method stub
		return new Number(this.index);
	}
	@Override
	public boolean hasChildern() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Node getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void replace(Node node1, Node node2) {
		// TODO Auto-generated method stub
		
	}

	
}
