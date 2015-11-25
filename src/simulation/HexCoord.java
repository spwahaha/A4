package simulation;

public class HexCoord implements Placeable{
	protected int col;
	protected int row;
	
	public HexCoord(int col, int row){
		this.col = col;
		this.row = row;
	}
	
	public boolean equals(HexCoord hex){
		return this.col == hex.col && this.row==hex.row;
	}
	
	
}
