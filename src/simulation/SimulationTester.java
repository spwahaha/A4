package simulation;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import ast.Program;
import parse.Parser;
import parse.ParserImpl;
import parse.Token;
import parse.Tokenizer;

public class SimulationTester {

	@Test
	public void test() throws IOException {
		String fileName = "example-critter.txt";
		Critter cri = new Critter(fileName);
		ArrayList<Critter> cris = new ArrayList<Critter>();
		cris.add(cri);
		System.out.println(cris.get(0).getMem(3));
		Critter cri2 = cris.get(0);
		cri2.setMem(3, cri2.getMem(3) + 1);
		System.out.println(cris.get(0).getMem(3));		
		HashMap<Integer, Food> hm = new HashMap<Integer, Food>();
		hm.put(1, new Food(300));
		Food fod1 = hm.get(1);
		fod1.setFoodValue(100);
		System.out.println(hm.get(1).value);
	}

}
