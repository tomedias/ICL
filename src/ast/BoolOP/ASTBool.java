package ast.BoolOP;
import ast.Exp;
import symbols.Env;
import values.BoolValue;
import values.Value;
public class ASTBool implements Exp {
    public boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }



    @Override
    public <T,E> T accept(Visitor<T,E> v,E env) {
        return v.visit(this,env);
    }


}
