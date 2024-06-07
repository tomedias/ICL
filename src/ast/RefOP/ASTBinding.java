package ast.RefOP;

import ast.ASTVar;
import ast.Exp;
import symbols.Env;
import types.TypingException;
import values.Value;

public class ASTBinding implements Exp {
    public ASTVar var;
    public Exp e1;

    public ASTBinding(ASTVar var, Exp e1) {
        this.var = var;
        this.e1 = e1;
    }




    @Override
    public <T,E> T accept(Visitor<T,E> v, E env) throws TypingException {
        return v.visit(this,env);
    }


}
