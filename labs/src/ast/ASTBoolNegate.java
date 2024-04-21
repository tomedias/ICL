package ast;

import symbols.Env;
import types.Type;
import types.TypingException;
import values.BoolValue;
import values.Value;

public class ASTBoolNegate implements Exp {
    public Exp arg1;

    public ASTBoolNegate(Exp arg1) {
        this.arg1 = arg1;
    }
    @Override
    public Value eval(Env<Value> env) {
        BoolValue var = (BoolValue)arg1.eval(env);
        return new BoolValue(!var.getValue());
    }

    @Override
    public <T,E> T accept(Visitor<T,E> v,E env) throws TypingException {
        return v.visit(this,env);
    }


}
