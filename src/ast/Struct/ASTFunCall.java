package ast.Struct;

import ast.ASTVar;
import ast.Exp;
import symbols.Env;
import types.TypingException;
import values.Value;

import java.util.ArrayList;

public class ASTFunCall implements Exp {
    public Exp funName;
    public ArrayList<Exp> args;

    public ASTFunCall(ASTVar funName, ArrayList<Exp> args) {
        this.funName = funName;
        this.args = args;
    }



    @Override
    public <T, E> T accept(Visitor<T, E> v, E env) throws TypingException, TypingException {
        return v.visit(this,env);
    }
}
