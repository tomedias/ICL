package ast;
import symbols.Env;
import types.BoolType;
import types.Type;
import types.TypingException;
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
    public <T,E> T accept(Visitor<T,E> v,E env) {
        return v.visit(this,env);
    }


}
