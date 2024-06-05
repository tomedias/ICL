package types;

import java.util.ArrayList;

public class FunType implements Type{
    public Type returnType;
    public ArrayList<Type> args;
    public FunType(Type returnType, ArrayList<Type> args) {
        this.returnType = returnType;
        this.args = args;
    }
    @Override
    public String getJvmType() {
        return "";
    }
}
