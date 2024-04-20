package ast;

import values.BoolValue;
import values.Value;

public class ASTBoolNegate implements Exp {
    public Exp value;

    public ASTBoolNegate(Exp value) {
        this.value = value;
    }
    @Override
    public Value eval() {
        BoolValue var = (BoolValue)value.eval();
        return new BoolValue(!var.getValue());
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
