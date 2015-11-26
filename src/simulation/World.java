package simulation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import interpret.InterpreterImpl;
import interpret.Outcome;

public class World {
	
	protected static int BASE_DAMAGE;
	protected static double DAMAGE_INC;
	protected static int ENERGY_PER_SIZE;
	protected static int FOOD_PER_SIZE;
	protected static int MAX_SMELL_DISTANCE;
	protected static int ROCK_VALUE;
	protected static int COLUMNS;
	protected static int ROWS;
	public static int MAX_RULES_PER_TURN;
	protected static int SOLAR_FLUX;
	protected static int MOVE_COST;
	protected static int ATTACK_COST;
	protected static int GROW_COST;
	protected static int BUD_COST;
	protected static int MATE_COST;
	protected static int RULE_COST;
	protected static int ABILITY_COST;
	protected static int INITIAL_ENERGY;
	protected static int MIN_MEMORY;
	protected static final int MAX_DIRECTION = 6;
	
	
	protected Hashtable<HexCoord, Placeable> map;
	protected int Col; // the col and row number of the world, 0 ~ col - 1, 0 ~ row-1
	protected int Row; // a hexcoord maybe out scope 
	protected ArrayList<Critter> critters = new ArrayList<Critter>();
	protected String name;
	protected String constantFileName = "examples/constants.txt";
	public static Random RAND = new Random();
	
	
	public World() throws IOException{
		loadConstant(constantFileName);
		loadRandomWorld();
	}
	


	public World(String filename) throws IOException{
		loadWorldFromFile(filename);
		loadConstant(constantFileName);
		System.out.println("load world successfully");
	}
	
	
	private void loadRandomWorld() {// not finished yet
		// TODO Auto-generated method stub
		this.map = new Hashtable<HexCoord,Placeable>();
		this.Row = World.ROWS;
		this.Col = World.COLUMNS;
		
	}


