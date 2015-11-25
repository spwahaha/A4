package simulation;

import java.util.Random;

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
	protected World world;
	
	
	public Critter(){
		this.mem = new int[8];
		
	}
	
	public Critter(HexCoord position, World world){
		this.position = position;
		this.world = world;
	}
	
	public void setMem(int index, int n){
		this.mem[index] = n;
	}
	
	public int getMem(int index){
		return this.mem[index];
	}
	
	public int getPass(){
		return this.mem[5];
	}
	
	public void setPass(int n){
		this.mem[5] = n;
	}
	
	public int getDirection(){
		return this.Direction;
	}
	
	public void setDirection(int dir){
		this.Direction = dir;
	}
	
	public int nearby(int dir){
		int c = this.position.col;
		int r = this.position.row;
		int[][] possiblePosi = {{c,r+1},
								{c+1,r+1},
								{c+1,r},
								{c,r-1},
								{c-1,r-1},
								{c-1,r}};

		HexCoord nearPosi = new HexCoord();
		int dirsum = this.Direction + dir;
		nearPosi.setCol(possiblePosi[dirsum % 6][0]); 		
		nearPosi.setCol(possiblePosi[dirsum % 6][1]);
		// if nearPosi out of scope, it should be rock,
		
		Placeable place = world.getObj(nearPosi);
		if(place == null) return 0;
		if(place instanceof Critter){
			((Critter)place).getAppearance();
		}
		if(place instanceof Rock){
			return -1;
		}
		if(place instanceof Food){
			return (-((Food)place).getFoodValue()) -1;
		}
		return 0;
		
	}
	
	public int ahead(int dist){
		return 0;
	}
	
	public int smell(){
		return 0;
	}
	
	public int random(int n){
		if(n < 2) return 0;
		Random rand = new Random();
		return rand.nextInt(n);
	}
	
	public int getAppearance(){
		return this.mem[4] * 100000 + this.mem[6] * 1000 + mem[7] * 10;
	}
	
}
