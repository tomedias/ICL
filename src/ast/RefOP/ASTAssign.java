package ast.RefOP;

import ast.Exp;
import symbols.Env;
import types.TypingException;
import values.Value;

public class ASTAssign implements Exp {

    public Exp arg1;
    public Exp arg2;
    public ASTAssign(Exp arg1, Exp arg2){
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Value eval(Env<Value> env) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

    @Override
    public <T, E> T accept(Visitor<T, E> v, E env) throws TypingException {
        return v.visit(this,env);
    }
    
}
