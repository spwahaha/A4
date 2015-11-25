package simulation;

import java.util.Hashtable;

public class World {
	protected Hashtable<HexCoord, Placeable> map;
	protected int Col; // the col and row number of the world, 0 ~ col - 1, 0 ~ row-1
	protected int Row; // a hexcoord maybe out scope 
	
	public World(){
		this.map = new Hashtable<HexCoord,Placeable>();
	}
	
	public void place(HexCoord posi, Placeable obj){
		map.put(posi, obj);
	}
	
	public Placeable getObj(HexCoord posi){
		return map.get(posi);
	}
	
	public boolean validPosi(HexCoord posi){
		int c = posi.col;
		int r = posi.row;
		if(c >= this.Col || r >= this.Row) return false;
		if(2 * r - c < 0 || 2 * r - c >= 2 * this.Row - this.Col) return false;
		return true;
	}
	
}
