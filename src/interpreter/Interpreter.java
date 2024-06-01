package interpreter;

import ast.*;
import ast.BoolOP.ASTAnd;
import ast.BoolOP.ASTBool;
import ast.BoolOP.ASTBoolNegate;
import ast.BoolOP.ASTOr;
import ast.NumOP.*;
import ast.RefOP.ASTAssign;
import ast.RefOP.ASTBinding;
import ast.RefOP.ASTDereference;
import ast.RefOP.ASTReference;
import ast.Struct.*;
import symbols.Env;
import types.Type;
import types.TypingException;
import values.*;


public class Interpreter implements Exp.Visitor<Value,Env<Value>> {

	@Override
	public IntValue visit(ASTInt e, Env<Value> env) {
		return new IntValue(e.value);
	}

	@Override
	public IntValue visit(ASTAdd e, Env<Value> env) throws TypingException {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() + ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public IntValue visit(ASTMult e,Env<Value> env) throws TypingException {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() * ((IntValue)(e.arg2.accept(this, env))).getValue());
	}
	@Override
	public IntValue visit(ASTDiv e, Env<Value> env) throws TypingException {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() / ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public IntValue visit(ASTSub e,Env<Value> env) throws TypingException {
		return new IntValue (((IntValue)(e.arg1.accept(this, env))).getValue() - ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTBool e, Env<Value> env) {
		return new BoolValue(e.value);
	}

	@Override
	public BoolValue visit(ASTBoolNegate e, Env<Value> env) throws TypingException {
		return new BoolValue(!((BoolValue)(e.arg1.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTEqual e,Env<Value> env) throws TypingException {
		return new BoolValue((e.arg1.accept(this, env)).equals(e.arg2.accept(this, env)));
	}

	@Override
	public BoolValue visit(ASTLess e, Env<Value> env) throws TypingException {
		return new BoolValue(((IntValue)(e.arg1.accept(this, env))).getValue() < ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTGreater e, Env<Value> env) throws TypingException {
		return new BoolValue(((IntValue)(e.arg1.accept(this, env))).getValue() > ((IntValue)(e.arg2.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTNotEqual e,Env<Value> env) throws TypingException {
		return new BoolValue(!(e.arg1.accept(this, env)).equals(e.arg2.accept(this, env)));
	}

	@Override
	public BoolValue visit(ASTAnd e, Env<Value> env) throws TypingException {
		return new BoolValue(((BoolValue)(e.arg1.accept(this, env))).getValue() && ((BoolValue)(e.arg1.accept(this, env))).getValue());
	}

	@Override
	public BoolValue visit(ASTOr e, Env<Value> env) throws TypingException {
		return new BoolValue(((BoolValue)(e.arg1.accept(this, env))).getValue() || ((BoolValue)(e.arg1.accept(this, env))).getValue());
	}

	@Override
	public Value visit(ASTLet e, Env<Value> env) throws TypingException {
		Env<Value> current = env.beginScope();
		for (ASTBinding binding : e.bindings) {
			Value v1 = binding.accept(this,current);
			current.bind(binding.var.var, v1);
		}
		return e.e2.accept(this, current);
	}

	@Override
	public Value visit(ASTVar e,Env<Value> env) {
        return env.find(e.var);
	}

	@Override
	public Value visit(ASTBinding e, Env<Value> env) throws TypingException {
		Env<Value> current = env.beginScope();
		Value val = e.e1.accept(this, current);
		current.bind(e.var.var, val);
		return val;
	}

	@Override
	public Value visit(ASTAssign e, Env<Value> env) throws TypingException {
		RefValue ref = (RefValue) e.arg1.accept(this,env);
		var value = e.arg2.accept(this,env);
		ref.setValue(value);
		return value;

	}

	@Override
	public Value visit(ASTReference e, Env<Value> env) throws TypingException {
		return new RefValue(e.arg1.accept(this,env));
	}

	@Override
	public Value visit(ASTDereference e, Env<Value> env) throws TypingException {
		RefValue ref = (RefValue) e.arg1.accept(this,env);
		return ref.getValue();
	}

	@Override
	public Value visit(ASTDotComma e, Env<Value> env) throws TypingException {
		e.arg1.accept(this,env);
		e.arg2.accept(this,env);
		return UnitValue.singleton;
	}

	@Override
	public Value visit(ASTWhile e, Env<Value> env) throws TypingException {
		while(((BoolValue)(e.condition.accept(this,env))).getValue()){
			e.body.accept(this,env);
		}
		return UnitValue.singleton;
	}

	@Override
	public Value visit(ASTPrint e, Env<Value> env) throws TypingException {
		System.out.print(e.e1.accept(this,env));
		return UnitValue.singleton;
	}

	@Override
	public Value visit(ASTPrintln e, Env<Value> env) throws TypingException {
		System.out.println(e.e1.accept(this,env));
		return UnitValue.singleton;
	}

	@Override
	public Value visit(ASTIf e, Env<Value> env) throws TypingException {
		if(((BoolValue)(e.condition.accept(this,env))).getValue()){
			e.thenBranch.accept(this,env);
		}
		else{
			e.elseBranch.accept(this,env);
		}
		return UnitValue.singleton;
	}

	@Override
	public Value visit(ASTFun e, Env<Value> env) throws TypingException {
		Env<Value> current = env.beginScope();
		for (FunArgs<String, Type> arg  : e.args) {
//			Value v1 = arg.accept(this,current);
//			current.bind(binding.var.var, v1);
		}
		return null;
	}

	public static Value interpret(Exp e, Env<Value> env) throws TypingException {
		Interpreter i = new Interpreter();
		return e.accept(i,env);
	}


	
	
	
}
