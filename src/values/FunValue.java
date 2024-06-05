package values;

import ast.Exp;
import ast.FunArgs;
import symbols.Env;
import types.Type;

import java.util.ArrayList;

public class FunValue implements Value {
    private ArrayList<FunArgs> args;
    private Exp body;
    private Type returnType;
    private Env<Value> environment;

    public FunValue(ArrayList<FunArgs> args, Exp body, Type returnType,Env<Value> env) {
        this.args = args;
        this.body = body;
        this.returnType = returnType;
        this.environment = env;
    }

    public ArrayList<FunArgs> getArgs() {
        return args;
    }

    public Exp getBody() {
        return body;
    }

    public Env<Value> getEnvironment() {
        return environment;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "fun";
    }
}
