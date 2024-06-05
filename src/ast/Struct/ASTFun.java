package ast.Struct;

import ast.Exp;
import ast.FunArgs;
import symbols.Env;
import types.Type;
import types.TypingException;
import values.Value;

import java.util.ArrayList;

public class ASTFun implements Exp {

    public ArrayList<FunArgs> args;
    public Exp body;
    public Type returnType;

    public ASTFun(ArrayList<FunArgs> args, Exp body, Type returnType) {
        this.args = args;
        this.body = body;
        this.returnType = returnType;
    }

    @Override
    public Value eval(Env<Value> env) {
        return null;
    }

    @Override
    public <T, E> T accept(Visitor<T, E> v, E env) throws TypingException, TypingException {
        return v.visit(this,env);
    }
}
