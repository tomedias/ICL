package ast.Struct;

import ast.Exp;
import symbols.Env;
import types.TypingException;
import values.Value;

public class ASTPrint implements Exp {
    public Exp e1;

    public ASTPrint(Exp e1) {
        this.e1 = e1;
    }



    @Override
    public <T, E> T accept(Visitor<T, E> v, E env) throws TypingException {
        return v.visit(this,env);
    }
}
