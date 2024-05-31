package ast;


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
import types.TypingException;
import values.Value;

public interface Exp {
	public interface Visitor<T,E> {
		public T visit(ASTInt i, E env);
		public T visit(ASTAdd e, E env) throws TypingException;
		public T visit(ASTMult e,E env) throws TypingException;
		public T visit(ASTDiv e, E env) throws TypingException;
		public T visit(ASTSub e,E env) throws TypingException;
		public T visit(ASTBool e, E env);
		public T visit(ASTBoolNegate e, E env) throws TypingException;
		public T visit(ASTEqual e,E env) throws TypingException;
		public T visit(ASTLess e, E env) throws TypingException;
		public T visit(ASTGreater e, E env) throws TypingException;
		public T visit(ASTNotEqual e,E env) throws TypingException;
		public T visit(ASTAnd e, E env) throws TypingException;
		public T visit(ASTOr e, E env) throws TypingException;
		public T visit(ASTLet e, E env) throws TypingException;
		public T visit(ASTVar e,E env) throws TypingException;
		public T visit(ASTBinding e, E env) throws TypingException;
		public T visit(ASTAssign e, E env) throws TypingException;
		public T visit(ASTReference e, E env) throws TypingException;
		public T visit(ASTDereference e, E env) throws TypingException;
		public T visit(ASTDotComma e, E env) throws TypingException;
		public T visit(ASTWhile e, E env) throws TypingException;
		public T visit(ASTPrint e, E env) throws TypingException;
		public T visit(ASTPrintln e, E env) throws TypingException;
		public T visit(ASTIf e, E env) throws TypingException;
		public T visit(ASTFun e, E env) throws TypingException;
	}
	
    public Value eval(Env<Value> env);

	public <T,E> T accept(Visitor<T,E> v,E env) throws TypingException, TypingException;





	
	
}

