package ast;

import values.IntValue;
import values.Value;

public class ASTInt implements Exp {
    public int value;

    public ASTInt(int value) {
        this.value = value;
    }

    @Override
    public Value eval() {
        return new IntValue(value);
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
