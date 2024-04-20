package ast;


import symbols.Env;
import values.IntValue;
import values.Value;

public class ASTMult implements Exp {
	public Exp arg1;
	public Exp arg2;
	
	public ASTMult(Exp arg1, Exp arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	@Override
	public Value eval(Env<Value> env) {
		return new IntValue(((IntValue)arg1.eval(env)).getValue() * ((IntValue)arg2.eval(env)).getValue());
	}

	@Override
	public <T> T accept(Visitor<T> v,Env<Value> env) {
		return v.visit(this,env);
	}
}
