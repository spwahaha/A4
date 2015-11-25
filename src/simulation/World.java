package simulation;

import java.util.ArrayList;
import java.util.Hashtable;

public class World {
	protected Hashtable<HexCoord, Placeable> map;
	protected int Col; // the col and row number of the world, 0 ~ col - 1, 0 ~ row-1
	protected int Row; // a hexcoord maybe out scope 
	protected ArrayList<Critter> critters = new ArrayList<Critter>();
	
	public World(){
		this.map = new Hashtable<HexCoord,Placeable>();
	}
	/** add critter, food or rock in the world
	 * if the placeObj is a critter, put it in the critter arrayList*/
	public boolean addCritter(Placeable placeObj, HexCoord posi){
		if(map.get(posi)!=null) return false; // there is something in this position posi
		if(placeObj instanceof Critter){
			critters.add((Critter)placeObj);
		}
		map.put(posi, placeObj);
		return true;
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
