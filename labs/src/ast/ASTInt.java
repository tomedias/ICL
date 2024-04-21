package ast;

import symbols.Env;
import types.IntType;
import types.Type;
import types.TypingException;
import values.IntValue;
import values.Value;

public class ASTInt implements Exp {
    public int value;

    public ASTInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Value eval(Env<Value> env) {
        return new IntValue(value);
    }

    @Override
    public <T,E> T accept(Visitor<T,E> v,E env) {
        return v.visit(this,env);
    }




}
