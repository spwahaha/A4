package simulation;

import java.util.Hashtable;

public class World {
	protected Hashtable<HexCoord, Placeable> map;
	
	public World(){
		this.map = new Hashtable<HexCoord,Placeable>();
	}
	
	public void place(HexCoord posi, Placeable obj){
		map.put(posi, obj);
	}
	
	public Placeable getObj(HexCoord posi){
		return map.get(posi);
	}
}
