package parse;

import java.io.FileNotFoundException;
import java.io.FileReader;

import ast.Mutation;
import ast.MutationFactory;
import ast.Node;
import ast.Program;

public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		Parser parser = new ParserImpl();
		Program prog = parser.parse(new FileReader("examples/test1.txt"));
//		System.out.println("success");
		StringBuilder sb = new StringBuilder();
		prog.prettyPrint(sb);
		System.out.println(sb.toString());
		System.out.println(prog.size());
		Node node = prog.nodeAt(1);
		System.out.println("asd");
		Node node1 = node.copy();
		assert node!=node1;
		System.out.println((node.prettyPrint(new StringBuilder()).toString()));
//		for(int i = 0; i < 100; i++){
			Mutation muta = MutationFactory.getRemove();
			muta.setProgram(prog);
			muta.getMutated();
			System.out.println("mutated");
			System.out.println((prog.prettyPrint(new StringBuilder()).toString()));	
//		}

		
	}
}
