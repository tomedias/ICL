package ast;
import symbols.Env;
import types.Type;
import types.TypingException;
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
    public Value eval(Env<Value> env) {
        return new BoolValue(arg1.eval(env).equals(arg2.eval(env)));
    }

    @Override
    public <T,E> T accept(Visitor<T,E> v,E env) throws TypingException {
        return v.visit(this,env);
    }


}
