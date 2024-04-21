package ast;


import symbols.Env;
import values.Value;

public interface Exp {
	public interface Visitor<T> {
		public T visit(ASTInt i,Env<Value> env);
		public T visit(ASTAdd e,Env<Value> env);
		public T visit(ASTMult e,Env<Value> env);
		public T visit(ASTDiv e,Env<Value> env);
		public T visit(ASTSub e,Env<Value> env);
		public T visit(ASTBool e,Env<Value> env);
		public T visit(ASTBoolNegate e,Env<Value> env);
		public T visit(ASTEqual e,Env<Value> env);
		public T visit(ASTLess e,Env<Value> env);
		public T visit(ASTGreater e,Env<Value> env);
		public T visit(ASTNotEqual e,Env<Value> env);
		public T visit(ASTAnd e,Env<Value> env);
		public T visit(ASTOr e,Env<Value> env);
		public T visit(ASTLet e,Env<Value> env);
		public T visit(ASTVar e,Env<Value> env);

	}
	
    public Value eval(Env<Value> env);

	public <T> T accept(Visitor<T> v,Env<Value> env);


	
	
}

