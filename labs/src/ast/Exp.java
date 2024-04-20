package ast;


import values.Value;

public interface Exp {
	public interface Visitor<T> {
		public T visit(ASTInt i);
		public T visit(ASTAdd e);
		public T visit(ASTMult e);
		public T visit(ASTDiv e);
		public T visit(ASTSub e);
		public T visit(ASTBool e);
		public T visit(ASTBoolNegate e);
		public T visit(ASTEqual e);
		public T visit(ASTLess e);
		public T visit(ASTGreater e);
		public T visit(ASTNotEqual e);
		public T visit(ASTAnd e);
		public T visit(ASTOr e);

	}
	
    public Value eval();
	
	public <T> T accept(Visitor<T> v);
	
	
}

