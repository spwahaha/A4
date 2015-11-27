package console;

import java.io.IOException;
import java.util.Scanner;

import ast.ProgramImpl;
import ast.Rule;
import simulation.Critter;
import simulation.Food;
import simulation.HexCoord;
import simulation.Placeable;
import simulation.Rock;
import simulation.World;

/** The console user interface for Assignment 5. */
public class Console {
    private Scanner scan;
    public boolean done;
	World world = null;

    //TODO world representation...

    public static void main(String[] args) {
        Console console = new Console();

        while (!console.done) {
            System.out.print("Enter a command or \"help\" for a list of commands.\n> ");
            console.handleCommand();
        }
    }

    /**
     * Processes a single console command provided by the user.
     */
    void handleCommand() {
        String command = scan.next().toLowerCase();
        switch (command) {
        case "new": {
            newWorld();
            break;
        }
        case "load": {
            String filename = scan.next();
            try {
				loadWorld(filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            break;
        }
        case "critters": {
            String filename = scan.next();
            int n = scan.nextInt();
            loadCritters(filename, n);
            break;
        }
        case "step": {
            int n = scan.nextInt();
            advanceTime(n);
            break;
        }
        case "info": {
            worldInfo();
            break;
        }
        case "hex": {
            int c = scan.nextInt();
            int r = scan.nextInt();
            hexInfo(c, r);
            break;
        }
        case "help": {
            printHelp();
            break;
        }
        case "exit": {
            done = true;
            break;
        }
        default:
            System.out.println(command + " is not a valid command.");
        }
    }

    /**
     * Constructs a new Console capable of reading the standard input.
     */
    public Console() {
        scan = new Scanner(System.in);
        done = false;
    }

    /**
     * Starts new random world simulation.
     */
    private void newWorld() {
        //TODO implement
    	try {
			world = new World();
	    	System.out.println("new world");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /**
     * Starts new simulation with world specified in filename.
     * @param filename
     * @throws IOException 
     */
    private void loadWorld(String filename) throws IOException {
        //TODO implement
    	world = new World(filename);
    }

    /**
     * Loads critter definition from filename and randomly places 
     * n critters with that definition into the world.
     * @param filename
     * @param n
     */
    private void loadCritters(String filename, int n) {
        //TODO implement
    	int number = n;
    	if(this.world==null){
    		System.out.println("NO WORLD NOW!!!");
    		return;
    	}
    	while(n > 0){
    		try {
				Critter cri = new Critter(filename);
				int r = this.world.RAND.nextInt(this.world.getRow());
				int c = this.world.RAND.nextInt(this.world.getCol());
				cri.setPosition(new HexCoord(c,r));
				boolean succ = this.world.addObj(cri, cri.getPosition());
				if(succ) n--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    	System.out.println("load critters successfully~: " + number + "critters");
    }

    /**
     * Advances the world by n time steps.
     * @param n
     */
    private void advanceTime(int n) {
        //TODO implement
    	if(this.world==null){
    		System.out.println("NO WORLD NOW!!!");
    		return;
    	}
    	this.world.excute(n);
    }

    /**
     * Prints current time step, number of critters, and world
     * map of the simulation.
     */
    private void worldInfo() {
        //TODO implement
    	if(this.world==null){
    		System.out.println("NO WORLD NOW!!!");
    		return;
    	}
    	System.out.println("The current step is NO: " + this.world.getSteps());
    	System.out.println("The number of critters is: " + this.world.getCritterNumber());
    	// start to draw the world map
    	printMap();
    }

    private void printMap() {
		// TODO Auto-generated method stub
		int row = this.world.getRow();
		int col = this.world.getCol();
		int cnt1 = row - col + 2;
		int cnt2 = row - col + 1;
		
		while(col>0){
			for(int j = col-1, k = 0; k < cnt1; k++){
				int r = 2 * k;
				int c = j + k;
				HexCoord posi = new HexCoord(c,r);
				Placeable pla = this.world.getObj(posi);
				if(pla == null) System.out.print("-   ");
				else if(pla instanceof Rock)
					System.out.print("#   ");
				else if(pla instanceof Food){
					System.out.print("f   ");
				}
				else if(pla instanceof Critter){
					System.out.print(((Critter)pla).getDirection() + "   ");
				}
			}
			System.out.println();
			System.out.print("  ");		
			if(col==1) return;
			for(int j = col-1, k = 0; k < cnt2; k++){
				int r = 2 * k;
				int c = j + k;
				HexCoord posi = new HexCoord(c,r);
				Placeable pla = this.world.getObj(posi);
				if(pla == null) System.out.print("-   ");
				else if(pla instanceof Rock)
					System.out.print("#   ");
				else if(pla instanceof Food){
					System.out.print("f   ");
				}
				else if(pla instanceof Critter){
					System.out.print(((Critter)pla).getDirection() + "   ");
				}
			}
			System.out.println();
			col--;
		}
	}

	/**
     * Prints description of the contents of hex (c,r).
     * @param c column of hex
     * @param r row of hex
     */
    private void hexInfo(int c, int r) {
        //TODO implement
    	if(this.world==null){
    		System.out.println("NO WORLD NOW!!!");
    		return;
    	}
    	Placeable pla = this.world.getObj(new HexCoord(c,r));
    	if(pla == null){
    		System.out.println("Nothing is here~~");
    		return;
    	}
    	
    	if(pla instanceof Rock){
    		System.out.println("Here is a rock");
    		return;
    	}
    	
    	if(pla instanceof Food){
    		System.out.println("Here is some food: " + ((Food)pla).getFoodValue());
    		return;
    	}
    	
    	if(pla instanceof Critter){
    		System.out.println("Here is a critter");
    		System.out.println("The species is: " + ((Critter)pla).getName());
    		((Critter)pla).printMem();
    		ProgramImpl rules = (ProgramImpl)((Critter)pla).getRules();
    		StringBuilder sb = new StringBuilder();
    		rules.prettyPrint(sb);
    		System.out.println("The rule set is: ");
    		System.out.println(sb.toString());
    		int lastIndex = ((Critter)pla).getLastRuleIndex();
    		Rule lastRule = rules.getRule(lastIndex);
    		sb = new StringBuilder();
    		lastRule.prettyPrint(sb);
    		System.out.println("The last excuted rule is: ");
    		System.out.println(sb.toString());

    		
    	}
    }

    /**
     * Prints a list of possible commands to the standard output.
     */
    private void printHelp() {
        System.out.println("new: start a new simulation with a random world");
        System.out.println("load <world_file>: start a new simulation with "
                + "the world loaded from world_file");
        System.out.println("critters <critter_file> <n>: add n critters "
                + "defined by critter_file randomly into the world");
        System.out.println("step <n>: advance the world by n timesteps");
        System.out.println("info: print current timestep, number of critters "
                + "living, and map of world");
        System.out.println("hex <c> <r>: print contents of hex "
                + "at column c, row r");
        System.out.println("exit: exit the program");
    }
}
