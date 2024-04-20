package interpreter;

import ast.*;
import symbols.Env;
import values.BoolValue;
import values.IntValue;
import values.Value;


public class Interpreter implements Exp.Visitor<Value> {

	@Override
	public IntValue visit(ASTInt e, Env<Value> env) {
		return (IntValue) e.eval(env);
	}

	@Override
	public IntValue visit(ASTAdd e,Env<Value> env) {
		return (IntValue) e.eval(env);
	}

	@Override
	public IntValue visit(ASTMult e,Env<Value> env) {
		return (IntValue) e.eval(env);
	}
	@Override
	public IntValue visit(ASTDiv e,Env<Value> env) {
		return (IntValue) e.eval(env);
	}

	@Override
	public IntValue visit(ASTSub e,Env<Value> env) {
		return (IntValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTBool e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTBoolNegate e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTEqual e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTLess e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTGreater e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTNotEqual e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTAnd e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public BoolValue visit(ASTOr e,Env<Value> env) {
		return (BoolValue) e.eval(env);
	}

	@Override
	public Value visit(ASTLet e,Env<Value> env) {
		return e.eval(env);
	}

	@Override
	public Value visit(ASTVar e,Env<Value> env) {
		return e.eval(env);
	}


	public static Value interpret(Exp e, Env<Value> env) {
		Interpreter i = new Interpreter();
		return e.accept(i,env);
		
	}
	

	
	
	
}
