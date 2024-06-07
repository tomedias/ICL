package ast.Struct;

import ast.Exp;
import symbols.Env;
import types.TypingException;
import values.Value;

public class ASTWhile implements Exp {
    public Exp condition;
    public Exp body;

    public ASTWhile(Exp condition, Exp body) {
        this.condition = condition;
        this.body = body;
    }



    @Override
    public <T, E> T accept(Visitor<T, E> v, E env) throws TypingException {
        return v.visit(this, env);
    }
}
