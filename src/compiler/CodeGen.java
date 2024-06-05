package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

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
import ast.String.ASTString;
import ast.Struct.*;
import symbols.Frame;
import symbols.FunctionInterface;
import target.*;
import types.*;
import values.FrameValue;
public class CodeGen implements Exp.Visitor<Type, Frame>{

	BasicBlock block = new BasicBlock();

	@Override
	public Type visit(ASTInt i, Frame env) {
		block.addInstruction(new SIPush(i.value));
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTAdd e, Frame env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IAdd());
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTMult e,Frame env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IMul());
		return IntType.singleton;
	}


	public Type visit(ASTDiv e, Frame env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IDiv());
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTSub e,Frame env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new ISub());
		return IntType.singleton;
	}

	@Override
	public Type visit(ASTBool e, Frame env) {
		if(e.value)
			block.addInstruction(new IBoolPush());
		else
			block.addInstruction(new NegativeIBool());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTBoolNegate e, Frame env) {
		block.addInstruction(new NegativeIBool());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTEqual e,Frame env) throws TypingException {
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
	public Type visit(ASTLess e, Frame env) throws TypingException {
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
	public Type visit(ASTGreater e, Frame env) throws TypingException {
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
	public Type visit(ASTNotEqual e,Frame env) throws TypingException {
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
	public Type visit(ASTAnd e, Frame env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IAnd());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTOr e, Frame env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		block.addInstruction(new IOr());
		return BoolType.singleton;
	}

	@Override
	public Type visit(ASTLet e, Frame env) throws TypingException {
		Frame frameBlock = env.beginScope();
		emitScope(block,frameBlock);
		Type returnType = UnitType.singleton;
		for(ASTBinding binding: e.bindings){
			if(returnType != UnitType.singleton){
				block.addInstruction(new IPop());
			}
			returnType = binding.accept(this,frameBlock);
		}
		e.e2.accept(this,frameBlock);
		emitEndScope(block,frameBlock);
		return returnType;
	}

	public static void emitEndScope(BasicBlock block , Frame frame){
		if(frame.getPrev() == null){
			block.addInstruction(new INull());
		}else{
			String name = frame.getName();
			String parent = frame.getPrev().getName();
			String parentType = String.format("L%s;",parent);
			block.addInstruction(new ILoad("1"));
			block.addInstruction(new IGetField(name,"SL",parentType));
		}
		block.addInstruction(new IStore("1"));
	}

	public static void emitScope(BasicBlock block,Frame frame){
		String name = frame.getName();
		String parent = frame.getPrev() == null ? "java/lang/Object" : frame.getPrev().getName();
		String parentType = String.format("L%s;",parent);
		initClass(block,name);
		block.addInstruction(new IDup());
		block.addInstruction(new ILoad("1"));
		block.addInstruction(new IPutField(name,"SL",parentType));
		block.addInstruction(new IStore("1"));
	}

	public static void initClass(BasicBlock block,String name){
		block.addInstruction(new INew(name));
		block.addInstruction(new IDup());
		block.addInstruction(new IInvokeSpecial(name));
	}

	@Override
	public Type visit(ASTVar e,Frame env) throws TypingException {
		FrameValue e1 = env.find(e.var);
		Frame x = env;
		block.addInstruction(new ILoad("1"));
		Type type = e1.getType();
		while(!x.getName().equals(e1.getFrame().getName())){
			block.addInstruction(new IGetField(x.getName(),"SL",String.format("L%s;",x.getPrev().getName())));
			x = x.getPrev();
		}
		block.addInstruction(new IGetField(e1.getFrame().getName(),String.format("v%s",e1.getVar_id()),e1.getType().getJvmType()));
		return type instanceof RefType ? ((RefType)type).refType : type;
	}

	@Override
	public Type visit(ASTBinding e, Frame env) throws TypingException {
		int var_id = env.getVars().size();
		Type type = e.e1.accept(this, env);
		FrameValue value = new FrameValue(env,var_id,type);
		block.addInstruction(new IDup());
		block.addInstruction(new ILoad("1"));
		block.addInstruction(new ISwap());
		block.addInstruction(new IPutField(env.getName(),String.format("v%s",var_id),type.getJvmType()));
		env.bind(e.var.var,value);
		return type;
	}

	@Override
	public Type visit(ASTAssign e, Frame env) throws TypingException {
		Type type = e.arg1.accept(this,env);
		Type refType = e.arg2.accept(this,env);
		block.addInstruction(new IPutField("ref_int","v","I"));
		return type;
	}

	@Override
	public Type visit(ASTReference e, Frame env) throws TypingException {
		block.addInstruction(new INew("ref_int"));
		block.addInstruction(new IDup());
		block.addInstruction(new IInvokeSpecial("ref_int"));
		block.addInstruction(new IDup());
		Type type = e.arg1.accept(this,env);
		block.addInstruction(new IPutField("ref_int","v","I"));
		return new RefType(type);
	}

	@Override
	public Type visit(ASTDereference e, Frame env) throws TypingException {
		Type type = e.arg1.accept(this,env);
		block.addInstruction(new IGetField("ref_int","v","I"));
		return type;
	}

	@Override
	public Type visit(ASTDotComma e, Frame env) throws TypingException {
		e.arg1.accept(this, env);
		e.arg2.accept(this, env);
		return UnitType.singleton;
	}

	@Override
	public Type visit(ASTWhile e, Frame env) throws TypingException {
		String loop_label = block.emitLabel();
		e.condition.accept(this, env);
		BasicBlock.DelayedOp loopIf = block.delayEmit();
		Type type = e.body.accept(this, env);
		if(type != UnitType.singleton) {
			block.addInstruction(new IPop());
		}
		block.addInstruction(new IGoTo(loop_label));
		String break_label = block.emitLabel();
		loopIf.set(new IGoToIfFalse(break_label));
		return UnitType.singleton;
	}

	@Override
	public Type visit(ASTPrint e, Frame env) throws TypingException {
		Type type = e.e1.accept(this, env);
		block.addInstruction(new CustomInstruction("getstatic java/lang/System/out Ljava/io/PrintStream;",null));
		block.addInstruction(new ISwap());
		if(type == IntType.singleton || type == BoolType.singleton) {
			block.addInstruction(new CustomInstruction("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;", null));
		}
		block.addInstruction(new IPrint());
		return UnitType.singleton;
	}

	@Override
	public Type visit(ASTPrintln e, Frame env) throws TypingException {
		Type type = e.e1.accept(this, env);
		block.addInstruction(new CustomInstruction("getstatic java/lang/System/out Ljava/io/PrintStream;",null));
		block.addInstruction(new ISwap());
		if(type == IntType.singleton || type == BoolType.singleton) {
			block.addInstruction(new CustomInstruction("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;", null));
		}
		block.addInstruction(new IPrintln());
		return UnitType.singleton;
	}

	@Override
	public Type visit(ASTIf e, Frame env) throws TypingException {
		e.condition.accept(this,env);

		BasicBlock.DelayedOp gotoIf= block.delayEmit();
		Type elseType =null;
		BasicBlock.DelayedOp popElse = null;

	    if(e.elseBranch != null) {
			elseType = e.elseBranch.accept(this,env);
			popElse = block.delayEmit();
			popElse.set(new IComment(" pop else"));
		}
		BasicBlock.DelayedOp goEnd = block.delayEmit();
		String label = block.emitLabel();
		gotoIf.set(new IGoToIfTrue(label));
		Type ifType = e.thenBranch.accept(this,env);
		BasicBlock.DelayedOp popIf = block.delayEmit();
		popIf.set(new IComment(" pop if"));
		String end = block.emitLabel();
		goEnd.set(new IGoTo(end));
		if(!ifType.equals(elseType) && !ifType.equals(UnitType.singleton)){
			if(elseType==null){
				popIf.set(new IPop());
			}
			else{
				popElse.set(new IPop());
			}
		}
	   return (elseType==null) || (ifType!=elseType) ?  UnitType.singleton: ifType;
	}


	@Override
	public Type visit(ASTFun e, Frame env) throws TypingException {
		return null;
	}

	@Override
	public Type visit(ASTFunCall e, Frame env) throws TypingException {
		return null;
	}

	@Override
	public Type visit(ASTString e, Frame env) throws TypingException {
		block.addInstruction(new ILdc("\""+e.value+"\""));
		return StringType.singleton;
	}


	public static BasicBlock codeGen(Exp e, Frame env) throws TypingException {
		CodeGen cg = new CodeGen();
		env.getAllFrames().add(env);
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
					   aconst_null
				 	   astore_1
					   ; setup local variables:
					   ;    1 - the PrintStream object held in java.lang.out
										          
				   """;
		String footer =
				"""
				return
				.end method
				""";
		StringBuilder sb = new StringBuilder(preamble);
		block.build(sb);

		sb.append(footer);
		return sb;

	}
	public static void dumpFrames(Frame frame, String file){
		try {
			PrintStream out = new PrintStream(new FileOutputStream(file+"/"+frame.getName()+".j"));
			out.println(".class " + frame.getName());
			out.println(".super java/lang/Object");
			if(frame.getPrev()==null){
				out.println(".field public SL L" + "java/lang/Object;");
			}else{
				out.println(".field public SL L" + frame.getPrev().getName() + ";");
			}
			for (int i = 0; i < frame.getVars().size(); i++) {
				out.println(".field public v" + i + " " + frame.getVars().get(i).getType().getJvmType());
			}
			out.println(".method public <init>()V");
			out.println("aload_0");
			out.println("invokenonvirtual java/lang/Object/<init>()V");
			out.println("return");
			out.println(".end method");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void dump_ref_int(){
		String dump = """
				.class public ref_int
				.super java/lang/Object
				.field public v I
				.method public <init>()V
				aload_0
				invokenonvirtual java/lang/Object/<init>()V
				return
				.end method
				""";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("main/ref_int.j"));
			out.print(dump);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void writeToFile(Exp e, String filename, Frame env) throws FileNotFoundException, TypingException {
		StringBuilder sb = genPreAndPost(codeGen(e,env));
		PrintStream out = new PrintStream(new FileOutputStream(filename));
		out.print(sb.toString());
		out.close();
	}

}