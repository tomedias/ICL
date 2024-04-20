package interpreter;

import ast.*;
import values.BoolValue;
import values.IntValue;
import values.Value;


public class Interpreter implements Exp.Visitor<Value> {

	@Override
	public IntValue visit(ASTInt e) {
		return (IntValue) e.eval();
	}

	@Override
	public IntValue visit(ASTAdd e) {
		return (IntValue) e.eval();
	}

	@Override
	public IntValue visit(ASTMult e) {
		return (IntValue) e.eval();
	}
	@Override
	public IntValue visit(ASTDiv e) {
		return (IntValue) e.eval();
	}

	@Override
	public IntValue visit(ASTSub e) {
		return (IntValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTBool e) {
		return (BoolValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTBoolNegate e) {
		return (BoolValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTEqual e) {
		return (BoolValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTLess e) {
		return (BoolValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTGreater e) {
		return (BoolValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTNotEqual e) {
		return (BoolValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTAnd e) {
		return (BoolValue) e.eval();
	}

	@Override
	public BoolValue visit(ASTOr e) {
		return (BoolValue) e.eval();
	}


	public static Value interpret(Exp e) {
		Interpreter i = new Interpreter();
		return e.accept(i);
		
	}
	

	
	
	
}
