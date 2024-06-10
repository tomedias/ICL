package types;

public class StringType implements Type{

    public static final StringType singleton = new StringType();

    private StringType() {}

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString() {
        return "string";
    }
    @Override
    public String getJvmType() {
        return "Ljava/lang/String;";
    }
}
