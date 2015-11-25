package simulation;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import ast.Program;
import parse.Parser;
import parse.ParserImpl;
import parse.Token;
import parse.Tokenizer;

public class SimulationTester {

	@Test
	public void test() throws FileNotFoundException {
		Tokenizer t = new Tokenizer(new FileReader("examples/example-critter.txt"));
		Critter cr1 = new Critter();
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
//		t.next();t.next(); // finish reading the critter attribute
		// then parseProgram
		System.out.println(t.peek().toString());
		Parser parser = new ParserImpl();
		Program prog = parser.parse(t);
		cr1.setName(name);
		cr1.setMem(0, memsize);
		cr1.setMem(1, defense);
		cr1.setMem(2, offense);
		cr1.setMem(3, size);
		cr1.setMem(4, energy);
		cr1.setMem(7, posture);
		cr1.setProgram(prog);
		System.out.println("critter success");
	}

}
