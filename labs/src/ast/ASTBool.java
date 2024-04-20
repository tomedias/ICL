package ast;
import values.BoolValue;
import values.Value;
public class ASTBool implements Exp{
    public boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    @Override
    public Value eval() {
        return new BoolValue(value);
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
