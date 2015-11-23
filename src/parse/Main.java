package parse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import ast.Mutation;
import ast.MutationFactory;
import ast.Node;
import ast.Program;

public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		Parser parser = new ParserImpl();
		Program prog = parser.parse(new FileReader("examples/test1.txt"));
		Program prog2 = prog.copy();
		assert prog!=prog2;
//		System.out.println("success");
		StringBuilder sb = new StringBuilder();
		prog.prettyPrint(sb);
		System.out.println(sb.toString());
		System.out.println(prog.size());
		Node node = prog.nodeAt(1);
		System.out.println("asd");
		Node node1 = node.copy();
		assert node!=node1;
		ArrayList<Program> pros = new ArrayList<Program>();
		for(int i = 0; i < 100; i++){
			pros.add(prog.copy());
		}
		System.out.println((prog.prettyPrint(new StringBuilder()).toString()));
		int count = 0;
		for(int i = 0; i < 100; i++){
			Mutation muta = MutationFactory.getRemove();
			Program pro = pros.get(i);
			muta.setProgram(pro);
			boolean succ = muta.getMutated();
			System.out.println("mutated");
			System.out.println((pro.prettyPrint(new StringBuilder()).toString()));	
			if(succ) count++;
		}
		System.out.println(count);

		System.out.println((prog.prettyPrint(new StringBuilder()).toString()));

	}
}
