package ast;

import symbols.Env;
import values.IntValue;
import values.Value;

public class ASTInt implements Exp {
    public int value;

    public ASTInt(int value) {
        this.value = value;
    }

    @Override
    public Value eval(Env<Value> env) {
        return new IntValue(value);
    }

    @Override
    public <T> T accept(Visitor<T> v,Env<Value> env) {
        return v.visit(this,env);
    }
}
