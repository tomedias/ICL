package types;

import java.util.ArrayList;
import java.util.Objects;

public class FunType implements Type{
    public Type returnType;
    public ArrayList<Type> args;
    public String interfaceName;
    public FunType(Type returnType, ArrayList<Type> args) {
        this.returnType = returnType;
        this.args = args;
    }
    @Override
    public String getJvmType() {
        return "Lclosure_interface_%s;".formatted(interfaceName);
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunType funType = (FunType) o;
        return Objects.equals(returnType, funType.returnType) && Objects.equals(args, funType.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnType, args);
    }
}
