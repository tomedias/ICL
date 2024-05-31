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
import types.*;
import values.Value;


public class CodeGen implements Exp.Visitor<Type,Env<Value>> {

	BasicBlock block = new BasicBlock();
	private String print = """
			invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
				invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
				""";

	@Override
	public Type visit(ASTInt i, Env<Value> env) {
		block.addInstruction(new SIPush(i.value));
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTAdd e, Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IAdd());
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTMult e,Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IMul());
		return IntType.singleton;
	}


	public Type visit(ASTDiv e, Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IDiv());
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTSub e,Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new ISub());
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTBool e, Env<Value> env) {
		if(e.value)
			block.addInstruction(new IBoolPush());
		else
			block.addInstruction(new NegativeIBool());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTBoolNegate e, Env<Value> env) {
		block.addInstruction(new NegativeIBool());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTEqual e,Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		BasicBlock.DelayedOp gotoIf = block.delayEmit();
		block.addInstruction(new NegativeIBool());
		BasicBlock.DelayedOp skipIf = block.delayEmit();
		String label = block.emitLabel();
		block.addInstruction(new IBoolPush());
		gotoIf.set(new IEqual(label));
		skipIf.set(new IGoTo(block.emitLabel()));
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTLess e, Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		BasicBlock.DelayedOp gotoIf = block.delayEmit();
		block.addInstruction(new NegativeIBool());
		BasicBlock.DelayedOp skipIf = block.delayEmit();
		String label = block.emitLabel();
		block.addInstruction(new IBoolPush());
		gotoIf.set(new ILess(label));
		skipIf.set(new IGoTo(block.emitLabel()));
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTGreater e, Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		BasicBlock.DelayedOp gotoIf = block.delayEmit();
		block.addInstruction(new NegativeIBool());
		BasicBlock.DelayedOp skipIf = block.delayEmit();
		String label = block.emitLabel();
		block.addInstruction(new IBoolPush());
		gotoIf.set(new IGreater(label));
		skipIf.set(new IGoTo(block.emitLabel()));

		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTNotEqual e,Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		BasicBlock.DelayedOp gotoIf = block.delayEmit();
		block.addInstruction(new NegativeIBool());
		BasicBlock.DelayedOp skipIf = block.delayEmit();
		String label = block.emitLabel();
		block.addInstruction(new IBoolPush());
		gotoIf.set(new INotEqual(label));
		skipIf.set(new IGoTo(block.emitLabel()));
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTAnd e, Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IAnd());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTOr e, Env<Value> env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IOr());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTLet e, Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTVar e,Env<Value> env) {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTBinding e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTAssign e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTReference e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTDereference e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTDotComma e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTWhile e, Env<Value> env) throws TypingException {
		String loop_label = block.emitLabel();
		e.condition.accept(this, env);
		BasicBlock.DelayedOp loopIf = block.delayEmit();
		Type type = e.body.accept(this, env);
		if(type != UnitType.singleton) {
			block.addInstruction(new IDiscard());
		}
		block.addInstruction(new IGoTo(loop_label));
		String break_label = block.emitLabel();
		loopIf.set(new IGoToIfFalse(break_label));
		return UnitType.singleton;
	}

	@Override
	public Type visit(ASTPrint e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTPrintln e, Env<Value> env) throws TypingException {
		//TODO
		return null;
	}

	@Override
	public Type visit(ASTIf e, Env<Value> env) throws TypingException {
		e.condition.accept(this,env);

		BasicBlock.DelayedOp gotoElse = block.delayEmit();
		Type ifType = e.thenBranch.accept(this,env);
		BasicBlock.DelayedOp popIF = block.delayEmit();
		popIF.set(new IComment(" pop if"));
		BasicBlock.DelayedOp GOTOENDIF = block.delayEmit();
		GOTOENDIF.set(new IComment("goto end"));
		BasicBlock.DelayedOp goToEnd = block.delayEmit();
		Type elseType =null;
		BasicBlock.DelayedOp popElse = null;
		BasicBlock.DelayedOp GOTOENDELSE = null;
	    if(e.elseBranch != null) {
			String else_label = block.emitLabel();
			gotoElse.set(new IGoToIfFalse(else_label));
			elseType = e.elseBranch.accept(this,env);
			popElse = block.delayEmit();
			popElse.set(new IComment(" pop else"));
			GOTOENDELSE = block.delayEmit();
			GOTOENDELSE.set(new IComment("goto end"));

		}else{
			gotoElse.set(new IGoToIfFalse("LabelEND"));
		}
		String go_if = block.emitLabel();
		goToEnd.set(new IGoTo(go_if));

		if(elseType==null && ifType == UnitType.singleton){
			popIF.set(new IDiscard());
		}

		if(elseType!=null && ifType!=elseType){
			popIF.set(new IDiscard());
			popElse.set(new IDiscard());
			GOTOENDELSE.set(new IGoTo("LabelEND"));
			GOTOENDIF.set(new IGoTo("LabelEND"));
		}

	   return (elseType==null) || (ifType!=elseType) ?  UnitType.singleton: ifType;
	}

	@Override
	public Type visit(ASTFun e, Env<Value> env) throws TypingException {
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
				LabelEND:
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