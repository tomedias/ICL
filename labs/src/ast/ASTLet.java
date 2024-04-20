package ast;

import values.Value;
import symbols.Env;

import java.util.ArrayList;

public class ASTLet implements Exp{
    private ArrayList<ASTBinding> bindings;
    private Exp e2;

    public ASTLet(ArrayList<ASTBinding> bindings, Exp e2) {
        this.bindings = bindings;
        this.e2 = e2;
    }

    @Override
    public Value eval(Env<Value> prev) {

        Env<Value> current = prev.beginScope();
        for (ASTBinding binding : bindings) {
            Value v1 = binding.eval(current);
            current.bind(binding.var, v1);
        }
        return e2.eval(current);
    }

    @Override
    public <T> T accept(Visitor<T> v,Env<Value> env) {
        return v.visit(this,env);
    }


}
