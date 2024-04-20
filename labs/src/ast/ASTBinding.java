package ast;

import symbols.Env;
import values.Value;

public class ASTBinding implements Exp{
    public String var;
    public Exp e1;

    public ASTBinding(String var, Exp e1) {
        this.var = var;
        this.e1 = e1;
    }

    @Override
    public Value eval(Env<Value> prev) {
        Env<Value> current = prev.beginScope();
        current.bind(var, e1.eval(prev));
        return e1.eval(current);
    }

    public String getVar() {
        return var;
    }

    @Override
    public <T> T accept(Visitor<T> v, Env<Value> env) {
        return null;
    }
}
