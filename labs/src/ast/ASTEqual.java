package ast;
import values.BoolValue;
import values.Value;
public class ASTEqual implements Exp{
    public Exp arg1;
    public Exp arg2;

    public ASTEqual(Exp arg1, Exp arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Value eval() {
        return new BoolValue(arg1.eval().equals(arg2.eval()));
    }

    @Override
    public <T> T accept(Exp.Visitor<T> v) {
        return v.visit(this);
    }
}
