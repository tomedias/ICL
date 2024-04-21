package ast;

import symbols.Env;
import types.Type;
import types.TypingException;
import values.Value;

public class ASTBinding implements Exp{
    public ASTVar var;
    public Exp e1;

    public ASTBinding(ASTVar var, Exp e1) {
        this.var = var;
        this.e1 = e1;
    }

    @Override
    public Value eval(Env<Value> prev) {
        Env<Value> current = prev.beginScope();
        current.bind(var.var, e1.eval(prev));
        return e1.eval(current);
    }


    @Override
    public <T,E> T accept(Visitor<T,E> v, E env) throws TypingException {
        return v.visit(this,env);
    }


}
