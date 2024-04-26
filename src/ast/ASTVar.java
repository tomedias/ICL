package ast;

import symbols.Env;
import types.Type;
import types.TypingException;
import values.Value;

public class ASTVar implements Exp{
    public String var;
    public Env<Value> env;

    public ASTVar(String var) {
        this.var = var;
    }

    @Override
    public Value eval(Env<Value> env) {
        return env.find(var);
    }

    @Override
    public <T,E> T accept(Visitor<T,E> visitor,E env) throws TypingException {
        return visitor.visit(this,env);
    }


    @Override
    public String toString() {
        return var  ;
    }
}
