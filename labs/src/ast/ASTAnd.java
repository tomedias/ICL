package ast;

import values.BoolValue;
import values.Value;

public class ASTAnd implements Exp{
    public Exp arg1;
    public Exp arg2;

    public ASTAnd(Exp arg1, Exp arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Value eval() {
        return new BoolValue(((BoolValue)arg1.eval()).getValue() && ((BoolValue)arg2.eval()).getValue());
    }

    @Override
    public <T> T accept(Exp.Visitor<T> v) {
        return v.visit(this);
    }
}
