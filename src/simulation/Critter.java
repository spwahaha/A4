package simulation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import ast.Program;
import ast.ProgramImpl;
import parse.Parser;
import parse.ParserImpl;
import parse.Tokenizer;

public class Critter implements Placeable {
	protected int[] mem;
	//0 memsize
	//1 defense
	//2 offense
	//3 size
	//4 energy
	//5 pass
	//6 tag  should be 0~99 
	//posture
	protected HexCoord position;
	protected Program rules;
	protected int Direction;
	protected World world;
	protected String name;
	protected int lastRuleIndex;
	protected static int MAX_TAG_VALUE;
	
	public Critter(){
		this.mem = new int[8];
		this.name = "";
		this.mem[3] = 1;
	}
	
	
	public Critter(String fileName){
		Tokenizer t = null;
		try {
			t = new Tokenizer(new FileReader("examples/example-critter.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Critter cr1 = new Critter();
		t.next(); t.next();
		String name = t.next().toString().split(":")[1];
		t.next(); t.next(); 
		int memsize = Integer.parseInt(t.next().toString());
		t.next(); t.next();
		int defense = Integer.parseInt(t.next().toString());
		t.next(); t.next();
		int offense = Integer.parseInt(t.next().toString());
		t.next(); t.next();
		int size = Integer.parseInt(t.next().toString());
		t.next(); t.next();
		int energy = Integer.parseInt(t.next().toString());
		t.next(); t.next();
		int posture = Integer.parseInt(t.next().toString());
		// finish reading the critter attribute
		// then parseProgram
//		System.out.println(t.peek().toString());
		Parser parser = new ParserImpl();
		Program prog = parser.parse(t);
		this.mem = new int[8];
		this.setName(name);
		this.setMem(0, memsize);
		this.setMem(1, defense);
		this.setMem(2, offense);
		this.setMem(3, size);
		this.setMem(4, energy);
		this.setMem(7, posture);
		this.setProgram(prog);
		System.out.println("critter success");
		t.close();
	}
	
	
	
	public Critter(HexCoord position, World world){
		this.position = position;
		this.world = world;
	}
	
	public void setPosition(HexCoord posi){
		this.position = posi;
	}
	
	public void setWorld(World world){
		this.world = world;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setMem(int index, int n){
		this.mem[index] = n;
	}
	
	public void setProgram(Program pro){
		this.rules = pro;
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
		return getPosiInfo(nearPosi);
		
	}
	
	public int ahead(int dist){
		int c = this.position.col;
		int r = this.position.row;
		int[][] possiblePosi = {{c,r+1},
								{c+1,r+1},
								{c+1,r},
								{c,r-1},
								{c-1,r-1},
								{c-1,r}};
		for(int i = 0; i < possiblePosi.length; i++){
			possiblePosi[i][0] = possiblePosi[i][0] - c;
			possiblePosi[i][1] = possiblePosi[i][1] - r;			
		}
		int disc = possiblePosi[this.Direction][0];
		int disr = possiblePosi[this.Direction][1];
		
		for(int i = 0; i < dist; i++){
			c = c + disc;
			r = r + disr;
		}
		
		HexCoord aheadPosi = new HexCoord(c,r);
		return getPosiInfo(aheadPosi);

	}
	
	private int getPosiInfo(HexCoord posi){
		if(!this.world.validPosi(posi)) return World.ROCK_VALUE; // if it's out of world, it's a rock!!
		Placeable place = world.getObj(posi);
		if(place == null) return 0;
		if(place instanceof Critter){
			((Critter)place).getAppearance();
		}
		if(place instanceof Rock){
			return World.ROCK_VALUE;
		}
		if(place instanceof Food){
			return (-((Food)place).getFoodValue()) -1;
		}
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
	
	public void setLastRule(int n){
		this.lastRuleIndex = n;
	}
	
	public int getComplexity(){
		int r = ((ProgramImpl)this.rules).getRuleNumber();
		int offense = this.getMem(2);
		int defense = this.getMem(1);
		int complexity = r * World.RULE_COST + 
							(offense + defense) * World.ABILITY_COST;
		return complexity;
	}
	
}