	private void loadWorldFromFile(String filename) throws IOException {
		// TODO Auto-generated method stub
		this.map = new Hashtable<HexCoord,Placeable>();
		// read world file and many object by world file
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
//		World world = new World();
		while(line!=null){
			if(line.contains("//")){
				line = br.readLine();
				continue;
			}
			line = line.toLowerCase();
			if(line.startsWith("name")){
				String name = line.substring(5, line.length());
				this.setName(name.trim());
			}
			
			if(line.startsWith("size")){
				String[] sizeAtr = line.split(" ");
				int col = Integer.parseInt(sizeAtr[1]);
				int row = Integer.parseInt(sizeAtr[2]);
				this.setSize(col, row);
			}
			
			if(line.startsWith("rock")){
				String[] posiAtr = line.split(" ");
				int col = Integer.parseInt(posiAtr[1]);
				int row = Integer.parseInt(posiAtr[2]);
				HexCoord posi = new HexCoord(col, row);
				this.addObj((new Rock()), posi);
			}
			
			if(line.startsWith("food")){
				String[] posiAtr = line.split(" ");
				int col = Integer.parseInt(posiAtr[1]);
				int row = Integer.parseInt(posiAtr[2]);
				int foodValue = Integer.parseInt(posiAtr[3]);
				HexCoord posi = new HexCoord(col, row);
				this.addObj((new Food(foodValue)), posi);
			}
			
			if(line.startsWith("critter")){
				String[] critterAtr = line.split(" ");
				String critterFilename = critterAtr[1];
				critterFilename = filename.split("/")[0] + "/" + critterFilename;
				int col = Integer.parseInt(critterAtr[2]);
				int row = Integer.parseInt(critterAtr[3]);
				int dir = Integer.parseInt(critterAtr[4]);
				HexCoord posi = new HexCoord(col, row);
				Critter cri1 = new Critter(critterFilename);
				cri1.setPosition(posi);
				cri1.setDirection(dir % 6);
				cri1.setWorld(this);
				this.addObj(cri1, posi);
			}
			line = br.readLine();
		}
		br.close();
	}

	
	private void loadConstant(String filename) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		while(line!=null){
			String[] info = line.split(" ");
			switch(info[0]){
			case "BASE_DAMAGE": World.BASE_DAMAGE = Integer.parseInt(info[1]);
								break;
			case "DAMAGE_INC": World.DAMAGE_INC = Double.parseDouble(info[1]);
								break;
			case "ENERGY_PER_SIZE": World.ENERGY_PER_SIZE = Integer.parseInt(info[1]);
									break;
			case "FOOD_PER_SIZE": World.FOOD_PER_SIZE = Integer.parseInt(info[1]);
									break;
			case "MAX_SMELL_DISTANCE": World.MAX_SMELL_DISTANCE = Integer.parseInt(info[1]);
									break;
			case "ROCK_VALUE": World.ROCK_VALUE = Integer.parseInt(info[1]);
								break;
			case "COLUMNS": World.COLUMNS = Integer.parseInt(info[1]);
								break;
			case "ROWS": World.ROWS = Integer.parseInt(info[1]);
									break;
			case "MAX_RULES_PER_TURN": World.MAX_RULES_PER_TURN = Integer.parseInt(info[1]);
									break;
			case "SOLAR_FLUX": World.SOLAR_FLUX = Integer.parseInt(info[1]);
									break;
			case "MOVE_COST": World.MOVE_COST = Integer.parseInt(info[1]);
									break;
			case "ATTACK_COST": World.ATTACK_COST = Integer.parseInt(info[1]);
									break;
			case "GROW_COST": World.GROW_COST = Integer.parseInt(info[1]);
									break;
			case "BUD_COST": World.BUD_COST = Integer.parseInt(info[1]);
									break;
			case "MATE_COST": World.MATE_COST = Integer.parseInt(info[1]);
									break;
			case "RULE_COST": World.RULE_COST = Integer.parseInt(info[1]);
									break;
			case "ABILITY_COST": World.ABILITY_COST = Integer.parseInt(info[1]);
									break;
			case "INITIAL_ENERGY": World.INITIAL_ENERGY = Integer.parseInt(info[1]);
									break;
			case "MIN_MEMORY": World.MIN_MEMORY = Integer.parseInt(info[1]);
									break;
			default: System.out.println("Unknown constant" + info[0]);
			
			}
			
			line = br.readLine();
		}
		
	}
	
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setSize(int col, int row){
		this.Col = col;
		this.Row = row;
	}
	
	/** add critter, food or rock in the world
	 * if the placeObj is a critter, put it in the critter arrayList*/
	public boolean addObj(Placeable placeObj, HexCoord posi){
		if(!this.validPosi(posi)) return false; // try to place on invalid place
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
		return this.map.get(posi);
	}
	
	public boolean validPosi(HexCoord posi){
		int c = posi.col;
		int r = posi.row;
		if(c >= this.Col || r >= this.Row) return false;
		if(2 * r - c < 0 || 2 * r - c >= 2 * this.Row - this.Col) return false;
		return true;
	}
	
	public void excute(int times){
		int i = times;
		while(i>0){
			i--;
			InterpreterImpl interpreter = null;
			for(int j = 0; j < critters.size(); j++){
				Critter cri = critters.get(j);
				interpreter = new InterpreterImpl(cri,cri.rules);
				Outcome outcome = interpreter.interpret();
				if(outcome == null) continue;
				excuteOutcome(cri, outcome);
			}
		}
	}



	private void excuteOutcome(Critter cri, Outcome outcome) {
		// TODO Auto-generated method stub
		String actionName = outcome.getAction().toLowerCase();
		int value = outcome.getValue();
		switch(actionName){
		case "wait":{
			cri.setMem(4, cri.getMem(4) + cri.getMem(3) * World.SOLAR_FLUX);
			break;
		}
		case "forward":{
			moveForward(cri);
			break;
		}
		case "backward":{
			moveBackward(cri);
			break;
		}
		case "left":{
			turnLeft(cri);
			break;
		}
		case "right":{
			turnRight(cri);
			break;
		}
		case "eat":{
			eat(cri);
			break;
		}
		case "serve":{
			serve(cri,value);
			break;
		}
		case "attack":{
			attack(cri);
			break;
		}
		case "tag":{
			tag(cri, value);
			break;
		}
		case "grow":{
			grow(cri);
			break;
		}
		case "bud":{
			bud(cri);
		}
		case "mate":{
			mate(cri);
		}
		
		}
	}

	private void die(Critter cri) {
		// TODO Auto-generated method stub
		// remove from arraylist
		this.critters.remove(cri);
		//become a food value = size * food_per_size
		Food food = new Food(cri.getMem(3) * World.FOOD_PER_SIZE);
		// update the map info
		this.map.put(cri.position, food);
	}

	private void moveForward(Critter cri) {
		// TODO Auto-generated method stub
		int cost = World.MOVE_COST * cri.getMem(3);// cost is movecost * size
		cri.setMem(4, cri.getMem(4) - cost);
		// test whether critter dies
		if(cri.getMem(4) <=0){
			// critter dies
			die(cri);			
			return;
		}
		
		HexCoord forwardPosi = getFrontPosi(cri);
		moveTo(cri, forwardPosi);
	} 



	private void moveBackward(Critter cri) {
		// TODO Auto-generated method stub
		int cost = World.MOVE_COST * cri.getMem(3);// cost is movecost * size
		cri.setMem(4, cri.getMem(4) - cost);
		//test whether dies
		if(cri.getMem(4) <=0){
			// cri die
			// delete it from arraylist and map
			this.critters.remove(cri);
			this.map.remove(cri.position);
			return;
		}
		
		HexCoord backwardPosi = getBackPosi(cri);
		moveTo(cri, backwardPosi);
	}

	private void moveTo(Critter cri, HexCoord destiny) {
		// TODO Auto-generated method stub
		if(!this.validPosi(destiny)) return; // if the forward position is invalid, move unsuccessfully
		// update energy
		if(this.map.get(destiny) == null){
			// if the there is nothing in the forwardposition
			// then move forward
			// set the original position null
			this.map.remove(cri.position);
			// update the position of critter
			cri.setPosition(destiny);
			// update the world
			this.map.put(destiny, cri);
		}
	}


	private void turnLeft(Critter cri) {
		// TODO Auto-generated method stub
		int cost = cri.getMem(3);// cost is size
		cri.setMem(4, cri.getMem(4) - cost);
		if(cri.getMem(4) <= 0){
			die(cri);
			return;
		}
		cri.Direction = (cri.Direction - 1) % World.MAX_DIRECTION;
		if(cri.Direction < 0) cri.Direction +=6;
	}



	private void turnRight(Critter cri) {
		// TODO Auto-generated method stub
		int cost = cri.getMem(3);// cost is size
		cri.setMem(4, cri.getMem(4) - cost);
		if(cri.getMem(4) <= 0){
			die(cri);
			return;
		}
		cri.Direction = (cri.Direction + 1) % World.MAX_DIRECTION;
		if(cri.Direction < 0) cri.Direction +=6;

	}



	private void eat(Critter cri) {
		// TODO Auto-generated method stub
		// find the ahead info, whether there is some food
		// if there is some food then eat, if not, return;
		int cost = cri.getMem(3);// cost is size
		cri.setMem(4, cri.getMem(4) - cost);
		if(cri.getMem(4) <= 0){
			die(cri);
			return;
		}
		int aheadInfo = cri.ahead(1); // 
		if(aheadInfo >= -1) return;

		HexCoord forwardPosi = getFrontPosi(cri);
		if(!validPosi(forwardPosi)) return;
		Food food = (Food)this.map.get(forwardPosi); // get the food in front of the critter
		int foodValue = food.value;
		int energy = cri.getMem(4);
		int maxEnergy = cri.getMem(3) * World.ENERGY_PER_SIZE;
		if(foodValue + energy < maxEnergy){
			// eat all the food and so remove food from the world
			cri.setMem(4, foodValue + energy);
			this.map.remove(forwardPosi);
		}else{
			foodValue = energy + foodValue - maxEnergy;
			cri.setMem(4, maxEnergy);
			food.setFoodValue(foodValue);
		}
	}


	/**A critter may convert some of its own energy into food added to 
	 * the hex in front of it, if that hex is either empty or already 
	 * contains some food.
	 * */
	private void serve(Critter cri, int value) {
		// TODO Auto-generated method stub
		// 1 front must be food or empty to serve successfully
		// 2 energy cost is value + cri.size
		// 3 critter may be killed by serve, then the served value should
		//   critter.energy - cost, and set current hex to food
		int cost = cri.getMem(3);// cost is size
		cri.setMem(4, cri.getMem(4) - cost);
		if(cri.getMem(4) <= 0){
			die(cri);
			return;
		}
		
		HexCoord frontPosi = getFrontPosi(cri);
		if(!validPosi(frontPosi)) return;
		if(this.map.get(frontPosi) == null 
				|| this.map.get(frontPosi) instanceof Food){
			// front is food or empty, so can serve
			Food food = null;
			if(this.map.get(frontPosi) == null){
				// front is empty
				food = new Food();
			}else{
				food = (Food)this.map.get(frontPosi);
			}
			
			if(value >= cri.getMem(4)){
				// served value is larger than cri.energy
				food.setFoodValue(food.getFoodValue() + cri.getMem(4));
				cri.setMem(4, 0);
			}else{
				food.setFoodValue(food.getFoodValue() + value);
				cri.setMem(4, cri.getMem(4) - value);
			}
			this.map.put(frontPosi, food); // put food in the world
			if(cri.getMem(4) <= 0){
				die(cri);
			}
		}
		
	}



	private void attack(Critter cri) {
		// TODO Auto-generated method stub
		int cost = World.ATTACK_COST * cri.getMem(3);// cost is attackcost * size
		cri.setMem(4, cri.getMem(4) - cost);
		// test whether critter dies
		if(cri.getMem(4) <=0){
			// critter dies
			die(cri);			
			return;
		}
		
		HexCoord frontPosi = getFrontPosi(cri);
		if(!validPosi(frontPosi)) return;
		if(!(this.map.get(frontPosi) instanceof Critter)) return;
		Critter victim = (Critter)this.map.get(frontPosi);
		int s1 = cri.getMem(3);
		int s2 = victim.getMem(3);
		int o1 = cri.getMem(2);
		int d2 = victim.getMem(1);
		double x = World.DAMAGE_INC * (s1 * o1 - s2 * d2);
		double p = 1.0/(1 + Math.exp(-x));
		int hurt = (int) (World.BASE_DAMAGE * s1 * p);
		victim.setMem(4, victim.getMem(4) - hurt);
		if(victim.getMem(4) <= 0){
			die(victim);
		}
		
	}
	
	public static HexCoord getFrontPosi(Critter cri){
		int c = cri.position.col;
		int r = cri.position.row;
		int[][] possiblePosi = {{0,1},
								{1,1},
								{1,0},
								{0,-1},
								{-1,-1},
								{-1,0}};
		c += possiblePosi[cri.Direction][0];
		r += possiblePosi[cri.Direction][1];
		return new HexCoord(c,r);
	}

	public static HexCoord getBackPosi(Critter cri){
		int c = cri.position.col;
		int r = cri.position.row;
		int[][] possiblePosi = {{0,1},
								{1,1},
								{1,0},
								{0,-1},
								{-1,-1},
								{-1,0}};
		int length = possiblePosi.length;
		c += possiblePosi[(cri.Direction + length/2) % length][0];
		r += possiblePosi[(cri.Direction + length/2) % length][1];
		HexCoord backwardPosi = new HexCoord(c,r);
		return backwardPosi;
	}
	
	


	private void tag(Critter cri, int value) {
		// TODO Auto-generated method stub
		int cost = cri.getMem(3);// cost is size
		cri.setMem(4, cri.getMem(4) - cost);
		// test whether critter dies
		if(cri.getMem(4) <=0){
			// critter dies
			die(cri);			
			return;
		}
		if(value > Critter.MAX_TAG_VALUE || value < 0) return; // tag value should be valid
		
		HexCoord frontPosi = getFrontPosi(cri);
		if(!validPosi(frontPosi)) return;
		if(!(this.map.get(frontPosi) instanceof Critter)) return;
		Critter victim = (Critter)this.map.get(frontPosi);
		victim.setMem(6, value); // set victim tag
	}



	private void grow(Critter cri) {
		// TODO Auto-generated method stub
		int cost = cri.getMem(3) * cri.getComplexity() * World.GROW_COST;
		cri.setMem(4, cri.getMem(4) - cost);
		if(cri.getMem(4) <= 0){
			die(cri);
			return;
		}
		cri.setMem(3, cri.getMem(3) + 1);
	}



	private void bud(Critter cri) {
		// TODO Auto-generated method stub
		int cost = World.BUD_COST * cri.getComplexity();
		cri.setMem(4, cri.getMem(4) - cost);
		if(cri.getMem(4) <= 0){
			die(cri);
			return;
		}
		
		HexCoord backPosi = getBackPosi(cri);
		// invalid position, so child cannot be created 
		if(!this.validPosi(backPosi)) return; 
		//there is something on that position, so child cannont be created
		if(this.map.get(backPosi)!=null) return; 
		 // get the child
		Critter child = cri.bud(); 
		// update the map and critters arraylist
		this.map.put(backPosi, child);
		this.critters.add(child);
	}



	private void mate(Critter cri) {
		// TODO Auto-generated method stub
		// mate is different in consuming energy
		// if unsuccessful, only cost energy of turn
		int cost = 0;
		boolean succ = false;
		HexCoord frontPosi = getFrontPosi(cri);
		if(!(this.map.get(frontPosi) instanceof Critter)){
			succ = false;

		}else{
			// front is a critter
			Critter bride = (Critter)this.map.get(frontPosi);
			// they have to be face to face 
			boolean faceToFace = Math.abs(cri.Direction-bride.Direction) == 3;
			// the last action of bride must also be mate
			boolean wantMate = bride.wantMate();
			// the position to put child
			HexCoord childPosi = getMatePosi(cri,bride);
			// both of them have enough energy
			int brideCost = World.MATE_COST * bride.getComplexity();
			int criCost = World.MATE_COST * cri.getComplexity();
			boolean criEnoughEner = cri.getMem(4) >= criCost;
			boolean briEnoughEner = bride.getMem(4) >= brideCost;
			boolean enoughEnergy = briEnoughEner && criEnoughEner;
			
			if(wantMate && faceToFace && enoughEnergy
					&& childPosi!=null){
				// finally, they can mate
				succ = true;
				Critter child = Critter.mate(cri, bride, childPosi, this);
				this.map.put(childPosi, child);
				this.critters.add(child);
			}
			
		}
		
		if(succ){
			cost = World.MATE_COST * cri.getComplexity();
		}else{
			cost = cri.getMem(3); 
		}
		
		cri.setMem(4, cri.getMem(4) - cost);
		if(cri.getMem(4) <= 0){
			die(cri);
			return;
		}
		
	}



	private HexCoord getMatePosi(Critter cri, Critter bride) {
		// TODO Auto-generated method stub
		HexCoord criBack = getBackPosi(cri);
		HexCoord brideBack = getBackPosi(cri);
		boolean criValid = validPosi(criBack);
		boolean briValid = validPosi(brideBack);
		if(!criValid && !briValid) return null; // no space for child
		//valid position and there is nothing on this position
		criValid = criValid && this.map.get(criBack) == null;
		briValid = briValid && this.map.get(briValid) == null;
		// if both valid return random one
		if(criValid && briValid) return World.RAND.nextDouble() > 0.5?
													criBack:brideBack;
		// return the valid one
		if(criValid) return criBack;
		if(briValid) return brideBack;
		return null;
	}

	
	
	
}
