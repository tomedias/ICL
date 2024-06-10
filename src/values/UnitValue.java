package values;

import types.RefType;
import types.Type;
import types.UnitType;

public class UnitValue implements Value{

    public static final UnitValue singleton = new UnitValue();

    private UnitValue() {
    }


    @Override
    public String toString() {
        return "()";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnitValue;
    }


    @Override
    public Type getType() {
        return UnitType.singleton;
    }
}
