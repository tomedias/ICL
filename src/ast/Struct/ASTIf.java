package ast.Struct;

import ast.Exp;
import symbols.Env;
import types.TypingException;
import values.Value;

public class ASTIf implements Exp {
    public Exp condition;
    public Exp thenBranch;
    public Exp elseBranch;

    public ASTIf(Exp condition, Exp thenBranch, Exp elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }



    @Override
    public <T, E> T accept(Visitor<T, E> v, E env) throws TypingException {
        return v.visit(this, env);
    }
}
