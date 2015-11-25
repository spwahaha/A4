package simulation;

import ast.Program;

public class Critter implements Placeable {
	protected int[] mem;
	//0 memsize
	//1 defense
	//2 offense
	//3 size
	//4 energy
	//5 pass
	//6 tag
	//posture
	protected HexCoord position;
	protected Program rules;
	protected int Direction;
	
	public Critter(){
		this.mem = new int[8];
		
	}
	
	public Critter(HexCoord position){
		this.position = position;
	}
	
	public void setMem(int index, int n){
		this.mem[index] = n;
	}
	
	public int getMem(int index){
		return this.mem[index];
	}
	
	public void setMemSize(int n){
		this.mem[0] = n;
	}
	
	public int getDirection(){
		return this.Direction;
	}
	
	public void setDirection(int dir){
		this.Direction = dir;
	}
	
}
