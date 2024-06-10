package types;

public enum ParsingType {
    INT("int", IntType.singleton),
    BOOL("bool", BoolType.singleton),
    STRING("string", StringType.singleton),
    UNIT("unit",  UnitType.singleton),
    REF_INT("ref_int", new RefType(IntType.singleton)),
    REF_BOOL("ref_bool", new RefType(BoolType.singleton)),
    REF_STRING("ref_string", new RefType(StringType.singleton)),
    REF_REF("ref_ref", new RefType(new RefType(IntType.singleton))); // lets say defaults for ref_ref

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
