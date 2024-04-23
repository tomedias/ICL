package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


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
import target.*;
import types.TypingException;
import values.Value;


public class CodeGen implements Exp.Visitor<Void,Env<Value>> {
	
	BasicBlock block = new BasicBlock();
	
	
	@Override
	public Void visit(ASTInt i, Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Void visit	(ASTAdd e, Env<Value> env) throws TypingException {
		//TODO
	    return null;
	}

	@Override
	public Void visit(ASTMult e,Env<Value> env) throws TypingException {
		//TODO
		return null;
	}


	public Void visit(ASTDiv e, Env<Value> env) throws TypingException {
		//TODO
	    return null;
	}

	@Override
	public Void visit(ASTSub e,Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTBool e, Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTBoolNegate e, Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTEqual e,Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTLess e, Env<Value> env) {
		//TODO

		return null;
	}

	@Override
	public Void visit(ASTGreater e, Env<Value> env) {
		//TODO

		return null;
	}

	@Override
	public Void visit(ASTNotEqual e,Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTAnd e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTOr e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTLet e, Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTVar e,Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTBinding e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTAssign e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTReference e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTDereference e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTDotComma e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTWhile e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTPrint e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTPrintln e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Void visit(ASTIf e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}


	public static BasicBlock codeGen(Exp e, Env<Value> env) throws TypingException {
		CodeGen cg = new CodeGen();
		e.accept(cg,env);
		return cg.block;
	}
	
	
	private static StringBuilder genPreAndPost(BasicBlock block) {
		String preamble = """
					  .class public Demo
					  .super java/lang/Object 
					  .method public <init>()V
					     aload_0
					     invokenonvirtual java/lang/Object/<init>()V
					     return
					  .end method
					  .method public static main([Ljava/lang/String;)V
					   .limit locals 10
					   .limit stack 256
					   ; setup local variables:
					   ;    1 - the PrintStream object held in java.lang.out
					  getstatic java/lang/System/out Ljava/io/PrintStream;					          
				   """;
		String footer = 
				"""
				invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
				invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
				return
				.end method
				""";
		StringBuilder sb = new StringBuilder(preamble);
		block.build(sb);
		sb.append(footer);
		return sb;
			
	}

	
	public static void writeToFile(Exp e, String filename, Env<Value> env) throws FileNotFoundException, TypingException {
	    StringBuilder sb = genPreAndPost(codeGen(e,env));
	    PrintStream out = new PrintStream(new FileOutputStream(filename));
	    out.print(sb.toString());
	    out.close();
		
	}

}
