package ast.Struct;

import ast.Exp;
import ast.RefOP.ASTBinding;
import types.TypingException;
import values.Value;
import symbols.Env;

import java.util.ArrayList;

public class ASTLet implements Exp {
    public ArrayList<ASTBinding> bindings;
    public Exp e2;

    public ASTLet(ArrayList<ASTBinding> bindings, Exp e2) {
        this.bindings = bindings;
        this.e2 = e2;
    }



    @Override
    public <T,E> T accept(Visitor<T,E> v,E env) throws TypingException {
        return v.visit(this,env);
    }




}
