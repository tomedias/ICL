package types;

public enum ParsingType {
    Int("int", "I" ,IntType.singleton),
    Bool("bool", "I",BoolType.singleton),
    Unit("unit", "V", UnitType.singleton),;

    private final String jvmId;
    private final String jvmType;
    private final Type type;

    ParsingType(String jvmId, String jvmType, Type type) {
        this.jvmId = jvmId;
        this.jvmType = jvmType;
        this.type = type;
    }
}
