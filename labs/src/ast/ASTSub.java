package ast;

import values.IntValue;
import values.Value;

public class ASTSub implements Exp {
    public Exp arg1;
    public Exp arg2;

    public ASTSub(Exp arg1, Exp arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Value eval() {
        return new IntValue(((IntValue)arg1.eval()).getValue() - ((IntValue)arg2.eval()).getValue());
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
