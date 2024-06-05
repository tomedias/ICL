package types;

public enum ParsingType {
    INT("int", IntType.singleton),
    BOOL("bool", BoolType.singleton),
    UNIT("unit",  UnitType.singleton),;

    private final String jvmId;
    private final Type type;

    ParsingType(String jvmId,Type type) {
        this.jvmId = jvmId;
        this.type = type;
    }

    public String getJvmId() {
        return jvmId;
    }

    public Type getType() {
        return type;
    }
}
