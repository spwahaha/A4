package interpret;

import ast.Condition;
import ast.Expr;
import ast.Program;
import simulation.Critter;

public class InterpreterImpl implements Interpreter {
	protected Critter critter;
	protected Program program;
	
	public InterpreterImpl(Critter critter, Program program){
		this.critter = critter;
		this.program = program;
	}
	
	public Outcome interpret(){
		
		
		return null;
		
	}
	
	@Override
	public Outcome interpret(Program p) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean eval(Condition c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int eval(Expr e) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
