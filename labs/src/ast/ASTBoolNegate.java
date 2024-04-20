package ast;

import symbols.Env;
import values.BoolValue;
import values.Value;

public class ASTBoolNegate implements Exp {
    public Exp value;

    public ASTBoolNegate(Exp value) {
        this.value = value;
    }
    @Override
    public Value eval(Env<Value> env) {
        BoolValue var = (BoolValue)value.eval(env);
        return new BoolValue(!var.getValue());
    }

    @Override
    public <T> T accept(Visitor<T> v,Env<Value> env) {
        return v.visit(this,env);
    }
}
