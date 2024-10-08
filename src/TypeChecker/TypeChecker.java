package TypeChecker;

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
import symbols.Env;
import types.*;

import java.util.ArrayList;


public class TypeChecker implements Exp.Visitor<Type,Env<Type>>{
    @Override
    public Type visit(ASTInt i, Env<Type> env) {
        return IntType.singleton;
    }

    @Override
    public Type visit(ASTAdd e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1!=arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return IntType.singleton;
    }

    @Override
    public Type visit(ASTMult e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1!=arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return IntType.singleton;
    }

    @Override
    public Type visit(ASTDiv e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1!=arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return IntType.singleton;
    }

    @Override
    public Type visit(ASTSub e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1!=arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return IntType.singleton;
    }

    @Override
    public Type visit(ASTBool e, Env<Type> env) {
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTBoolNegate e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        if(arg1 != BoolType.singleton ){
            throw new TypingException(String.format("Operator cannot be applied to %s",arg1.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTEqual e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTLess e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTGreater e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTNotEqual e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTLessEq e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTGreaterEq e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != IntType.singleton || arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTAnd e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != BoolType.singleton || arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTOr e, Env<Type> env) throws TypingException {
        Type arg1 = e.arg1.accept(this, env);
        Type arg2 = e.arg2.accept(this, env);
        if(arg1 != BoolType.singleton || arg1 != arg2){
            throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        }
        return BoolType.singleton;
    }

    @Override
    public Type visit(ASTLet e, Env<Type> env) throws TypingException {
        for (ASTBinding bind : e.bindings){
            bind.accept(this,env);
        }
        return e.e2.accept(this, env);
    }

    @Override
    public Type visit(ASTVar e, Env<Type> env) throws TypingException {
        if(e.var.isEmpty() || Character.isDigit(e.var.charAt(0))){
            throw new TypingException("Type error: expected String as variable with no digits in the beginning");
        }
        return env.find(e.var);
    }

    @Override
    public Type visit(ASTBinding e, Env<Type> env) throws TypingException {
        Type type = e.e1.accept(this, env);
        env.bind(e.var.var,type);
        //if(e.var.accept(this,env) != type){
          //  throw new TypingException(String.format("Operator cannot be applied to %s, %s",arg1.toString(),arg2.toString()));
        //}
        return type;
    }

    @Override
    public Type visit(ASTAssign e, Env<Type> env) throws TypingException {
        Type typeVar = e.arg1.accept(this, env);
        if(! (typeVar instanceof RefType) || ((RefType)typeVar).refType != e.arg2.accept(this, env)){
            throw new TypingException("Type error: expected RefType");
        }
        return typeVar;
    }

    @Override
    public Type visit(ASTReference e, Env<Type> env) throws TypingException {
        return new RefType(e.arg1.accept(this, env));
    }

    @Override
    public Type visit(ASTDereference e, Env<Type> env) throws TypingException {
        if(!(e.arg1.accept(this, env) instanceof RefType)){
            throw new TypingException("Type error: expected RefType");
        }
        return ((RefType)e.arg1.accept(this, env)).refType;
    }

    @Override
    public Type visit(ASTDotComma e, Env<Type> env) throws TypingException {
        e.arg1.accept(this,env);
        e.arg2.accept(this,env);
        return UnitType.singleton;
    }

    @Override
    public Type visit(ASTWhile e, Env<Type> env) throws TypingException {
        e.condition.accept(this,env);
        e.body.accept(this,env);
        return UnitType.singleton;
    }

    @Override
    public Type visit(ASTPrint e, Env<Type> env) throws TypingException {
        e.e1.accept(this,env);
        return UnitType.singleton;
    }

    @Override
    public Type visit(ASTPrintln e, Env<Type> env) throws TypingException {
        e.e1.accept(this,env);
        return UnitType.singleton;
    }

    @Override
    public Type visit(ASTIf e, Env<Type> env) throws TypingException {
        Type type = e.thenBranch.accept(this,env);
        if(e.elseBranch!=null){
            if(type!= e.elseBranch.accept(this,env)){
                return UnitType.singleton;
            }
        }
        return type;
    }

    @Override
    public Type visit(ASTFun e, Env<Type> env) throws TypingException {
        ArrayList<Type> types = new ArrayList<>();
        for(FunArgs arg : e.args){
           types.add(arg.type());
        }
        return new FunType(e.returnType,types);
    }

    @Override
    public Type visit(ASTFunCall e, Env<Type> env) throws TypingException {
        Type funType = e.funName.accept(this,env);
        if(!(funType instanceof FunType)){
            throw new TypingException("Type error: expected FunType");
        }
        FunType fun = (FunType) funType;
        if(fun.args.size() != e.args.size()){
            throw new TypingException("Type error: expected same number of arguments");
        }
        for(int i = 0; i < e.args.size(); i++){
            if(fun.args.get(i) != e.args.get(i).accept(this,env)){
                throw new TypingException("Type error: expected same type of arguments");
            }
        }
        return fun.returnType;
    }

    @Override
    public Type visit(ASTString e, Env<Type> env) throws TypingException {
        return StringType.singleton;
    }

    public static Type typeChecker(Exp e, Env<Type> env) throws TypingException {
        TypeChecker i = new TypeChecker();
        return e.accept(i,env);
    }
}
