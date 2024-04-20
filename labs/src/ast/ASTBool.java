package ast;
import symbols.Env;
import values.BoolValue;
import values.Value;
public class ASTBool implements Exp{
    public boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    @Override
    public Value eval(Env<Value> env) {
        return new BoolValue(value);
    }

    @Override
    public <T> T accept(Visitor<T> v,Env<Value> env) {
        return v.visit(this,env);
    }
}
