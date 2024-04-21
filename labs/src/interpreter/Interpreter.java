package interpreter;

import ast.*;
import symbols.Env;
import values.BoolValue;
import values.IntValue;
import values.Value;


public class Interpreter implements Exp.Visitor<Value> {

	@Override
	public IntValue visit(ASTInt e, Env<Value> env) {
		return new IntValue(e.value);
	}

	@Override
	public IntValue visit(ASTAdd e,Env<Value> env) {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() + ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public IntValue visit(ASTMult e,Env<Value> env) {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() * ((IntValue)(e.arg2.accept(this, env))).getValue());
	}
	@Override
	public IntValue visit(ASTDiv e,Env<Value> env) {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() / ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public IntValue visit(ASTSub e,Env<Value> env) {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() - ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTBool e,Env<Value> env) {
		return new BoolValue(e.value);
	}

	@Override
	public BoolValue visit(ASTBoolNegate e,Env<Value> env) {
		return new BoolValue(!((BoolValue)(e.value.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTEqual e,Env<Value> env) {
		return new BoolValue((e.arg1.accept(this, env)).equals(e.arg1.accept(this, env)));
	}

	@Override
	public BoolValue visit(ASTLess e,Env<Value> env) {
		return new BoolValue(((IntValue)(e.arg1.accept(this, env))).getValue() < ((IntValue)(e.arg1.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTGreater e,Env<Value> env) {
		return new BoolValue(((IntValue)(e.arg1.accept(this, env))).getValue() > ((IntValue)(e.arg1.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTNotEqual e,Env<Value> env) {
		return new BoolValue(!(e.arg1.accept(this, env)).equals(e.arg1.accept(this, env)));
	}

	@Override
	public BoolValue visit(ASTAnd e,Env<Value> env) {
		return new BoolValue(((BoolValue)(e.arg1.accept(this, env))).getValue() && ((BoolValue)(e.arg1.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTOr e,Env<Value> env) {
		return new BoolValue(((BoolValue)(e.arg1.accept(this, env))).getValue() || ((BoolValue)(e.arg1.accept(this, env))).getValue());
	}

	@Override
	public Value visit(ASTLet e,Env<Value> env) {
		Env<Value> current = env.beginScope();
		for (ASTBinding binding : e.bindings) {
			Value v1 = binding.accept(this,current);
			current.bind(binding.var, v1);
		}
		return e.e2.accept(this, current);
	}

	@Override
	public Value visit(ASTVar e,Env<Value> env) {
        return env.find(e.var);
	}


	public static Value interpret(Exp e, Env<Value> env) {
		Interpreter i = new Interpreter();
		return e.accept(i,env);
		
	}
	

	
	
	
}
