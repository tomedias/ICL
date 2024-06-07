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
import symbols.FunctionManager;
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
		LateInstruction gotoIf = block.delayOperation();
		block.addInstruction(new NegativeIBool());
		LateInstruction skipIf = block.delayOperation();
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
		LateInstruction gotoIf = block.delayOperation();
		block.addInstruction(new NegativeIBool());
		LateInstruction skipIf = block.delayOperation();
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
		LateInstruction gotoIf = block.delayOperation();
		block.addInstruction(new NegativeIBool());
		LateInstruction skipIf = block.delayOperation();
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
		LateInstruction gotoIf = block.delayOperation();
		block.addInstruction(new NegativeIBool());
		LateInstruction skipIf = block.delayOperation();
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
		if(refType.equals(IntType.singleton) || refType.equals(BoolType.singleton)){
			block.addInstruction(new IPutField("ref_int","v","I"));
		}
		else
			block.addInstruction(new IPutField("ref_ref","v","Ljava/lang/Object;"));
		return type;
	}

	@Override
	public Type visit(ASTReference e, Frame env) throws TypingException {
		LateInstruction newRef = block.delayOperation();
		block.addInstruction(new IDup());
		LateInstruction invokespecial = block.delayOperation();
		block.addInstruction(new IDup());
		Type type = e.arg1.accept(this,env);
		String class_name;
		String field_type;
		if(type == IntType.singleton || type == BoolType.singleton){
			class_name = "ref_int";
			field_type = "I";
		}else{
			class_name = "ref_ref";
			field_type = "Ljava/lang/Object;";
		}
		newRef.set(new INew(class_name));
		invokespecial.set(new IInvokeSpecial(class_name));
		block.addInstruction(new IPutField(class_name,"v",field_type));
		return new RefType(type,class_name);
	}

	@Override
	public Type visit(ASTDereference e, Frame env) throws TypingException {
		Type type = e.arg1.accept(this,env);
		System.out.println(type.toString());
		String class_name;
		String field_type;
		if(type == IntType.singleton || type == BoolType.singleton){
			class_name = "ref_int";
			field_type = "I";
		}else{
			class_name = "ref_ref";
			field_type = "Ljava/lang/Object;";
		}
		block.addInstruction(new IGetField(class_name,"v",field_type));
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
		LateInstruction loopIf = block.delayOperation();
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
		InvokePrint(env, e.e1);
		block.addInstruction(new IPrint());
		return UnitType.singleton;
	}

	@Override
	public Type visit(ASTPrintln e, Frame env) throws TypingException {
		InvokePrint(env, e.e1);
		block.addInstruction(new IPrintln());
		return UnitType.singleton;
	}

	private void InvokePrint(Frame env, Exp e1) throws TypingException {
		Type type = e1.accept(this, env);
		block.addInstruction(new CustomInstruction("getstatic java/lang/System/out Ljava/io/PrintStream;",null));
		block.addInstruction(new ISwap());
		if(type == IntType.singleton || type == BoolType.singleton) {
			block.addInstruction(new CustomInstruction("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;", null));
		}
	}

	@Override
	public Type visit(ASTIf e, Frame env) throws TypingException {
		e.condition.accept(this,env);

		LateInstruction gotoIf= block.delayOperation();
		Type elseType =null;
		LateInstruction popElse = null;

	    if(e.elseBranch != null) {
			elseType = e.elseBranch.accept(this,env);
			popElse = block.delayOperation();
			popElse.set(new IComment(" pop else"));
		}
		LateInstruction goEnd = block.delayOperation();
		String label = block.emitLabel();
		gotoIf.set(new IGoToIfTrue(label));
		Type ifType = e.thenBranch.accept(this,env);
		LateInstruction popIf = block.delayOperation();
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
		ArrayList<Type> argTypes = new ArrayList<>();
		for(FunArgs arg : e.args){
			argTypes.add(arg.type());
		}
		FunType funType = new FunType(e.returnType,argTypes);
		Frame functionFrame = env.beginScope();
		for(FunArgs arg : e.args){
			FrameValue value = new FrameValue(functionFrame,functionFrame.getVars().size(),arg.type());
			functionFrame.bind(arg.name(),value);
		}
		env.endScope();
		FunctionManager manager = FunctionManager.singleton;
		FunctionManager.FunctionInterface functionInterface = manager.getFunctionInterface(funType);
		funType.setInterfaceName(functionInterface.name());
		FunctionManager.Function function = manager.addFunction(functionInterface,env,functionFrame,e.body);
		block.addInstruction(new INew("closure_"+function.id()));
		block.addInstruction(new IDup());
		block.addInstruction(new IInvokeSpecial("closure_"+function.id()));
		block.addInstruction(new IDup());
		block.addInstruction(new ILoad("1"));
		block.addInstruction(new IPutField("closure_"+function.id(),"SL",String.format("L%s;",env.getName())));
		return funType;
	}

	@Override
	public Type visit(ASTFunCall e, Frame env) throws TypingException {
		Type funType = e.funName.accept(this,env);
		for(Exp arg : e.args){
			arg.accept(this,env);
		}
		FunctionManager.FunctionInterface functionInterface = FunctionManager.singleton.getFunctionInterface((FunType)funType);
		String arg1 = String.format("closure_interface_%s/apply(%s)%s",functionInterface.name(),String.join("",functionInterface.parameterType()),functionInterface.retType());
		block.addInstruction(new IInvokeInterface(arg1,String.valueOf(functionInterface.parameterType().size()+1)));
		return ((FunType)funType).returnType;
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

	public static void dumpFunctions(){
		for(FunctionManager.Function f : FunctionManager.singleton.getFunctions()){
			try {
				PrintStream out = new PrintStream(new FileOutputStream("output/"+"closure_"+f.id()+".j"));
				String dump = String.format("""
				.class public closure_%d
				.super java/lang/Object
				.implements closure_interface_%s
				.field public SL L%s;
				.method public <init>()V
				aload_0
				invokenonvirtual java/lang/Object/<init>()V
				return
				.end method
				.method public apply(%s)%s
				.limit locals %d
				.limit stack 256
				""",f.id(),f.functionInterface().name(),f.frame().getName(),String.join("",f.functionInterface().parameterType()),f.functionInterface().retType(), Math.max(f.functionInterface().parameterType().size()+1,10));
				out.println(dump);
				StringBuilder loadParams = getLoadParams(f);
				BasicBlock block = codeGen(f.body(),f.functionFrame());
				out.println("new "+f.functionFrame().getName());
				out.println("dup");
				out.println("invokespecial "+f.functionFrame().getName()+"/<init>()V");
				out.println("dup");
				out.println("aload_0");
				out.println("getfield closure_"+f.id()+"/SL L"+f.frame().getName()+";");
				out.println("putfield "+f.functionFrame().getName()+"/SL L"+f.frame().getName()+";");
				out.println(loadParams.toString());
				out.println(block.toString());
				if(f.functionInterface().retType().equals("I")) {
					out.println("ireturn");
				}else if(f.functionInterface().retType().equals("V")){
					out.println("return");
				}else{
					out.println("areturn");
				}
				out.print(".end method");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (TypingException e) {
                throw new RuntimeException(e);
            }
        }
	}

	private static StringBuilder getLoadParams(FunctionManager.Function f) {
		FunctionManager.FunctionInterface functionInterface = f.functionInterface();
		StringBuilder loadParams = new StringBuilder();
		for(int i = 0; i < functionInterface.parameterType().size(); i++){
			String type = functionInterface.parameterType().get(i);
			loadParams.append("dup\n");
			if(type.equals("I")) {
				loadParams.append(String.format("iload %d\n", i + 1));
			}else{
				loadParams.append(String.format("aload %d\n", i + 1));
			}
			loadParams.append(String.format("putfield %s/v%d %s\n", f.functionFrame().getName(), i, type));
		}
		loadParams.append(String.format("astore_%d",1));
		return loadParams;
	}

	public static void dump_interfaces(){
		for(FunctionManager.FunctionInterface f : FunctionManager.singleton.getFunctionInterfaces().values()){
			try {
				PrintStream out = new PrintStream(new FileOutputStream("output/"+"closure_interface_"+f.name()+".j"));
				String dump = String.format("""
				.interface public closure_interface_%s
				.super java/lang/Object
				.method public abstract apply(%s)%s
				.end method
				""",f.name(),String.join("",f.parameterType()),f.retType());
				out.println(dump);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
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
			PrintStream out = new PrintStream(new FileOutputStream("output/ref_int.j"));
			out.print(dump);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void dump_ref_ref(){
		String dump = """
				.class public ref_ref
				.super java/lang/Object
				.field public v Ljava/lang/Object;
				.method public <init>()V
				aload_0
				invokenonvirtual java/lang/Object/<init>()V
				return
				.end method
				""";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("output/ref_ref.j"));
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